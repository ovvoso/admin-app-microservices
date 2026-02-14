package ms.mscommande.ms_commandes.mapping;

import org.mapstruct.Mapper;

import ms.mscommande.ms_commandes.dto.Commande;
import ms.mscommande.ms_commandes.entities.CommandeEntity;


@Mapper
public interface CommandeMapper {
    Commande toCommande(CommandeEntity commandeEntity);

    CommandeEntity fromCommande(Commande commande);
}

