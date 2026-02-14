package ms.mscommande.ms_commandes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    @NotNull(message = "La reference ne doit pas être null")
    @NotBlank(message = "La reference ne doit pas être vide")
    private String ref;
    @NotNull(message = "Le nom ne doit pas être null")
    private String nom;
    @NotNull
    private double stock;

}
