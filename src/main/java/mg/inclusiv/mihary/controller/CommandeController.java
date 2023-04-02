package mg.inclusiv.mihary.controller;

import mg.inclusiv.mihary.entity.Commande;
import mg.inclusiv.mihary.service.CommandeService;
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
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @GetMapping("/{userId}/{produitId}")
    public ResponseEntity<List<Object[]>> getCommandeDetails(
            @PathVariable Integer userId,
            @PathVariable Long produitId
    ) {
        List<Object[]> commandeDetails = commandeService.getCommandeDetails(userId, produitId);
        return ResponseEntity.ok(commandeDetails);
    }
    @GetMapping("details/{userId}")
    public ResponseEntity<List<Object[]>> getCommandeDetailsByIdCoop(
            @PathVariable Integer userId

    ) {
        List<Object[]> commandeDetails = commandeService.getCommandeDetailsByIdCoop(userId);
        return ResponseEntity.ok(commandeDetails);
    }
    @GetMapping("nombre/{userId}")
    public ResponseEntity<List<Object[]>> findNombreCommande(
            @PathVariable Integer userId

    ) {
        List<Object[]> commandeDetails = commandeService.findNombreCommande(userId);
        return ResponseEntity.ok(commandeDetails);
    }



    @PostMapping("/ajout")
    public ResponseEntity<Commande> createCommande(@Valid @RequestBody Commande commande) {
        Commande newCommande = commandeService.createCommande(commande);
        System.out.println(newCommande.getUtilisateur());
        return ResponseEntity.ok(newCommande);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable(value = "id") Long commandeId)
            throws ResourceNotFoundException {
        Commande commande = commandeService.getCommandeById(commandeId);
        return ResponseEntity.ok(commande);
    }

    @GetMapping("")
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable(value = "id") Long commandeId,
                                                   @RequestBody Commande commandeDetails) throws ResourceNotFoundException {
        Commande updatedCommande = commandeService.updateCommande(commandeId, commandeDetails);
        return ResponseEntity.ok(updatedCommande);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCommande(@PathVariable(value = "id") Long commandeId)
            throws ResourceNotFoundException {
        commandeService.deleteCommande(commandeId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}