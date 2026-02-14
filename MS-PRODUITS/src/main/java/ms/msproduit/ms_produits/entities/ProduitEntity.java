package ms.msproduit.ms_produits.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String ref;
    @Column(unique = true, nullable = false, length = 200)
    private String nom;
    @Column(nullable = false)
    private double stock;
}

