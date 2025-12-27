package ss1.back.psi_firm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.EmpleadoRecoveryPasswordDto;
import ss1.back.psi_firm.dto.request.LogInDto;
import ss1.back.psi_firm.dto.request.NewCuentaEmpleadoDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.CodigoSesionEmpleadoCrud;
import ss1.back.psi_firm.repository.crud.CuentaEmpleadoCrud;
import ss1.back.psi_firm.repository.entities.CodigoSesionEmpleadoEntity;
import ss1.back.psi_firm.repository.entities.CuentaEmpleadoEntity;
import ss1.back.psi_firm.repository.entities.EmpleadoEntity;
import ss1.back.psi_firm.utils.AuthUtils;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CuentaEmpleadoService {

    private final CuentaEmpleadoCrud cuentaEmpleadoCrud;
    private final EmpleadoService empleadoService;
    private final AuthUtils authUtils;
    private final EmailService emailService;
    private final CodigoSesionEmpleadoCrud codigoSesionEmpleadoCrud;

    public ArrayList<CuentaEmpleadoEntity> getAll(){
        return (ArrayList<CuentaEmpleadoEntity>) cuentaEmpleadoCrud.findAll();
    }

    public void createCuentaEmpleado(NewCuentaEmpleadoDto newCuentaEmpleadoDto){

        EmpleadoEntity empleadoEntity = empleadoService.getById(newCuentaEmpleadoDto.getDpi());

        CuentaEmpleadoEntity cuentaEmpleadoEntity = new CuentaEmpleadoEntity();
        cuentaEmpleadoEntity.setUsername(newCuentaEmpleadoDto.getUsername());
        cuentaEmpleadoEntity.setPassword(authUtils.hashPassword(newCuentaEmpleadoDto.getPassword()));
        cuentaEmpleadoEntity.setEmpleado(empleadoEntity);

        cuentaEmpleadoCrud.save(cuentaEmpleadoEntity);
    }

    public ResponseSuccessDto validateLogin(LogInDto logInDto) {
        Optional<CuentaEmpleadoEntity> cuentaEmpleadoOptional = cuentaEmpleadoCrud.findById(logInDto.getUsername());
        
        if (cuentaEmpleadoOptional.isEmpty()) {
            log.warn("Usuario no encontrado: {}", logInDto.getUsername());
            throw new BusinessException(HttpStatus.NOT_FOUND, "Empleado no encontrado");
        }
        
        CuentaEmpleadoEntity cuentaEmpleadoEntity = cuentaEmpleadoOptional.get();
        boolean passwordValid = authUtils.validatePassword(logInDto.getPassword(), cuentaEmpleadoEntity.getPassword());
        
        if (!passwordValid) {
            log.warn("Contraseña inválida para usuario: {}", logInDto.getUsername());
            throw new BusinessException(HttpStatus.NOT_FOUND, "Contraseña incorrecta");
        }

        String verificationCode = emailService.sendVerificationCode(cuentaEmpleadoEntity.getEmpleado().getEmail());

        CodigoSesionEmpleadoEntity codigoSesionEmpleadoEntity = new CodigoSesionEmpleadoEntity();
        codigoSesionEmpleadoEntity.setCodigo(verificationCode);
        codigoSesionEmpleadoEntity.setFechaExpiracion(authUtils.createExpirationLocalDateTime(2));
        codigoSesionEmpleadoEntity.setCuentaEmpleado(cuentaEmpleadoEntity);

        codigoSesionEmpleadoCrud.save(codigoSesionEmpleadoEntity);
        log.info("Código de sesión guardado para empleado: {}", logInDto.getUsername());
        return new ResponseSuccessDto(HttpStatus.OK.value(), "Se ha enviado un codigo a su correo", null);
    }

    public void recoveryPassword(EmpleadoRecoveryPasswordDto empleadoRecoveryPasswordDto){
        Optional<CuentaEmpleadoEntity> cuentaEmpleadoOptional = cuentaEmpleadoCrud.findById(empleadoRecoveryPasswordDto.getUsername());
        
        if (cuentaEmpleadoOptional.isEmpty()) {
            log.warn("Usuario no encontrado para recuperación de contraseña: {}", empleadoRecoveryPasswordDto.getUsername());
            throw new BusinessException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        
        if (!empleadoRecoveryPasswordDto.getNewPassword().equals(empleadoRecoveryPasswordDto.getConfirmNewPassword())) {
            log.warn("Las contraseñas no coinciden para usuario: {}", empleadoRecoveryPasswordDto.getUsername());
            throw new BusinessException(HttpStatus.BAD_REQUEST, "La nueva contraseña no coincide con la confirmación");
        }
        
        CuentaEmpleadoEntity cuentaEmpleadoEntity = cuentaEmpleadoOptional.get();
        String hashedPassword = authUtils.hashPassword(empleadoRecoveryPasswordDto.getNewPassword());
        cuentaEmpleadoEntity.setPassword(hashedPassword);
        
        cuentaEmpleadoCrud.save(cuentaEmpleadoEntity);
        log.info("Contraseña actualizada exitosamente para usuario: {}", empleadoRecoveryPasswordDto.getUsername());
    }

}
