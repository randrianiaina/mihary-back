package mg.inclusiv.mihary.repository;

import mg.inclusiv.mihary.entity.Approvisionnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {

        String  query ="SELECT a.quantiteApprovisionnement, a.dateApprovisionnement, a.prixUnitaire, u.nomUtilisateur, p.nomProduit " +
                "FROM Approvisionnement a " +
                "INNER JOIN a.utilisateur u " +
                "INNER JOIN a.produit p";
        @Query(query)
        List<Object[]> getAllApprovisionnementsWithProductName();
}
