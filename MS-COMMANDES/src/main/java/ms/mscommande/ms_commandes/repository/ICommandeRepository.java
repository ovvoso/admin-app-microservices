package ms.mscommande.ms_commandes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ms.mscommande.ms_commandes.entities.CommandeEntity;

public interface ICommandeRepository extends JpaRepository<CommandeEntity, Long> {

}
