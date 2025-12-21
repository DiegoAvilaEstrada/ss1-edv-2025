package ss1.back.psi_firm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewCodigoSesionPacienteDto;
import ss1.back.psi_firm.dto.request.VerificationCodeDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.CodigoSesionPacienteCrud;
import ss1.back.psi_firm.repository.crud.CuentaPacienteCrud;
import ss1.back.psi_firm.repository.entities.CodigoSesionPacienteEntity;
import ss1.back.psi_firm.repository.entities.CuentaPacienteEntity;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodigoSesionPacienteService {

    private final CodigoSesionPacienteCrud codigoSesionPacienteCrud;
    private final CuentaPacienteCrud cuentaPacienteCrud;

    public void createCodigoSesionPaciente(NewCodigoSesionPacienteDto newCodigoSesionPacienteDto){

        Optional<CuentaPacienteEntity> cuentaPacienteEntityOptional = cuentaPacienteCrud.findById(newCodigoSesionPacienteDto.getUsername());

        if(cuentaPacienteEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Cuenta de paciente no encontrada");
        }

        CuentaPacienteEntity cuentaPacienteEntity = cuentaPacienteEntityOptional.get();

        CodigoSesionPacienteEntity codigoSesionPacienteEntity = new CodigoSesionPacienteEntity();
        codigoSesionPacienteEntity.setCodigo(newCodigoSesionPacienteDto.getCodigo());
        codigoSesionPacienteEntity.setFechaExpiracion(newCodigoSesionPacienteDto.getFechaExpiracion());
        codigoSesionPacienteEntity.setCuentaPaciente(cuentaPacienteEntity);

        codigoSesionPacienteCrud.save(codigoSesionPacienteEntity);
    }

    public ResponseSuccessDto verificationCode(VerificationCodeDto verificationCodeDto){
        Optional<CodigoSesionPacienteEntity> optionalCodigoSesionPacienteEntity = codigoSesionPacienteCrud.findByCodigo(verificationCodeDto.getCode());

        if(optionalCodigoSesionPacienteEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "El código de verificación no existe");
        }

        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setResponseObject(optionalCodigoSesionPacienteEntity.get().getCuentaPaciente().getPaciente());
        responseSuccessDto.setMessage("Verificación de código exitoso!");

        return responseSuccessDto;
    }

}
