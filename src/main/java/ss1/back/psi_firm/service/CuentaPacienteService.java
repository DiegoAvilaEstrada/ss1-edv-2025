package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.LogInDto;
import ss1.back.psi_firm.dto.request.NewPacienteCuentaDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.CodigoSesionPacienteCrud;
import ss1.back.psi_firm.repository.crud.CuentaPacienteCrud;
import ss1.back.psi_firm.repository.entities.CodigoSesionPacienteEntity;
import ss1.back.psi_firm.repository.entities.CuentaPacienteEntity;
import ss1.back.psi_firm.repository.entities.PacienteEntity;
import ss1.back.psi_firm.utils.AuthUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CuentaPacienteService {

    private final CuentaPacienteCrud cuentaPacienteCrud;
    private final PacienteService pacienteService;
    private final AuthUtils authUtils;
    private final EmailService emailService;
    private final CodigoSesionPacienteCrud codigoSesionPacienteCrud;

    public void createCuentaPaciente(NewPacienteCuentaDto newPacienteCuentaDto){

        PacienteEntity pacienteEntity = pacienteService.getById(newPacienteCuentaDto.getDpi());

        CuentaPacienteEntity cuentaPacienteEntity = new CuentaPacienteEntity();
        cuentaPacienteEntity.setUsername(newPacienteCuentaDto.getUsername());
        cuentaPacienteEntity.setPassword(newPacienteCuentaDto.getPassword());
        cuentaPacienteEntity.setPaciente(pacienteEntity);

        cuentaPacienteCrud.save(cuentaPacienteEntity);
    }

    public ResponseSuccessDto validateLogin(LogInDto logInDto) {
        Optional<CuentaPacienteEntity> cuentaPacienteOptional = cuentaPacienteCrud.findById(logInDto.getUsername());
        
        if (cuentaPacienteOptional.isEmpty()) {
            log.warn("Usuario no encontrado: {}", logInDto.getUsername());
            throw new BusinessException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        
        CuentaPacienteEntity cuentaPacienteEntity = cuentaPacienteOptional.get();
        boolean passwordValid = authUtils.validatePassword(logInDto.getPassword(), cuentaPacienteEntity.getPassword());
        
        if (!passwordValid) {
            log.warn("Contraseña inválida para usuario: {}", logInDto.getUsername());
            throw new BusinessException(HttpStatus.NOT_FOUND, "Contraseña incorrecta");
        }

        String verificationCode = emailService.sendVerificationCode(cuentaPacienteEntity.getPaciente().getEmail());

        CodigoSesionPacienteEntity codigoSesionPacienteEntity = new CodigoSesionPacienteEntity();
        codigoSesionPacienteEntity.setCodigo(verificationCode);
        codigoSesionPacienteEntity.setFechaExpiracion(authUtils.createExpirationLocalDateTime(2));
        codigoSesionPacienteEntity.setCuentaPaciente(cuentaPacienteEntity);

        codigoSesionPacienteCrud.save(codigoSesionPacienteEntity);
        log.info("Código de sesión guardado para paciente: {}", logInDto.getUsername());

        return new ResponseSuccessDto(HttpStatus.OK.value(), "Se ha enviado un codigo a su correo", null);
    }

}
