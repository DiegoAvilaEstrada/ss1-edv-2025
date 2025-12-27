package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.LogInDto;
import ss1.back.psi_firm.dto.request.NewPacienteCuentaDto;
import ss1.back.psi_firm.dto.request.PacienteRecoveryPasswordDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.CodigoSesionPacienteCrud;
import ss1.back.psi_firm.repository.crud.CuentaPacienteCrud;
import ss1.back.psi_firm.repository.entities.CodigoSesionPacienteEntity;
import ss1.back.psi_firm.repository.entities.CuentaPacienteEntity;
import ss1.back.psi_firm.repository.entities.PacienteEntity;
import ss1.back.psi_firm.utils.AuthUtils;

import java.util.ArrayList;
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

    public ArrayList<CuentaPacienteEntity> getAll(){
        return (ArrayList<CuentaPacienteEntity>) cuentaPacienteCrud.findAll();
    }

    public void createCuentaPaciente(NewPacienteCuentaDto newPacienteCuentaDto){

        PacienteEntity pacienteEntity = pacienteService.getById(newPacienteCuentaDto.getDpi());

        CuentaPacienteEntity cuentaPacienteEntity = new CuentaPacienteEntity();
        cuentaPacienteEntity.setUsername(newPacienteCuentaDto.getUsername());
        String hashedPassword = authUtils.hashPassword(newPacienteCuentaDto.getPassword());
        cuentaPacienteEntity.setPassword(hashedPassword);
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


    public void recoveryPassword(PacienteRecoveryPasswordDto pacienteRecoveryPasswordDto){
        Optional<CuentaPacienteEntity> cuentaPacienteOptional = cuentaPacienteCrud.findById(pacienteRecoveryPasswordDto.getUsername());
        
        if (cuentaPacienteOptional.isEmpty()) {
            log.warn("Usuario no encontrado para recuperación de contraseña: {}", pacienteRecoveryPasswordDto.getUsername());
            throw new BusinessException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        
        if (!pacienteRecoveryPasswordDto.getNewPassword().equals(pacienteRecoveryPasswordDto.getConfirmNewPassword())) {
            log.warn("Las contraseñas no coinciden para usuario: {}", pacienteRecoveryPasswordDto.getUsername());
            throw new BusinessException(HttpStatus.BAD_REQUEST, "La nueva contraseña no coincide con la confirmación");
        }
        
        CuentaPacienteEntity cuentaPacienteEntity = cuentaPacienteOptional.get();
        String hashedPassword = authUtils.hashPassword(pacienteRecoveryPasswordDto.getNewPassword());
        cuentaPacienteEntity.setPassword(hashedPassword);
        
        cuentaPacienteCrud.save(cuentaPacienteEntity);
        log.info("Contraseña actualizada exitosamente para usuario: {}", pacienteRecoveryPasswordDto.getUsername());
    }

}
