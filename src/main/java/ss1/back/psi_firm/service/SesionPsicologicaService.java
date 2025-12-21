package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewSesionPsicologicaDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.SesionPsicologicaCrud;
import ss1.back.psi_firm.repository.entities.SesionPsicologicaEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SesionPsicologicaService {

    private final SesionPsicologicaCrud sesionPsicologicaCrud;
    private final TratamientoService tratamientoService;

    public ArrayList<SesionPsicologicaEntity> getAll(){
        return (ArrayList<SesionPsicologicaEntity>) sesionPsicologicaCrud.findAll();
    }

    public SesionPsicologicaEntity getById(Integer id){
        Optional<SesionPsicologicaEntity> sesionPsicologicaEntityOptional = sesionPsicologicaCrud.findById(id);

        if(sesionPsicologicaEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Sesión psicológica no encontrada");
        }

        return sesionPsicologicaEntityOptional.get();
    }

    public void createNewSesionPsicologica(NewSesionPsicologicaDto newSesionPsicologicaDto){

        SesionPsicologicaEntity sesionPsicologicaEntity = new SesionPsicologicaEntity();
        sesionPsicologicaEntity.setTratamiento(tratamientoService.getById(newSesionPsicologicaDto.getIdTratamiento()));
        sesionPsicologicaEntity.setFechaSesion(newSesionPsicologicaDto.getFechaSesion());
        sesionPsicologicaEntity.setObservaciones(newSesionPsicologicaDto.getObservaciones());

        sesionPsicologicaCrud.save(sesionPsicologicaEntity);
    }

}
