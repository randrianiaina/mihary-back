package mg.inclusiv.mihary.service;

import lombok.RequiredArgsConstructor;
import mg.inclusiv.mihary.entity.UserCreateRequest;
import mg.inclusiv.mihary.entity.Utilisateur;
import mg.inclusiv.mihary.exception.UtilisateurNotFoundException;
import mg.inclusiv.mihary.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur findById(Integer id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            return utilisateur.get();
        } else {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
    }
    //private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordEncoder passwordEncoder;

    public Utilisateur readUserByUsername (String username) {
        Utilisateur optionalUser = utilisateurRepository.findByLogin(username);
        if (optionalUser !=null) {
            return optionalUser;
        } else {
            throw new EntityNotFoundException();
        }
    }

    /* public Utilisateur readUserByUsername (String username) {
         Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByUsername(username);
         if (optionalUtilisateur.isPresent()) {
             return optionalUtilisateur.get();
         } else {
             throw new EntityNotFoundException("Utilisateur with username " + username + " not found.");
         }
     }
  */
    public void createUser(UserCreateRequest userCreateRequest) {
        Utilisateur user = new Utilisateur();
        //Utilisateur existingUser = utilisateurRepository.findByLogin(userCreateRequest.getLogin())
          //      .orElse(null);
        //if (existingUser != null) {
        Utilisateur byUsername = utilisateurRepository.findByLogin(userCreateRequest.getLogin());
        if (byUsername !=null) {
            throw new RuntimeException("Utilisateur existant, veuillez choisir un autre nom.");
        }

        user.setLogin(userCreateRequest.getLogin());
        user.setNomUtilisateur(userCreateRequest.getNom());
        user.setPrenomUtilisateur(userCreateRequest.getPrenom());
        user.setTelephoneUtilisateur(userCreateRequest.getTelephone());
        user.setEmail(userCreateRequest.getEmailUtilisateur());
        user.setTypeUtilisateur(userCreateRequest.getType());
        user.setMdpUtilisateur(passwordEncoder.encode(userCreateRequest.getMdpUtilisateur()));
        //user.setMdpUtilisateur(userCreateRequest.getMdpUtilisateur());
        utilisateurRepository.save(user);
    }

    public Utilisateur  save(Utilisateur utilisateur, MultipartFile photo) throws IOException {
        if (photo != null && !photo.isEmpty()) {
            utilisateur.setPhotoUtilisateur(photo.getBytes());
        }
        return utilisateurRepository.save(utilisateur);
    }
    public Utilisateur  update(Utilisateur utilisateur){

        return utilisateurRepository.save(utilisateur);
    }



    public void deleteById(Integer id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            utilisateurRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
    }
    public Integer findCooperativeIdByIdAgriculteur(Integer idAgriculteur) {
        Utilisateur agriculteur = utilisateurRepository.findByIdAndTypeUtilisateur(idAgriculteur, Utilisateur.TypeUtilisateur.AGRICULTEUR);
        return agriculteur.getCooperative().getId();
    }


    public List<Utilisateur> findAllCooperatives() {
        return utilisateurRepository.findByTypeUtilisateur(Utilisateur.TypeUtilisateur.COOPERATIVE);
    }

    public List<Utilisateur> findAllAgriculteurs() {
        return utilisateurRepository.findByTypeUtilisateur(Utilisateur.TypeUtilisateur.AGRICULTEUR);
    }

    public List<Utilisateur> findAgriculteursByCooperativeId(Integer id) {
        return utilisateurRepository.findByCooperativeIdAndTypeUtilisateur(id, Utilisateur.TypeUtilisateur.AGRICULTEUR);
    }

    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur update(Integer id, Utilisateur utilisateur) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur existingUtilisateur = optionalUtilisateur.get();
            existingUtilisateur.setNomUtilisateur(utilisateur.getNomUtilisateur());
            existingUtilisateur.setPrenomUtilisateur(utilisateur.getPrenomUtilisateur());
            existingUtilisateur.setLogin(utilisateur.getLogin());
            existingUtilisateur.setMdpUtilisateur(utilisateur.getMdpUtilisateur());
            existingUtilisateur.setAdresseUtilisateur(utilisateur.getAdresseUtilisateur());
            existingUtilisateur.setTelephoneUtilisateur(utilisateur.getTelephoneUtilisateur());
            existingUtilisateur.setEmail(utilisateur.getEmail());
            existingUtilisateur.setCooperative(utilisateur.getCooperative());
            return utilisateurRepository.save(existingUtilisateur);
        } else {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
    }
    public Utilisateur updateSolde(Integer id, double montant) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));

        double nouveauSolde = utilisateur.getSoldeUtilisateur() + montant;
        utilisateur.setSoldeUtilisateur(nouveauSolde);

        return utilisateurRepository.save(utilisateur);
    }
    public Utilisateur login(String username, String password) {
        Utilisateur utilisateur = readUserByUsername(username);
        if (!passwordEncoder.matches(password, utilisateur.getMdpUtilisateur())) {
          throw new RuntimeException("Mot de passe incorrect pour l'utilisateur avec le login : " + username);
       }
//        if (password != utilisateur.getMdpUtilisateur()) {
//            throw new RuntimeException("Mot de passe incorrect pour l'utilisateur avec le login : " + username);
//        }

        return utilisateur;
    }
    public Utilisateur readUserByMail(String mail) {
        Optional<Utilisateur> optionalUtilisateur = Optional.ofNullable(utilisateurRepository.findByEmail(mail));
        if (optionalUtilisateur.isPresent()) {
            return optionalUtilisateur.get();
        } else {
            throw new UtilisateurNotFoundException(mail);
        }
    }

//    public Utilisateur authentification(String mail, String password) {
//        Utilisateur utilisateur = readUserByMail(mail);
////        if (!passwordEncoder.matches(password, utilisateur.getMdpUtilisateur())) {
////            throw new RuntimeException("Mot de passe incorrect pour l'utilisateur avec l'adresse mail, : " + mail);
////        }
//        if (password != utilisateur.getMdpUtilisateur()) {
//            throw new RuntimeException("Mot de passe incorrect pour l'utilisateur avec l'adresse mail, : " + mail);
//        }
//        return utilisateur;
//    }
}