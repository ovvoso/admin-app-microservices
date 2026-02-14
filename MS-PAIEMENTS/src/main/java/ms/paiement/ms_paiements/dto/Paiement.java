package ms.paiement.ms_paiements.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {
     private Long id;

    @NotNull(message = "Le montant ne doit pas être null")
    @Positive(message = "Le montant doit être positif")
    private Double montant;

    @NotNull(message = "La date ne doit pas être null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "L'identifiant de la commande ne doit pas être null")
    @Positive(message = "L'identifiant de la commande doit être positif")
    private Long idCommande;
}
