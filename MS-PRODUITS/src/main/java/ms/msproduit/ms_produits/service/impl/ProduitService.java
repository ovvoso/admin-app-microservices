package ms.msproduit.ms_produits.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import ms.msproduit.ms_produits.service.IProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.msproduit.ms_produits.repository.IProduitRepository;
import ms.msproduit.ms_produits.dto.Produit;
import ms.msproduit.ms_produits.entities.ProduitEntity;
import ms.msproduit.ms_produits.exception.EntityNotFoundException;
import ms.msproduit.ms_produits.exception.RequestException;
import ms.msproduit.ms_produits.mapping.ProduitMapper;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "produits")
public class ProduitService implements IProduitService{
    private final IProduitRepository iProduitRepository;
    private final ProduitMapper produitMapper;
    private final MessageSource messageSource;

   /* public ProduitService(IProduitRepository iProduitRepository, ProduitMapper produitMapper,
            MessageSource messageSource) {
        this.iProduitRepository = iProduitRepository;
        this.produitMapper = produitMapper;
        this.messageSource = messageSource;
    }*/

    @Transactional(readOnly = true)
    public List<Produit> getProduits() {
        return iProduitRepository.findAll().stream()
                .map(produitMapper::toProduit)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#ref")
    @Transactional(readOnly = true)
    public Produit getProduit(String ref) {
        return produitMapper.toProduit(iProduitRepository.findByRef(ref)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("produit.notfound", new Object[] { ref },
                                Locale.getDefault()))));
    }

    @Transactional
    public Produit createProduit(Produit produit) {
        Optional<ProduitEntity> entity = iProduitRepository.findByRef(produit.getRef());

        if (entity.isPresent()) {
            throw new RequestException(messageSource.getMessage("produit.exists", new Object[] { produit.getRef() },
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }

        return produitMapper.toProduit(iProduitRepository.save(produitMapper.fromProduit(produit)));
    }

    @CachePut(key = "#ref")
    @Transactional
    public Produit updateProduit(String ref, Produit produit) {
        return iProduitRepository.findByRef(ref)
                .map(entity -> {
                    produit.setRef(ref);
                    entity.setNom(produit.getNom());
                    entity.setStock(produit.getStock());
                    return produitMapper.toProduit(iProduitRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("produit.notfound", new Object[] { ref },
                                Locale.getDefault())));
    }

    @CacheEvict(key = "#ref")
    @Transactional
    public void deleteProduit(String ref) {
        ProduitEntity entity = iProduitRepository.findByRef(ref)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("produit.notfound", new Object[] { ref },
                                Locale.getDefault())));
        try {
            iProduitRepository.delete(entity);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("produit.errordeletion", new Object[] { ref },
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }

    @Override
    public Page<Produit> findAll(Pageable pageable) {
        Page<ProduitEntity> produitPage = iProduitRepository.findAll(pageable);

        return produitPage.map(produitMapper::toProduit);
    }

}
