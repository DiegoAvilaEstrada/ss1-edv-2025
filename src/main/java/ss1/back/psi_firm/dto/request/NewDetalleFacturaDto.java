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
public class NewDetalleFacturaDto {

    private Integer idFactura;
    private Integer idProducto;
    private Integer cantidad;
    private BigDecimal costoTotal;

}

