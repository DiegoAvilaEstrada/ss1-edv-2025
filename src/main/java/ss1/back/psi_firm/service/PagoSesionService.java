package ss1.back.psi_firm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.dto.request.NewPagoSesionDto;
import ss1.back.psi_firm.exception.BusinessException;
import ss1.back.psi_firm.repository.crud.PagoSesionCrud;
import ss1.back.psi_firm.repository.entities.PagoSesionEntity;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PagoSesionService {

    private final PagoSesionCrud pagoSesionCrud;
    private final SesionPsicologicaService sesionPsicologicaService;
    private final FacturaService facturaService;

    public ArrayList<PagoSesionEntity> getAll(){
        return (ArrayList<PagoSesionEntity>) pagoSesionCrud.findAll();
    }

    public PagoSesionEntity getById(Integer id){
        Optional<PagoSesionEntity> pagoSesionEntityOptional = pagoSesionCrud.findById(id);

        if(pagoSesionEntityOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Pago de sesión no encontrado");
        }

        return pagoSesionEntityOptional.get();
    }

    public void createNewPagoSesion(NewPagoSesionDto newPagoSesionDto){

        PagoSesionEntity pagoSesionEntity = new PagoSesionEntity();
        pagoSesionEntity.setSesion(sesionPsicologicaService.getById(newPagoSesionDto.getIdSesion()));
        pagoSesionEntity.setFactura(facturaService.getById(newPagoSesionDto.getIdFactura()));
        pagoSesionEntity.setFechaPago(newPagoSesionDto.getFechaPago());
        pagoSesionEntity.setDescuento(newPagoSesionDto.getDescuento());
        pagoSesionEntity.setMontoPagado(newPagoSesionDto.getMontoPagado());

        pagoSesionCrud.save(pagoSesionEntity);
    }

}
