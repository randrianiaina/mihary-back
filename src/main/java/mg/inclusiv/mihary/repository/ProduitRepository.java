package mg.inclusiv.mihary.repository;

import mg.inclusiv.mihary.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query("SELECT p FROM Produit p WHERE p.referenceProduit = ?1")
    List<Produit> findByReferenceProduit(Integer referenceProduit);

    @Query("SELECT p FROM Produit p WHERE p.referenceProduit IN "
            + "(SELECT u.id FROM Utilisateur u WHERE u.cooperative.id = ?1)")
    List<Produit> findAllByCooperativeId(Integer idCooperative);





}