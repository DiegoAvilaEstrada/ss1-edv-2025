package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewCodigoSesionEmpleadoDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.CodigoSesionEmpleadoCrud;
import ss1.back.psi_firm.repository.crud.CuentaEmpleadoCrud;
import ss1.back.psi_firm.repository.entities.CodigoSesionEmpleadoEntity;
import ss1.back.psi_firm.repository.entities.CuentaEmpleadoEntity;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodigoSesionEmpleadoService {

    private final CodigoSesionEmpleadoCrud codigoSesionEmpleadoCrud;
    private final CuentaEmpleadoCrud cuentaEmpleadoCrud;

    public void createCodigoSesionEmpleado(NewCodigoSesionEmpleadoDto newCodigoSesionEmpleadoDto){

        Optional<CuentaEmpleadoEntity> cuentaEmpleadoEntityOptional = cuentaEmpleadoCrud.findById(newCodigoSesionEmpleadoDto.getUsername());

        if(cuentaEmpleadoEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Cuenta de empleado no encontrada");
        }

        CuentaEmpleadoEntity cuentaEmpleadoEntity = cuentaEmpleadoEntityOptional.get();

        CodigoSesionEmpleadoEntity codigoSesionEmpleadoEntity = new CodigoSesionEmpleadoEntity();
        codigoSesionEmpleadoEntity.setCodigo(newCodigoSesionEmpleadoDto.getCodigo());
        codigoSesionEmpleadoEntity.setFechaExpiracion(newCodigoSesionEmpleadoDto.getFechaExpiracion());
        codigoSesionEmpleadoEntity.setCuentaEmpleado(cuentaEmpleadoEntity);

        codigoSesionEmpleadoCrud.save(codigoSesionEmpleadoEntity);
    }

}
