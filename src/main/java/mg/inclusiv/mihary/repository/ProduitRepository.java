package mg.inclusiv.mihary.repository;

import mg.inclusiv.mihary.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query("SELECT p FROM Produit p WHERE p.referenceProduit = ?1")
    List<Produit> findByReferenceProduit(Integer referenceProduit);


}
