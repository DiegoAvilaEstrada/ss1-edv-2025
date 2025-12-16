package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewEmpleadoDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.EmpleadoEntity;
import ss1.back.psi_firm.service.EmpleadoService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllEmpleados(){

        ArrayList<EmpleadoEntity> empleados = empleadoService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Empleados obtenidos con éxito");
        responseSuccessDto.setResponseObject(empleados);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{dpi}")
    public ResponseEntity<ResponseSuccessDto> getEmpleadoById(@PathVariable(name = "dpi") String dpi){

        EmpleadoEntity empleadoEntity = empleadoService.getById(dpi);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(empleadoEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Empleado encontrado con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createEmpleado(@RequestBody NewEmpleadoDto newEmpleadoDto){

        empleadoService.createNewEmpleado(newEmpleadoDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Empleado creado con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
