package ss1.back.psi_firm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewPacienteDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.PacienteCrud;
import ss1.back.psi_firm.repository.entities.PacienteEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PacienteService {

    private final PacienteCrud pacienteCrud;

    public ArrayList<PacienteEntity> getAll(){
        return (ArrayList<PacienteEntity>) pacienteCrud.findAll();
    }

    public PacienteEntity getById(String dpi){
        Optional<PacienteEntity> pacienteEntityOptional = pacienteCrud.findById(dpi);

        if(pacienteEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }

        return pacienteEntityOptional.get();
    }

    public void createNewPaciente(NewPacienteDto newPacienteDto){

        PacienteEntity pacienteEntity = new PacienteEntity();
        pacienteEntity.setDpi(newPacienteDto.getDpi());
        pacienteEntity.setNombre(newPacienteDto.getNombre());
        pacienteEntity.setApellido(newPacienteDto.getApellido());
        pacienteEntity.setTelefono(newPacienteDto.getTelefono());
        pacienteEntity.setEmail(newPacienteDto.getEmail());
        pacienteEntity.setNit(newPacienteDto.getNit());

        pacienteCrud.save(pacienteEntity);
    }

    public void updatePaciente(NewPacienteDto newPacienteDto){
        Optional<PacienteEntity> pacienteEntityOptional = pacienteCrud.findById(newPacienteDto.getDpi());

        if(pacienteEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }

        PacienteEntity pacienteEntity = pacienteEntityOptional.get();
        pacienteEntity.setNombre(newPacienteDto.getNombre());
        pacienteEntity.setApellido(newPacienteDto.getApellido());
        pacienteEntity.setTelefono(newPacienteDto.getTelefono());
        pacienteEntity.setEmail(newPacienteDto.getEmail());
        pacienteEntity.setNit(newPacienteDto.getNit());

        pacienteCrud.save(pacienteEntity);
    }

    public void deletePaciente(String dpi){
        Optional<PacienteEntity> pacienteEntityOptional = pacienteCrud.findById(dpi);

        if(pacienteEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }

        pacienteCrud.delete(pacienteEntityOptional.get());
    }

}
