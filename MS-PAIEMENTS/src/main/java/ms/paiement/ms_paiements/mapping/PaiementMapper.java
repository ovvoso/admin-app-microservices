package ms.paiement.ms_paiements.mapping;

import org.mapstruct.Mapper;

import ms.paiement.ms_paiements.dto.Paiement;
import ms.paiement.ms_paiements.entities.PaiementEntity;

@Mapper
public interface PaiementMapper {
     Paiement toPaiement(PaiementEntity paiementEntity);

    PaiementEntity fromPaiement(Paiement paiement);
}
