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
@Table(name = "cuenta_empleado")
public class CuentaEmpleadoEntity {

    @Id
    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "dpi_empleado", nullable = false)
    private EmpleadoEntity empleado;
}

