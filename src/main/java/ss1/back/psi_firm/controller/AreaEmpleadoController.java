package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewAreaEmpleadoDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.AreaEmpleadoEntity;
import ss1.back.psi_firm.service.AreaEmpleadoService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/area-empleado")
public class AreaEmpleadoController {

    private final AreaEmpleadoService areaEmpleadoService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllAreaEmpleados(){

        ArrayList<AreaEmpleadoEntity> areaEmpleados = areaEmpleadoService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("AreaEmpleados obtenidos con éxito");
        responseSuccessDto.setResponseObject(areaEmpleados);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getAreaEmpleadoById(@PathVariable(name = "id") Integer id){

        AreaEmpleadoEntity areaEmpleadoEntity = areaEmpleadoService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(areaEmpleadoEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("AreaEmpleado encontrado con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createAreaEmpleado(@RequestBody NewAreaEmpleadoDto newAreaEmpleadoDto){

        areaEmpleadoService.createNewAreaEmpleado(newAreaEmpleadoDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("AreaEmpleado creado con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
