package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.EmpleadoRecoveryPasswordDto;
import ss1.back.psi_firm.dto.request.NewCuentaEmpleadoDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.CuentaEmpleadoEntity;
import ss1.back.psi_firm.service.CuentaEmpleadoService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cuenta-empleado")
public class CuentaEmpleadoController {

    private final CuentaEmpleadoService cuentaEmpleadoService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllCuentaEmpleados(){

        ArrayList<CuentaEmpleadoEntity> cuentaEmpleados = cuentaEmpleadoService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Cuentas de empleados obtenidas con éxito");
        responseSuccessDto.setResponseObject(cuentaEmpleados);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createCuentaEmpleado(@RequestBody NewCuentaEmpleadoDto newCuentaEmpleadoDto){

        cuentaEmpleadoService.createCuentaEmpleado(newCuentaEmpleadoDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Cuenta de empleado creada con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/recovery/password")
    public ResponseEntity<ResponseSuccessDto> recoveryPassword(@RequestBody EmpleadoRecoveryPasswordDto empleadoRecoveryPasswordDto){
        cuentaEmpleadoService.recoveryPassword(empleadoRecoveryPasswordDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Recuperación de contraseña exitoso!");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}

