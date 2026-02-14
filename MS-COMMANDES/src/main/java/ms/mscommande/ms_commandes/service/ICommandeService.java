package ms.mscommande.ms_commandes.service;

import java.util.List;
import ms.mscommande.ms_commandes.dto.Commande;

public interface ICommandeService {
    List<Commande> getCommandes();

    Commande getCommande(Long id);

    Commande createCommande(Commande commande);

    Commande updateCommande(Long id, Commande commande);

    void deleteCommande(Long id);
}
