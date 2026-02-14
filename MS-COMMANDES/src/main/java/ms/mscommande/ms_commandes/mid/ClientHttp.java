package ms.mscommande.ms_commandes.mid;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ms.mscommande.ms_commandes.dto.Produit;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientHttp implements IClientHttp {
         /** The rest template. */
    private final RestTemplate restTemplate;

    @Override
    public Produit getProduitByRef(String ref) {
        String url = "http://localhost:5173/produits/" + ref;

        final ResponseEntity<Produit> response = this.restTemplate.exchange(url,
                HttpMethod.GET,
                null, // pas de body ni headers pour GET
                Produit.class);

        return response.getBody();
    }

}
