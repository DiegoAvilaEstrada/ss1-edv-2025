package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewRolDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.RolEntity;
import ss1.back.psi_firm.service.RolService;

import java.util.ArrayList;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rol")
public class RolController {

    private RolService rolService;


    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllRoles(){

        ArrayList<RolEntity> roles = rolService.getAllRoles();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Roles obtenidos con éxito");
        responseSuccessDto.setResponseObject(roles);
        return new ResponseEntity<>(responseSuccessDto,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getRolById(@PathVariable(name = "id") Integer id){

        RolEntity rolEntity = rolService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(rolEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Rol encontrado con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createRol(@RequestBody NewRolDto newRolDto){

        rolService.createNewRole(newRolDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Rol creado con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }






}
