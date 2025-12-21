package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewDetalleFacturaDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.DetalleFacturaCrud;
import ss1.back.psi_firm.repository.entities.DetalleFacturaEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DetalleFacturaService {

    private final DetalleFacturaCrud detalleFacturaCrud;
    private final FacturaService facturaService;
    private final ProductoService productoService;

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

        DetalleFacturaEntity detalleFacturaEntity = new DetalleFacturaEntity();
        detalleFacturaEntity.setFactura(facturaService.getById(newDetalleFacturaDto.getIdFactura()));
        detalleFacturaEntity.setProducto(productoService.getById(newDetalleFacturaDto.getIdProducto()));
        detalleFacturaEntity.setCantidad(newDetalleFacturaDto.getCantidad());
        detalleFacturaEntity.setCostoTotal(newDetalleFacturaDto.getCostoTotal());

        detalleFacturaCrud.save(detalleFacturaEntity);
    }

}
