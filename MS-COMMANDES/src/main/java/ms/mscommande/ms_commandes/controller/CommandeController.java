package ms.mscommande.ms_commandes.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ms.mscommande.ms_commandes.dto.Commande;
import ms.mscommande.ms_commandes.service.impl.CommandeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/commandes")
@RequiredArgsConstructor
public class CommandeController {
     private final CommandeService commandeService;

    @GetMapping
    public ResponseEntity<List<Commande>> getCommandes() {
        return new ResponseEntity<>(commandeService.getCommandes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommande(@PathVariable Long id) {
        return new ResponseEntity<>(commandeService.getCommande(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Commande> createCommande(@Valid @RequestBody Commande commande) {
        return new ResponseEntity<>(commandeService.createCommande(commande), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @Valid @RequestBody Commande commande) {
        return new ResponseEntity<>(commandeService.updateCommande(id, commande), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return new ResponseEntity<>("La commande avec l'id : " + id + "  est supprimée avec succès",
                HttpStatus.OK);
    }

}
