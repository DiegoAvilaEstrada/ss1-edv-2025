package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewTipoProductoDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.TipoProductoEntity;
import ss1.back.psi_firm.service.TipoProductoService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tipo_producto")
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllTiposProducto(){

        ArrayList<TipoProductoEntity> tiposProducto = tipoProductoService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Tipos de producto obtenidos con éxito");
        responseSuccessDto.setResponseObject(tiposProducto);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getTipoProductoById(@PathVariable(name = "id") Integer id){

        TipoProductoEntity tipoProductoEntity = tipoProductoService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(tipoProductoEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Tipo de producto encontrado con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createTipoProducto(@RequestBody NewTipoProductoDto newTipoProductoDto){

        tipoProductoService.createNewTipoProducto(newTipoProductoDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Tipo de producto creado con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
