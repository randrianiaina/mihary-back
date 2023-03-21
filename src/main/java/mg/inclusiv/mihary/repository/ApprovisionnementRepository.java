package mg.inclusiv.mihary.repository;

import mg.inclusiv.mihary.entity.Approvisionnement;
import mg.inclusiv.mihary.entity.Produit;
import mg.inclusiv.mihary.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {
    List<Approvisionnement> findByUtilisateur(Utilisateur utilisateur);
    List<Approvisionnement> findByProduit(Produit produit);


}
