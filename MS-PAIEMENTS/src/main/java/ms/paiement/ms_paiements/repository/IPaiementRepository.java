package ms.paiement.ms_paiements.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import ms.paiement.ms_paiements.entities.PaiementEntity;

public interface IPaiementRepository extends JpaRepository<PaiementEntity, Long>{

}
