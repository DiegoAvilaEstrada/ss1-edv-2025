package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewFacturaDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.FacturaCrud;
import ss1.back.psi_firm.repository.entities.FacturaEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FacturaService {

    private final FacturaCrud facturaCrud;
    private final PacienteService pacienteService;
    private final TratamientoService tratamientoService;

    public ArrayList<FacturaEntity> getAll(){
        return (ArrayList<FacturaEntity>) facturaCrud.findAll();
    }

    public FacturaEntity getById(Integer id){
        Optional<FacturaEntity> facturaEntityOptional = facturaCrud.findById(id);

        if(facturaEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Factura no encontrada");
        }

        return facturaEntityOptional.get();
    }

    public void createNewFactura(NewFacturaDto newFacturaDto){

        FacturaEntity facturaEntity = new FacturaEntity();
        facturaEntity.setPaciente(pacienteService.getById(newFacturaDto.getDpiPaciente()));
        facturaEntity.setTratamiento(tratamientoService.getById(newFacturaDto.getIdTratamiento()));
        facturaEntity.setFechaEmision(newFacturaDto.getFechaEmision());
        facturaEntity.setMontoTotal(newFacturaDto.getMontoTotal());

        facturaCrud.save(facturaEntity);
    }

}
