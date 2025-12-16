package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewAreaEmpleadoDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.AreaEmpleadoCrud;
import ss1.back.psi_firm.repository.entities.AreaEmpleadoEntity;
import ss1.back.psi_firm.repository.entities.AreaEntity;
import ss1.back.psi_firm.repository.entities.EmpleadoEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AreaEmpleadoService {

    private final AreaEmpleadoCrud areaEmpleadoCrud;
    private final AreaService areaService;
    private final EmpleadoService empleadoService;

    public ArrayList<AreaEmpleadoEntity> getAll(){
        return (ArrayList<AreaEmpleadoEntity>) areaEmpleadoCrud.findAll();
    }

    public AreaEmpleadoEntity getById(Integer id){
        Optional<AreaEmpleadoEntity> areaEmpleadoEntityOptional = areaEmpleadoCrud.findById(id);

        if(areaEmpleadoEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "AreaEmpleado no encontrado");
        }

        return areaEmpleadoEntityOptional.get();
    }

    public void createNewAreaEmpleado(NewAreaEmpleadoDto newAreaEmpleadoDto){

        AreaEntity areaEntity = areaService.getById(newAreaEmpleadoDto.getIdArea());
        EmpleadoEntity empleadoEntity = empleadoService.getById(newAreaEmpleadoDto.getDpiEmpleado());

        AreaEmpleadoEntity areaEmpleadoEntity = new AreaEmpleadoEntity();
        areaEmpleadoEntity.setArea(areaEntity);
        areaEmpleadoEntity.setEmpleado(empleadoEntity);

        areaEmpleadoCrud.save(areaEmpleadoEntity);
    }

}
