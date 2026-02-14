package ms.msproduit.ms_produits.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ms.msproduit.ms_produits.dto.Produit;
import ms.msproduit.ms_produits.service.impl.ProduitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produits")
@RequiredArgsConstructor

public class ProduitController {
    private final ProduitService produitService;

    @GetMapping
    public ResponseEntity<List<Produit>> getProduits() {
        return new ResponseEntity<>(produitService.getProduits(), HttpStatus.OK);
    }

    @GetMapping("/{ref}")
    public ResponseEntity<Produit> getProduit(@PathVariable String ref) {
        return new ResponseEntity<>(produitService.getProduit(ref), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Produit> createProduit(@Valid @RequestBody Produit produit) {
        return new ResponseEntity<>(produitService.createProduit(produit), HttpStatus.CREATED);
    }

    @PutMapping("/{ref}")
    public ResponseEntity<Produit> updateProduit(@PathVariable String ref, @Valid @RequestBody Produit produit) {
        return new ResponseEntity<>(produitService.updateProduit(ref, produit), HttpStatus.OK);
    }

    @DeleteMapping("/{ref}")
    public ResponseEntity<String> deleteProduit(@PathVariable String ref) {
        produitService.deleteProduit(ref);
        return new ResponseEntity<>("Le produit avec la reference : " + ref + "  est supprimée avec succès",
                HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public Page<Produit> getProduits(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size); // Création de Pageable avec page et size
        return produitService.findAll(pageable);
    }

}
