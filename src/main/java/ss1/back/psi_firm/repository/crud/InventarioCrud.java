package ss1.back.psi_firm.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ss1.back.psi_firm.repository.entities.InventarioEntity;

import java.util.Optional;

@Repository
public interface InventarioCrud extends JpaRepository<InventarioEntity, Integer> {

    @Query(value = "SELECT * FROM inventario WHERE id_producto = ? ;", nativeQuery = true)
    Optional<InventarioEntity> findByProductId(Integer integer);
}

