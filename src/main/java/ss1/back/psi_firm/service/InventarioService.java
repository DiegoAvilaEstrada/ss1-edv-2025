package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewInventarioDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.InventarioCrud;
import ss1.back.psi_firm.repository.crud.ProductoCrud;
import ss1.back.psi_firm.repository.entities.InventarioEntity;
import ss1.back.psi_firm.repository.entities.ProductoEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventarioService {

    private final InventarioCrud inventarioCrud;
    private final ProductoCrud productoCrud;

    public ArrayList<InventarioEntity> getAll(){
        return (ArrayList<InventarioEntity>) inventarioCrud.findAll();
    }

    public InventarioEntity getById(Integer id){
        Optional<InventarioEntity> inventarioEntityOptional = inventarioCrud.findById(id);

        if(inventarioEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Inventario no encontrado");
        }

        return inventarioEntityOptional.get();
    }

    /*es agregar más productos al inventario sin repetir registros con productos iguales*/
    public void createNewInventario(NewInventarioDto newInventarioDto){

        Optional<ProductoEntity> productoEntityOptional = productoCrud.findById(newInventarioDto.getIdProducto());

        if(productoEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }

        Optional<InventarioEntity> inventarioEntityOptional = inventarioCrud.findByProductId(productoEntityOptional.get().getId());

        InventarioEntity inventarioEntity;

        if(inventarioEntityOptional.isEmpty()){
            inventarioEntity = new InventarioEntity();
            inventarioEntity.setProducto(productoEntityOptional.get());
            inventarioEntity.setStock(newInventarioDto.getStock());
            inventarioEntity.setMinimoStock(newInventarioDto.getMinimoStock());
            inventarioEntity.setVentasRealizadas(0);
        }else{
            inventarioEntity = inventarioEntityOptional.get();
            inventarioEntity.setStock(inventarioEntity.getStock()+newInventarioDto.getStock());
        }

        inventarioCrud.save(inventarioEntity);
    }

    public void modificarStockVentasInventario(Integer idProducto, int stock, int ventasRealizadas){
        Optional<InventarioEntity> inventarioEntityOptional = inventarioCrud.findByProductId(idProducto);

        if(inventarioEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Inventario no encontrado para el producto especificado");
        }

        InventarioEntity inventarioEntity = inventarioEntityOptional.get();
        inventarioEntity.setStock(inventarioEntity.getStock() + stock);
        inventarioEntity.setVentasRealizadas(inventarioEntity.getVentasRealizadas() + ventasRealizadas);

        inventarioCrud.save(inventarioEntity);
    }

}
