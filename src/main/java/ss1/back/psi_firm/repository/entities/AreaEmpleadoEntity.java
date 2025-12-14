package ss1.back.psi_firm.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "area_empleado")
public class AreaEmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private AreaEntity area;

    @ManyToOne
    @JoinColumn(name = "dpi_empleado", nullable = false)
    private EmpleadoEntity empleado;
}

