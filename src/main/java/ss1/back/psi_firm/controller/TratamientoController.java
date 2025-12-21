package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewTratamientoDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.TratamientoEntity;
import ss1.back.psi_firm.service.TratamientoService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tratamiento")
public class TratamientoController {

    private final TratamientoService tratamientoService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllTratamientos(){

        ArrayList<TratamientoEntity> tratamientos = tratamientoService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Tratamientos obtenidos con éxito");
        responseSuccessDto.setResponseObject(tratamientos);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getTratamientoById(@PathVariable(name = "id") Integer id){

        TratamientoEntity tratamientoEntity = tratamientoService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(tratamientoEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Tratamiento encontrado con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createTratamiento(@RequestBody NewTratamientoDto newTratamientoDto){

        tratamientoService.createNewTratamiento(newTratamientoDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Tratamiento creado con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
