package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss1.back.psi_firm.dto.request.LogInDto;
import ss1.back.psi_firm.dto.request.VerificationCodeDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.service.CodigoSesionEmpleadoService;
import ss1.back.psi_firm.service.CodigoSesionPacienteService;
import ss1.back.psi_firm.service.CuentaEmpleadoService;
import ss1.back.psi_firm.service.CuentaPacienteService;

@RequiredArgsConstructor
@RequestMapping("/login")
@RestController
public class LoginController {


    private final CuentaEmpleadoService cuentaEmpleadoService;

    private final CuentaPacienteService cuentaPacienteService;

    private final CodigoSesionEmpleadoService codigoSesionEmpleadoService;

    private final CodigoSesionPacienteService codigoSesionPacienteService;

    @PostMapping("/empleado")
    public ResponseEntity<ResponseSuccessDto> loginEmpleado(@RequestBody LogInDto logInDto){
        ResponseSuccessDto responseSuccessDto = cuentaEmpleadoService.validateLogin(logInDto);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/paciente")
    public ResponseEntity<ResponseSuccessDto> loginPaciente(@RequestBody LogInDto logInDto){
        ResponseSuccessDto responseSuccessDto = cuentaPacienteService.validateLogin(logInDto);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/code/empleado")
    public ResponseEntity<ResponseSuccessDto> verificationCodeEmpleado(@RequestBody VerificationCodeDto verificationCodeDto){
        ResponseSuccessDto responseSuccessDto = codigoSesionEmpleadoService.verificationCode(verificationCodeDto);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/code/paciente")
    public ResponseEntity<ResponseSuccessDto> verificationCodePaciente(@RequestBody VerificationCodeDto verificationCodeDto){
        ResponseSuccessDto responseSuccessDto = codigoSesionPacienteService.verificationCode(verificationCodeDto);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
