package mg.inclusiv.mihary.repository;

import mg.inclusiv.mihary.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByReferenceProduit(Integer reference);


}
