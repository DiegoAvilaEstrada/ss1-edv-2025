package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewDetalleFacturaDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.DetalleFacturaEntity;
import ss1.back.psi_firm.service.DetalleFacturaService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/detalle_factura")
public class DetalleFacturaController {

    private final DetalleFacturaService detalleFacturaService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllDetallesFactura(){

        ArrayList<DetalleFacturaEntity> detalles = detalleFacturaService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Detalles de factura obtenidos con éxito");
        responseSuccessDto.setResponseObject(detalles);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getDetalleFacturaById(@PathVariable(name = "id") Integer id){

        DetalleFacturaEntity detalleFacturaEntity = detalleFacturaService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(detalleFacturaEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Detalle de factura encontrado con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createDetalleFactura(@RequestBody NewDetalleFacturaDto newDetalleFacturaDto){

        detalleFacturaService.createNewDetalleFactura(newDetalleFacturaDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Detalle de factura creado con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
