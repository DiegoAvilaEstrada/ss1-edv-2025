package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewFacturaDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.FacturaEntity;
import ss1.back.psi_firm.service.FacturaService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/factura")
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllFacturas(){

        ArrayList<FacturaEntity> facturas = facturaService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Facturas obtenidas con éxito");
        responseSuccessDto.setResponseObject(facturas);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getFacturaById(@PathVariable(name = "id") Integer id){

        FacturaEntity facturaEntity = facturaService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(facturaEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Factura encontrada con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createFactura(@RequestBody NewFacturaDto newFacturaDto){

        facturaService.createNewFactura(newFacturaDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Factura creada con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
