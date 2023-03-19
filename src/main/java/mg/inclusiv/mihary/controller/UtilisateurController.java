package mg.inclusiv.mihary.controller;

import mg.inclusiv.mihary.entity.UserCreateRequest;
import mg.inclusiv.mihary.entity.Utilisateur;
import mg.inclusiv.mihary.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {private final UtilisateurService utilisateurService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.utilisateurService = utilisateurService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping("/restricted")
    public String restrictedAccessEndpoint() {
        return "Bienvenue, veuillez-vous authentifier, merci";
    }

    @GetMapping("/{id}")
    public Utilisateur findById(@PathVariable Integer id) {
        return utilisateurService.findById(id);
    }

    @GetMapping("/list")
    public List<Utilisateur> findAll() {
        return utilisateurService.findAll();
    }

    @PostMapping("/ajout")
    public Utilisateur create(@RequestBody @Valid Utilisateur utilisateur) {
        String encodedPassword = bCryptPasswordEncoder.encode(utilisateur.getMdpUtilisateur());
        utilisateur.setMdpUtilisateur(encodedPassword);
        return utilisateurService.save(utilisateur);
    }

    @PutMapping("/modifier/{id}")
    public Utilisateur update(@PathVariable Long id, @RequestBody @Valid Utilisateur utilisateur) {
        utilisateur.setId(id);
        return utilisateurService.save(utilisateur);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Integer id) {
        utilisateurService.deleteById(id);
    }

    @GetMapping("/cooperatives")
    public List<Utilisateur> findAllCooperatives() {
        return utilisateurService.findAllCooperatives();
    }

    @GetMapping("/agriculteurs")
    public List<Utilisateur> findAllAgriculteurs() {
        return utilisateurService.findAllAgriculteurs();
    }

    @GetMapping("/cooperatives/{id}/agriculteurs")
    public List<Utilisateur> findAgriculteursByCooperativeId(@PathVariable Integer id) {
        return utilisateurService.findAgriculteursByCooperativeId(id);
    }

    @PostMapping("/user")
    public ResponseEntity createUser (@RequestBody UserCreateRequest userCreateRequest) {
        utilisateurService.createUser(userCreateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{login}")
    public Utilisateur readUserByUsername(@PathVariable String login) {
        return utilisateurService.readUserByUsername(login);
    }

    @GetMapping("/email/{email}")
    public Utilisateur findByEmail(@PathVariable String email) {
        return utilisateurService.findByEmail(email);
    }

    @PutMapping("/{id}/solde")
    public Utilisateur updateSolde(@PathVariable Integer id, @RequestParam double montant) {
        return utilisateurService.updateSolde(id, montant);
    }

}