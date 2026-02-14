package ms.mscommande.ms_commandes.service.impl;

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


import ms.mscommande.ms_commandes.dto.Commande;
import ms.mscommande.ms_commandes.repository.ICommandeRepository;
import ms.mscommande.ms_commandes.dto.Produit;
import ms.mscommande.ms_commandes.entities.CommandeEntity;
import ms.mscommande.ms_commandes.exception.RequestException;
import ms.mscommande.ms_commandes.mapping.CommandeMapper;
import ms.mscommande.ms_commandes.mid.IClientHttp;
import ms.mscommande.ms_commandes.service.ICommandeService;




import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "commandes")
public class CommandeService implements ICommandeService  {
    private final ICommandeRepository iCommandeRepository;
    private final CommandeMapper commandeMapper;
    private final MessageSource messageSource;
    private final IClientHttp clientHttp;

    @Transactional(readOnly = true)
    public List<Commande> getCommandes() {
        return StreamSupport.stream(iCommandeRepository.findAll().spliterator(), false)
                .map(commandeMapper::toCommande)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public Commande getCommande(Long id) {
        return commandeMapper.toCommande(iCommandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("commande.notfound", new Object[] { id },
                                Locale.getDefault()))));
    }

    @Transactional
    public Commande createCommande(Commande commande) {
        Produit produit = clientHttp.getProduitByRef(commande.getRefProduits());

        if (produit == null) {
            throw new RequestException(
                    messageSource.getMessage("produit.notfound", new Object[] { commande.getRefProduits() },
                            Locale.getDefault()),
                    HttpStatus.BAD_REQUEST);
        }

        return commandeMapper.toCommande(iCommandeRepository.save(commandeMapper.fromCommande(commande)));
    }

    @CachePut(key = "#id")
    @Transactional
    public Commande updateCommande(Long id, Commande commande) {
        Produit produit = clientHttp.getProduitByRef(commande.getRefProduits());

        if (produit == null) {
            throw new RequestException(
                    messageSource.getMessage("produit.notfound", new Object[] { commande.getRefProduits() },
                            Locale.getDefault()),
                    HttpStatus.BAD_REQUEST);
        }

        return iCommandeRepository.findById(id)
                .map(entity -> {
                    entity.setDate(commande.getDate());
                    entity.setPrix(commande.getPrix());
                    entity.setRefProduits(commande.getRefProduits());
                    entity.setQuantite(commande.getQuantite());
                    return commandeMapper.toCommande(iCommandeRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("commande.notfound", new Object[] { id },
                                Locale.getDefault())));
    }

    @CacheEvict(key = "#id")
    @Transactional
    public void deleteCommande(Long id) {
        CommandeEntity entity = iCommandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("commande.notfound", new Object[] { id },
                                Locale.getDefault())));
        try {
            iCommandeRepository.delete(entity);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("commande.errordeletion", new Object[] { id },
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}
