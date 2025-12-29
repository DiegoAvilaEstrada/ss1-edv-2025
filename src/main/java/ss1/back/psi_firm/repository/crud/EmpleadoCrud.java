package ss1.back.psi_firm.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ss1.back.psi_firm.repository.entities.EmpleadoEntity;

import java.util.List;

@Repository
public interface EmpleadoCrud extends JpaRepository<EmpleadoEntity, String> {
    
    List<EmpleadoEntity> findByRolEmpleadoRol(String rol);
}

