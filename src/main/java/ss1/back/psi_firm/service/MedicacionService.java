package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewMedicacionDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.MedicacionCrud;
import ss1.back.psi_firm.repository.entities.MedicacionEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicacionService {

    private final MedicacionCrud medicacionCrud;
    private final TratamientoService tratamientoService;
    private final ProductoService productoService;

    public ArrayList<MedicacionEntity> getAll(){
        return (ArrayList<MedicacionEntity>) medicacionCrud.findAll();
    }

    public MedicacionEntity getById(Integer id){
        Optional<MedicacionEntity> medicacionEntityOptional = medicacionCrud.findById(id);

        if(medicacionEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Medicación no encontrada");
        }

        return medicacionEntityOptional.get();
    }

    public void createNewMedicacion(NewMedicacionDto newMedicacionDto){

        MedicacionEntity medicacionEntity = new MedicacionEntity();
        medicacionEntity.setTratamiento(tratamientoService.getById(newMedicacionDto.getIdTratamiento()));
        medicacionEntity.setProducto(productoService.getById(newMedicacionDto.getIdProducto()));
        medicacionEntity.setDosis(newMedicacionDto.getDosis());
        medicacionEntity.setFrecuencia(newMedicacionDto.getFrecuencia());

        medicacionCrud.save(medicacionEntity);
    }

}
