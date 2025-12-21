package ss1.back.psi_firm.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ss1.back.psi_firm.repository.entities.CodigoSesionEmpleadoEntity;

import java.util.Optional;

@Repository
public interface CodigoSesionEmpleadoCrud extends JpaRepository<CodigoSesionEmpleadoEntity, Integer> {
    
    Optional<CodigoSesionEmpleadoEntity> findByCodigo(String codigo);
}

