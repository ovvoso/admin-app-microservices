package ms.mscommande.ms_commandes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    private Long id;

    @NotNull(message = "La date ne doit pas être null")
    private LocalDate date;

    @NotNull(message = "Le prix ne doit pas être null")
    @Positive(message = "Le prix doit être positif")
    private Integer prix;

    @NotNull(message = "La référence produits ne doit pas être null")
    @NotBlank(message = "La référence produits ne doit pas être vide")
    private String refProduits;

    @NotNull(message = "La quantité ne doit pas être null")
    @Positive(message = "La quantité doit être positive")
    private Double quantite;
}
