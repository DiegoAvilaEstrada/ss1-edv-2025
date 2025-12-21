package ss1.back.psi_firm.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ss1.back.psi_firm.repository.entities.CodigoSesionPacienteEntity;

import java.util.Optional;

@Repository
public interface CodigoSesionPacienteCrud extends JpaRepository<CodigoSesionPacienteEntity, Integer> {
    
    Optional<CodigoSesionPacienteEntity> findByCodigo(String codigo);
}

