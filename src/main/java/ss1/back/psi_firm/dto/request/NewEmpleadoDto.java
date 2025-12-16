package ss1.back.psi_firm.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewEmpleadoDto {

    private String dpi;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private Integer salario;
    private Integer idRolEmpleado;
    private BigDecimal descuentoIgss;

}
