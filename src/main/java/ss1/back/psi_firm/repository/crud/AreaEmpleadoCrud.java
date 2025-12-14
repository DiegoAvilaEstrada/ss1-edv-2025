package ss1.back.psi_firm.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ss1.back.psi_firm.repository.entities.AreaEmpleadoEntity;

@Repository
public interface AreaEmpleadoCrud extends JpaRepository<AreaEmpleadoEntity, Integer> {
}

