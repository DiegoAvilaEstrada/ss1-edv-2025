package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewDetalleFacturaDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.DetalleFacturaCrud;
import ss1.back.psi_firm.repository.entities.DetalleFacturaEntity;
import ss1.back.psi_firm.repository.entities.ProductoEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DetalleFacturaService {

    private final DetalleFacturaCrud detalleFacturaCrud;
    private final FacturaService facturaService;
    private final ProductoService productoService;
    private final InventarioService inventarioService;

    public ArrayList<DetalleFacturaEntity> getAll(){
        return (ArrayList<DetalleFacturaEntity>) detalleFacturaCrud.findAll();
    }

    public DetalleFacturaEntity getById(Integer id){
        Optional<DetalleFacturaEntity> detalleFacturaEntityOptional = detalleFacturaCrud.findById(id);

        if(detalleFacturaEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Detalle de factura no encontrado");
        }

        return detalleFacturaEntityOptional.get();
    }

    public void createNewDetalleFactura(NewDetalleFacturaDto newDetalleFacturaDto){

        ProductoEntity producto = productoService.getById(newDetalleFacturaDto.getIdProducto());
        DetalleFacturaEntity detalleFacturaEntity = new DetalleFacturaEntity();
        detalleFacturaEntity.setFactura(facturaService.getById(newDetalleFacturaDto.getIdFactura()));
        detalleFacturaEntity.setProducto(producto);
        detalleFacturaEntity.setCantidad(newDetalleFacturaDto.getCantidad());
        BigDecimal cantidadBigDecimal = BigDecimal.valueOf(newDetalleFacturaDto.getCantidad());
        detalleFacturaEntity.setCostoTotal(producto.getPrecioVenta().multiply(cantidadBigDecimal));
        
        BigDecimal montoTotalActual = detalleFacturaEntity.getFactura().getMontoTotal();
        BigDecimal precioVenta = producto.getPrecioVenta().multiply(cantidadBigDecimal);
        BigDecimal nuevoMontoTotal = montoTotalActual.add(precioVenta);
        detalleFacturaEntity.getFactura().setMontoTotal(nuevoMontoTotal);

        /*se aumenta la cantidad de ventas de un producto, y se reduce el stcok*/
        Integer idProducto = detalleFacturaEntity.getProducto().getId();
        Integer cantidad = newDetalleFacturaDto.getCantidad();
        
        inventarioService.modificarStockVentasInventario(
            idProducto,
            -cantidad,  // stock negativo para reducir
            cantidad   // ventas realizadas positivo para aumentar
        );

        detalleFacturaCrud.save(detalleFacturaEntity);
    }

}
