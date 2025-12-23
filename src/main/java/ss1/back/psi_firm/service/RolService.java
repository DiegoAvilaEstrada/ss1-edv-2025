package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;
import ss1.back.psi_firm.dto.request.NewRolDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.RolCrud;
import ss1.back.psi_firm.repository.entities.RolEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RolService {


    private final RolCrud rolCrud;


    public ArrayList<RolEntity> getAllRoles(){
        return (ArrayList<RolEntity>) rolCrud.findAll();
    }

    public RolEntity getById(Integer id){
        Optional<RolEntity> rolEntityOptional = rolCrud.findById(id);

        if(rolEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        }

        return rolEntityOptional.get();
    }

    public void createNewRole(NewRolDto newRolDto){

        RolEntity rolEntity = new RolEntity();
        rolEntity.setRol(newRolDto.getRoleName());

        rolCrud.save(rolEntity);
    }


}
