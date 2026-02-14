package ms.paiement.ms_paiements.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.paiement.ms_paiements.repository.IPaiementRepository;
import ms.paiement.ms_paiements.dto.Paiement;
import ms.paiement.ms_paiements.entities.PaiementEntity;
import ms.paiement.ms_paiements.exception.RequestException;
import ms.paiement.ms_paiements.mapping.PaiementMapper;
import ms.paiement.ms_paiements.service.IPaiementService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "paiements")
public class PaiementService implements IPaiementService {
    private final IPaiementRepository iPaiementRepository;
    private final PaiementMapper paiementMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public List<Paiement> getPaiements() {
        return StreamSupport.stream(iPaiementRepository.findAll().spliterator(), false)
                .map(paiementMapper::toPaiement)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public Paiement getPaiement(Long id) {
        return paiementMapper.toPaiement(iPaiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("paiement.notfound", new Object[] { id },
                                Locale.getDefault()))));
    }

    @Transactional
    public Paiement createPaiement(Paiement paiement) {
        return paiementMapper.toPaiement(iPaiementRepository.save(paiementMapper.fromPaiement(paiement)));
    }

    @CachePut(key = "#id")
    @Transactional
    public Paiement updatePaiement(Long id, Paiement paiement) {
        return iPaiementRepository.findById(id)
                .map(entity -> {
                    entity.setMontant(paiement.getMontant());
                    entity.setDate(paiement.getDate());
                    entity.setIdCommande(paiement.getIdCommande());
                    return paiementMapper.toPaiement(iPaiementRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("paiement.notfound", new Object[] { id },
                                Locale.getDefault())));
    }

    @CacheEvict(key = "#id")
    @Transactional
    public void deletePaiement(Long id) {
        PaiementEntity entity = iPaiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("paiement.notfound", new Object[] { id },
                                Locale.getDefault())));
        try {
            iPaiementRepository.delete(entity);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("paiement.errordeletion", new Object[] { id },
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}
