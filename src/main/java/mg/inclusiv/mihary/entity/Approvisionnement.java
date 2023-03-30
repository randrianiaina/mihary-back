package mg.inclusiv.mihary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@org.hibernate.annotations.Proxy(lazy=false)
@Entity
@Table(name = "approvisionnement")
public class Approvisionnement {
    @Id
    @Column(name = "idApprovisionnement",nullable = false,length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApprovisionnement;

    @ManyToOne()
    @JoinColumn(name = "utilisateur_idUtilisateur",referencedColumnName = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne()
    @JoinColumn(name = "produit_idProduit",referencedColumnName = "idProduit")
    private Produit produit;

    @Column(name="quantiteApprovisionnement", nullable=false)
    private Integer quantiteApprovisionnement;

    @Column(name="prixUnitaire", nullable=false)
    private BigDecimal prixUnitaire;

    @Column(name="dateApprovisionnement", nullable=false)
    private LocalDate dateApprovisionnement;


    @Column(name="statutPaiement", nullable=false, columnDefinition = "varchar(255) default 'non payé'")
    private String statutPaiement;
}
