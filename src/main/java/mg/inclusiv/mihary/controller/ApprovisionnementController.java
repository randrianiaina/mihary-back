package mg.inclusiv.mihary.controller;


import mg.inclusiv.mihary.entity.Approvisionnement;
import mg.inclusiv.mihary.entity.Produit;
import mg.inclusiv.mihary.entity.Utilisateur;
import mg.inclusiv.mihary.service.ApprovisionnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/approvisionnements")
public class ApprovisionnementController {

    @Autowired
    private ApprovisionnementService approvisionnementService;

    @GetMapping("")
    public List<Approvisionnement> getAllApprovisionnements() {
        return approvisionnementService.getAllApprovisionnements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Approvisionnement> getApprovisionnementById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Approvisionnement approvisionnement = approvisionnementService.getApprovisionnementById(id);
        return ResponseEntity.ok().body(approvisionnement);
    }

    @PostMapping("")
    public Approvisionnement createApprovisionnement(@Valid @RequestBody Approvisionnement approvisionnement) {
        return approvisionnementService.saveApprovisionnement(approvisionnement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Approvisionnement> updateApprovisionnement(@PathVariable(value = "id") Long id,
                                                                     @Valid @RequestBody Approvisionnement approvisionnementDetails) throws ResourceNotFoundException {
        Approvisionnement updatedApprovisionnement = approvisionnementService.updateApprovisionnement(id, approvisionnementDetails);
        return ResponseEntity.ok(updatedApprovisionnement);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteApprovisionnement(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        approvisionnementService.deleteApprovisionnement(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/{id}/produits")
    public List<Produit> getProduitsByApprovisionnement(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Approvisionnement approvisionnement = approvisionnementService.getApprovisionnementById(id);
        return approvisionnementService.getProduitsByApprovisionnement(approvisionnement);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Approvisionnement> getApprovisionnementsByUtilisateur(@PathVariable(value = "utilisateurId") Integer utilisateurId) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurId);
        return approvisionnementService.getApprovisionnementsByUtilisateur(utilisateur);
    }
}
