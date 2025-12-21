package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewProductoDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.ProductoCrud;
import ss1.back.psi_firm.repository.crud.TipoProductoCrud;
import ss1.back.psi_firm.repository.entities.ProductoEntity;
import ss1.back.psi_firm.repository.entities.TipoProductoEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoCrud productoCrud;
    private final TipoProductoCrud tipoProductoCrud;

    public ArrayList<ProductoEntity> getAll(){
        return (ArrayList<ProductoEntity>) productoCrud.findAll();
    }

    public ProductoEntity getById(Integer id){
        Optional<ProductoEntity> productoEntityOptional = productoCrud.findById(id);

        if(productoEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }

        return productoEntityOptional.get();
    }

    public void createNewProducto(NewProductoDto newProductoDto){

        Optional<TipoProductoEntity> tipoProductoEntityOptional = tipoProductoCrud.findById(newProductoDto.getIdTipoProducto());

        if(tipoProductoEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Tipo de producto no encontrado");
        }

        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setNombreProducto(newProductoDto.getNombreProducto());
        productoEntity.setDescripcion(newProductoDto.getDescripcion());
        productoEntity.setPrecioVenta(newProductoDto.getPrecioVenta());
        productoEntity.setTipoProducto(tipoProductoEntityOptional.get());

        productoCrud.save(productoEntity);
    }

}
