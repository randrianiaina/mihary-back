package mg.inclusiv.mihary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduit" )
    private Long idProduit;

    @Column(length = 50,name = "nomProduit")
    private String nomProduit;

    @Column(length = 200,name = "descriptionProduit")
    private String descriptionProduit;

    @Column(length = 50,name = "uniteProduit")
    private String uniteProduit;

    @Column(name = "prixProduit")
    private BigDecimal prixProduit;

    @Lob
    @Column(name = "photoProduit")
    private byte[] photoProduit;

    @Column(name = "stockProduit" )
    private Integer stockProduit;

    @Column(name = "categorieProduit", length = 30)
    private String categorieProduit;

    @Column(name = "referenceProduit")
    private Integer referenceProduit;

    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private List<Approvisionnement> approvisionnements;

    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private List<LigneCommande> lignesDeCommande;

    public Produit(String nomProduit, BigDecimal prixProduit, String categorieProduit, String uniteProduit, int stockProduit, String descriptionProduit, byte[] bytes, int referenceProduit) {
        this.nomProduit=nomProduit;
        this.prixProduit=prixProduit;
        this.categorieProduit=categorieProduit;
        this.uniteProduit=uniteProduit;
        this.stockProduit=stockProduit;
        this.descriptionProduit=descriptionProduit;
        this.photoProduit=bytes;
        this.referenceProduit=referenceProduit;
    }

    // Getters et setters
}

