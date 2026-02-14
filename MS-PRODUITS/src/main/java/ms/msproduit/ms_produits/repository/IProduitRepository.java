package ms.msproduit.ms_produits.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ms.msproduit.ms_produits.entities.ProduitEntity;

public interface IProduitRepository extends JpaRepository<ProduitEntity, String> {
    Optional<ProduitEntity> findByRef(String ref);
    Page<ProduitEntity> findAll(Pageable pageable);
}


