package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewTratamientoDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.TratamientoCrud;
import ss1.back.psi_firm.repository.entities.TratamientoEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TratamientoService {

    private final TratamientoCrud tratamientoCrud;
    private final PacienteService pacienteService;
    private final EmpleadoService empleadoService;

    public ArrayList<TratamientoEntity> getAll(){
        return (ArrayList<TratamientoEntity>) tratamientoCrud.findAll();
    }

    public TratamientoEntity getById(Integer id){
        Optional<TratamientoEntity> tratamientoEntityOptional = tratamientoCrud.findById(id);

        if(tratamientoEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Tratamiento no encontrado");
        }

        return tratamientoEntityOptional.get();
    }

    public void createNewTratamiento(NewTratamientoDto newTratamientoDto){

        TratamientoEntity tratamientoEntity = new TratamientoEntity();
        tratamientoEntity.setPaciente(pacienteService.getById(newTratamientoDto.getDpiPaciente()));
        tratamientoEntity.setPsicologo(empleadoService.getById(newTratamientoDto.getPsicologoDpi()));
        tratamientoEntity.setMedicado(newTratamientoDto.getMedicado());
        tratamientoEntity.setFechaInicio(newTratamientoDto.getFechaInicio());
        tratamientoEntity.setEstadoTratamiento(newTratamientoDto.getEstadoTratamiento());

        tratamientoCrud.save(tratamientoEntity);
    }

}
