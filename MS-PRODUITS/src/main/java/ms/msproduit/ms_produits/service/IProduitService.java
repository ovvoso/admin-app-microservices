package ms.msproduit.ms_produits.service;

import ms.msproduit.ms_produits.dto.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProduitService {
    List<Produit> getProduits();
    Produit getProduit(String ref);
    Produit createProduit(Produit produit);
    Produit updateProduit(String ref, Produit produit);
    void deleteProduit(String ref);
    Page<Produit> findAll(Pageable pageable);
}

