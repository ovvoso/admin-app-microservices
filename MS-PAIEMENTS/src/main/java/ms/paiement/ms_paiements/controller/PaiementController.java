package ms.paiement.ms_paiements.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ms.paiement.ms_paiements.dto.Paiement;
import ms.paiement.ms_paiements.service.impl.PaiementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {
    private final PaiementService paiementService;

    @GetMapping
    public ResponseEntity<List<Paiement>> getPaiements() {
        return new ResponseEntity<>(paiementService.getPaiements(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiement(@PathVariable Long id) {
        return new ResponseEntity<>( paiementService.getPaiement(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Paiement> createPaiement(@Valid @RequestBody Paiement paiement) {
        return new ResponseEntity<>( paiementService.createPaiement(paiement), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paiement> updatePaiement(@PathVariable Long id, @Valid @RequestBody Paiement paiement) {
        return new ResponseEntity<>( paiementService.updatePaiement(id, paiement), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return new ResponseEntity<>("Le paiement avec l'id : " + id + "  est supprimé avec succès", HttpStatus.OK);
    }
}
