package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewSesionPsicologicaDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.SesionPsicologicaEntity;
import ss1.back.psi_firm.service.SesionPsicologicaService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sesion_psicologica")
public class SesionPsicologicaController {

    private final SesionPsicologicaService sesionPsicologicaService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllSesionesPsicologicas(){

        ArrayList<SesionPsicologicaEntity> sesiones = sesionPsicologicaService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Sesiones psicológicas obtenidas con éxito");
        responseSuccessDto.setResponseObject(sesiones);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getSesionPsicologicaById(@PathVariable(name = "id") Integer id){

        SesionPsicologicaEntity sesionPsicologicaEntity = sesionPsicologicaService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(sesionPsicologicaEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Sesión psicológica encontrada con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createSesionPsicologica(@RequestBody NewSesionPsicologicaDto newSesionPsicologicaDto){

        sesionPsicologicaService.createNewSesionPsicologica(newSesionPsicologicaDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Sesión psicológica creada con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
