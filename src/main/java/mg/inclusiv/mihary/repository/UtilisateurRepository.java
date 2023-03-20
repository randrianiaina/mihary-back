package mg.inclusiv.mihary.repository;

import mg.inclusiv.mihary.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
   // Utilisateur findByLogin(String login);
    Utilisateur findByEmail(String email);
    Boolean existsByLogin(String login);

    Utilisateur findByLogin(String login);


    List<Utilisateur> findByTypeUtilisateur(Utilisateur.TypeUtilisateur typeUtilisateur);
    List<Utilisateur> findByCooperativeIdAndTypeUtilisateur(Integer cooperativeId, Utilisateur.TypeUtilisateur typeUtilisateur);
}
