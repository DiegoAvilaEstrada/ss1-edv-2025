package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewPagoSesionDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.PagoSesionEntity;
import ss1.back.psi_firm.service.PagoSesionService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pago_sesion")
public class PagoSesionController {

    private final PagoSesionService pagoSesionService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllPagosSesion(){

        ArrayList<PagoSesionEntity> pagos = pagoSesionService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Pagos de sesión obtenidos con éxito");
        responseSuccessDto.setResponseObject(pagos);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getPagoSesionById(@PathVariable(name = "id") Integer id){

        PagoSesionEntity pagoSesionEntity = pagoSesionService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(pagoSesionEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Pago de sesión encontrado con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createPagoSesion(@RequestBody NewPagoSesionDto newPagoSesionDto){

        pagoSesionService.createNewPagoSesion(newPagoSesionDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Pago de sesión creado con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
