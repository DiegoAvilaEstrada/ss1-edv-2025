package ss1.back.psi_firm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewFacturaDto {

    private String dpiPaciente;
    private Integer idTratamiento;
    private LocalDate fechaEmision;
    private BigDecimal montoTotal;

}

