package ss1.back.psi_firm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ss1.back.psi_firm.dto.request.NewProductoDto;
import ss1.back.psi_firm.dto.response.ResponseSuccessDto;
import ss1.back.psi_firm.repository.entities.ProductoEntity;
import ss1.back.psi_firm.service.ProductoService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessDto> getAllProductos(){

        ArrayList<ProductoEntity> productos = productoService.getAll();
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Productos obtenidos con éxito");
        responseSuccessDto.setResponseObject(productos);
        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessDto> getProductoById(@PathVariable(name = "id") Integer id){

        ProductoEntity productoEntity = productoService.getById(id);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setResponseObject(productoEntity);
        responseSuccessDto.setCode(HttpStatus.OK.value());
        responseSuccessDto.setMessage("Producto encontrado con éxito");

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSuccessDto> createProducto(@RequestBody NewProductoDto newProductoDto){

        productoService.createNewProducto(newProductoDto);
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setMessage("Producto creado con éxito");
        responseSuccessDto.setCode(HttpStatus.OK.value());

        return new ResponseEntity<>(responseSuccessDto, HttpStatus.OK);
    }

}
