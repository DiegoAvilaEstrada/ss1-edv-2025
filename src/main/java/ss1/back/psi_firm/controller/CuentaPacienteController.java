package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewPacienteCuentaDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.service.CuentaPacienteService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cuenta-paciente")
public class CuentaPacienteController {

    private final CuentaPacienteService cuentaPacienteService;

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createCuentaPaciente(@RequestBody NewPacienteCuentaDto newPacienteCuentaDto){

        cuentaPacienteService.createCuentaPaciente(newPacienteCuentaDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Cuenta de paciente creada con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
