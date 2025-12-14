package ss1.back.psi_firm.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ss1.back.psi_firm.repository.entities.TipoProductoEntity;

@Repository
public interface TipoProductoCrud extends JpaRepository<TipoProductoEntity, Integer> {
}

