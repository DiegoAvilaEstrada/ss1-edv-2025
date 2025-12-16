package ss1.back.psi_firm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewCuentaEmpleadoDto;
import ss1.back.psi_firm.repository.crud.CuentaEmpleadoCrud;
import ss1.back.psi_firm.repository.entities.CuentaEmpleadoEntity;
import ss1.back.psi_firm.repository.entities.EmpleadoEntity;

@Slf4j
@RequiredArgsConstructor
@Service
public class CuentaEmpleadoService {

    private final CuentaEmpleadoCrud cuentaEmpleadoCrud;
    private final EmpleadoService empleadoService;

    public void createCuentaEmpleado(NewCuentaEmpleadoDto newCuentaEmpleadoDto){

        EmpleadoEntity empleadoEntity = empleadoService.getById(newCuentaEmpleadoDto.getDpi());

        CuentaEmpleadoEntity cuentaEmpleadoEntity = new CuentaEmpleadoEntity();
        cuentaEmpleadoEntity.setUsername(newCuentaEmpleadoDto.getUsername());
        cuentaEmpleadoEntity.setPassword(newCuentaEmpleadoDto.getPassword());
        cuentaEmpleadoEntity.setEmpleado(empleadoEntity);

        cuentaEmpleadoCrud.save(cuentaEmpleadoEntity);
    }

}
