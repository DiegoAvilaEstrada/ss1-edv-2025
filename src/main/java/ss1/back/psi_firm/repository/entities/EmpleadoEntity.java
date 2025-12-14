package ss1.back.psi_firm.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class EmpleadoEntity {

    @Id
    @Column(name = "dpi", length = 13)
    private String dpi;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "apellido", length = 50)
    private String apellido;

    @Column(name = "telefono", length = 8)
    private String telefono;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "salario")
    private Integer salario;

    @ManyToOne
    @JoinColumn(name = "id_rol_empleado", nullable = false)
    private RolEntity rolEmpleado;

    @Column(name = "descuento_igss", precision = 5, scale = 2)
    private BigDecimal descuentoIgss;
}

