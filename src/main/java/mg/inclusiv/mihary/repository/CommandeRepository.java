package mg.inclusiv.mihary.repository;

import mg.inclusiv.mihary.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Long> {
    @Query("SELECT cl.nomUtilisateur, c.dateCommande, lc.quantiteLigneCommande, (p.prixProduit * lc.quantiteLigneCommande) "
            + "FROM Commande c "
            + "JOIN LigneCommande lc ON c.idCommande = lc.commande.idCommande "
            + "JOIN Produit p ON lc.produit.idProduit = p.idProduit "
            + "JOIN Utilisateur u ON p.referenceProduit = u.id "
            + "JOIN Utilisateur cl ON c.utilisateur.id = cl.id "
            + "WHERE c.statutCommande = 'traité' AND u.id = :userId AND lc.produit.idProduit = :produitId")
    List<Object[]> findCommandeDetails(@Param("userId") Integer userId, @Param("produitId") Long produitId);
    @Query("SELECT cl.nomUtilisateur, c.dateCommande, lc.quantiteLigneCommande,p.nomProduit,c.idCommande, (p.prixProduit * lc.quantiteLigneCommande) "
            + "FROM Commande c "
            + "JOIN LigneCommande lc ON c.idCommande = lc.commande.idCommande "
            + "JOIN Produit p ON lc.produit.idProduit = p.idProduit "
            + "JOIN Utilisateur u ON p.referenceProduit = u.id "
            + "JOIN Utilisateur cl ON c.utilisateur.id = cl.id "
            + "WHERE c.statutCommande = 'traité' AND u.id = :userId")
    List<Object[]> findCommandeDetailsByIdCoop(@Param("userId") Integer userId);
    @Query("SELECT DISTINCT c.idCommande "
            + "FROM Commande c "
            + "JOIN LigneCommande lc ON c.idCommande = lc.commande.idCommande "
            + "JOIN Produit p ON lc.produit.idProduit = p.idProduit "
            + "JOIN Utilisateur u ON p.referenceProduit = u.id "
            + "JOIN Utilisateur cl ON c.utilisateur.id = cl.id "
            + "WHERE c.statutCommande = 'traité' AND u.id = :userId")
    List<Object[]> findNombreCommande(@Param("userId") Integer userId);
}
