package ss1.back.psi_firm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewMedicacionDto {

    private Integer idTratamiento;
    private Integer idProducto;
    private String dosis;
    private String frecuencia;

}

