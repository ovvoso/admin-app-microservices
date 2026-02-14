package ms.msproduit.ms_produits.mapping;

import org.mapstruct.Mapper;
import ms.msproduit.ms_produits.dto.Produit;
import ms.msproduit.ms_produits.entities.ProduitEntity;

@Mapper
public interface ProduitMapper {
    Produit toProduit(ProduitEntity produitEntity);
    ProduitEntity fromProduit(Produit produit);
}
