package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewTipoProductoDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.TipoProductoCrud;
import ss1.back.psi_firm.repository.entities.TipoProductoEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TipoProductoService {

    private final TipoProductoCrud tipoProductoCrud;

    public ArrayList<TipoProductoEntity> getAll(){
        return (ArrayList<TipoProductoEntity>) tipoProductoCrud.findAll();
    }

    public TipoProductoEntity getById(Integer id){
        Optional<TipoProductoEntity> tipoProductoEntityOptional = tipoProductoCrud.findById(id);

        if(tipoProductoEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Tipo de producto no encontrado");
        }

        return tipoProductoEntityOptional.get();
    }

    public void createNewTipoProducto(NewTipoProductoDto newTipoProductoDto){

        TipoProductoEntity tipoProductoEntity = new TipoProductoEntity();
        tipoProductoEntity.setNombreTipo(newTipoProductoDto.getNombreTipo());

        tipoProductoCrud.save(tipoProductoEntity);
    }

}
