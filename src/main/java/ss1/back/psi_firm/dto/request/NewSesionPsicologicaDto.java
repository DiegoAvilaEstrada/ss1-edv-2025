package ss1.back.psi_firm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewSesionPsicologicaDto {

    private Integer idTratamiento;
    private LocalDate fechaSesion;
    private String observaciones;

}

