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
@Table(name = "paciente")
public class PacienteEntity {

    @Id
    @Column(name = "dpi", length = 13)
    private String dpi;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;

    @Column(name = "telefono", length = 8)
    private String telefono;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "nit", length = 14)
    private String nit;
}

