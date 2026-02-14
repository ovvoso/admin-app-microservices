package ms.paiement.ms_paiements.service;

import java.util.List;

import ms.paiement.ms_paiements.dto.Paiement;

public interface IPaiementService {
    List<Paiement> getPaiements();

    Paiement getPaiement(Long id);

    Paiement createPaiement(Paiement paiement);

    Paiement updatePaiement(Long id, Paiement paiement);

    void deletePaiement(Long id);
}
