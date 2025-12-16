package ss1.back.psi_firm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewEmpleadoDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.EmpleadoCrud;
import ss1.back.psi_firm.repository.entities.EmpleadoEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmpleadoService {

    private final EmpleadoCrud empleadoCrud;
    private final RolService rolService;

    public ArrayList<EmpleadoEntity> getAll(){
        return (ArrayList<EmpleadoEntity>) empleadoCrud.findAll();
    }

    public EmpleadoEntity getById(String dpi){
        Optional<EmpleadoEntity> empleadoEntityOptional = empleadoCrud.findById(dpi);

        if(empleadoEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Empleado no encontrado");
        }

        return empleadoEntityOptional.get();
    }

    public void createNewEmpleado(NewEmpleadoDto newEmpleadoDto){

        EmpleadoEntity empleadoEntity = new EmpleadoEntity();
        empleadoEntity.setDpi(newEmpleadoDto.getDpi());
        empleadoEntity.setNombre(newEmpleadoDto.getNombre());
        empleadoEntity.setApellido(newEmpleadoDto.getApellido());
        empleadoEntity.setTelefono(newEmpleadoDto.getTelefono());
        empleadoEntity.setEmail(newEmpleadoDto.getEmail());
        empleadoEntity.setSalario(newEmpleadoDto.getSalario());
        empleadoEntity.setRolEmpleado(rolService.getById(newEmpleadoDto.getIdRolEmpleado()));
        empleadoEntity.setDescuentoIgss(newEmpleadoDto.getDescuentoIgss());

        empleadoCrud.save(empleadoEntity);
    }

}
