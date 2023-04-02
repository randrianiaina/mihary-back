package mg.inclusiv.mihary.repository;

import mg.inclusiv.mihary.entity.Approvisionnement;
import mg.inclusiv.mihary.entity.Produit;
import mg.inclusiv.mihary.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {

        String  query ="SELECT a.quantiteApprovisionnement, a.dateApprovisionnement, a.prixUnitaire, u.nomUtilisateur, p.nomProduit " +
                "FROM Approvisionnement a " +
                "INNER JOIN a.utilisateur u " +
                "INNER JOIN a.produit p";
        @Query(query)
        List<Object[]> getAllApprovisionnementsWithProductName();
    @Query("SELECT a.quantiteApprovisionnement, a.dateApprovisionnement, a.prixUnitaire, u.nomUtilisateur, p.nomProduit " +
            "FROM Approvisionnement a " +
            "INNER JOIN a.utilisateur u " +
            "INNER JOIN a.produit p " +
            "WHERE u.id = :userId")
    List<Object[]> getAllApprovisionnementsWithUtilisateurId(@Param("userId") Integer userId);
    @Query("SELECT a.idApprovisionnement, a.quantiteApprovisionnement ,a.dateApprovisionnement, a.prixUnitaire, u.nomUtilisateur,u.id, p.nomProduit,a.statutPaiement " +
            "FROM Approvisionnement a " +
            "INNER JOIN a.utilisateur u " +
            "INNER JOIN a.produit p " +
            "WHERE u.cooperative.id = :userId")
    List<Object[]> getAllApprovisionnementsWithCooperativeId(@Param("userId") Integer userId);
    @Query("SELECT a.quantiteApprovisionnement, a.dateApprovisionnement, a.prixUnitaire, u.nomUtilisateur, p.nomProduit " +
            "FROM Approvisionnement a " +
            "INNER JOIN a.utilisateur u " +
            "INNER JOIN a.produit p " +
            "WHERE u.id = :userId AND a.dateApprovisionnement = :dateApprovisionnement")
    List<Object[]> getAllApprovisionnementsWithProductNameByUserIdAndDate(@Param("userId") Integer userId, @Param("dateApprovisionnement") LocalDate dateApprovisionnement);

    @Query("SELECT a.quantiteApprovisionnement, a.dateApprovisionnement, a.prixUnitaire, u.nomUtilisateur, p.nomProduit " +
            "FROM Approvisionnement a " +
            "INNER JOIN a.utilisateur u " +
            "INNER JOIN a.produit p " +
            "WHERE u.id = :userId AND a.dateApprovisionnement BETWEEN :startDate AND :endDate")
    List<Object[]> getAllApprovisionnementsWithProductNameByUserIdAndDateInter(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    List<Approvisionnement> findByUtilisateur(Utilisateur utilisateur);
    List<Approvisionnement> findByProduit(Produit produit);


    @Query("SELECT a FROM Approvisionnement a " +
            "JOIN a.produit p " +
            "JOIN a.utilisateur u " +
            "JOIN u.cooperative c " +
            "WHERE p.idProduit = :idProduit " +
            "AND c.id = :idCooperative")
    List<Approvisionnement>getAllApprovisionnementsWithProductIdAndUserId (@Param("idCooperative") Integer idCooperative,@Param("idProduit") Long idProduit);

}
