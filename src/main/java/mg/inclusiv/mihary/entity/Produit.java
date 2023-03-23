package mg.inclusiv.mihary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

@Data
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
    private Blob photoProduit;

    @Column(name = "stockProduit" )
    private Integer stockProduit;

    @Column(name = "categorieProduit", length = 30)
    private String categorieProduit;

    @Column(name = "referenceProduit", length = 30,unique = true)
    private String referenceProduit;

    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private List<Approvisionnement> approvisionnements;

    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private List<LigneCommande> lignesDeCommande;

    // Getters et setters
}

