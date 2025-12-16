package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewAreaDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.AreaCrud;
import ss1.back.psi_firm.repository.entities.AreaEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AreaService {

    private final AreaCrud areaCrud;

    public ArrayList<AreaEntity> getAll(){
        return (ArrayList<AreaEntity>) areaCrud.findAll();
    }

    public AreaEntity getById(Integer id){
        Optional<AreaEntity> areaEntityOptional = areaCrud.findById(id);

        if(areaEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Area no encontrada");
        }

        return areaEntityOptional.get();
    }

    public void createNewArea(NewAreaDto newAreaDto){

        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setNombreArea(newAreaDto.getNombreArea());

        areaCrud.save(areaEntity);
    }

}
