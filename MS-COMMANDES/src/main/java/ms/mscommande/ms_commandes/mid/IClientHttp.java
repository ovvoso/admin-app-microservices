package ms.mscommande.ms_commandes.mid;

import ms.mscommande.ms_commandes.dto.Produit;

public interface IClientHttp {
    Produit getProduitByRef(String ref);
}
