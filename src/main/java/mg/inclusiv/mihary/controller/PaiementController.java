package mg.inclusiv.mihary.controller;

import mg.inclusiv.mihary.entity.Commande;
import mg.inclusiv.mihary.entity.LigneCommande;
import mg.inclusiv.mihary.entity.Paiement;
import mg.inclusiv.mihary.entity.Produit;
import mg.inclusiv.mihary.service.LigneCommandeService;
import mg.inclusiv.mihary.service.PaiementService;
//----- Chargement pour JSON-------
import org.json.JSONException;
import org.json.JSONArray;
//----- ---------------------------
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private LigneCommandeService ligneCommandeService;

    @GetMapping("")
    public List<Paiement> getAllPaiements() {
        return paiementService.getAllPaiements();
    }

    @PostMapping("/valider")
    public ResponseEntity<Paiement> createPaiement(@RequestBody Object request) throws JSONException {

        //transformer l'objet obtenu en JSONObject
        JSONObject obj= new JSONObject((Map<String, String>) request);

        //-----------prendre les éléments pour l'objet paiement et insérer dans la table Paiement------------
        Paiement newPaiement = new Paiement();
        //Date Paiement
        newPaiement.setDatePaiement(LocalDate.parse(obj.getString("datePaiement")));
        //Mode de Paiement
        newPaiement.setModePaiement(obj.getString("modePaiement"));
        //Montant Paiement
        newPaiement.setMontantPaiement(BigDecimal.valueOf(obj.getDouble("montantPaiement")));
        //Recupérer l'ID de la commande
        Commande c = new Commande();
        c.setIdCommande(obj.getLong("idCommande"));
        newPaiement.setCommande(c);
        //Statut Paiement
        newPaiement.setStatutPaiement(obj.getString("statutPaiement"));
        newPaiement.setChargeId(obj.getString("idStripe"));
        //Insertion dans la base
        paiementService.createPaiement(newPaiement);
        //----------------------------------- fin ajout paiement ---------------------------------------------


        //-------------------------------------Ligne de commande-------------------------------------------
        //Fake objet appelé panier
        String harona = "{'panier': " + obj.get("panier") + "}";
        //Transformer "alefa" en JSONObject
        obj = new JSONObject(harona);
        //Convertir en Array de JSON
        JSONArray jsonArray = obj.getJSONArray("panier");
        Long id = c.getIdCommande();

        //Iteration
        for(int i = 0; i < jsonArray.length(); i++){
            //Convertir chaque array en Objet de type Json
            JSONObject object = new JSONObject(jsonArray.get(i).toString());
            //Creation de la LigneCommande
            LigneCommande ligneCommande = new LigneCommande();
            ligneCommande.setCommande(c);
            //Produit
            Produit p = new Produit();
            p.setIdProduit((long) object.getInt("id"));
            ligneCommande.setProduit(p);
            //Quantité ligneCommande
            ligneCommande.setQuantiteLigneCommande(object.getInt("quantity"));
            //Et Prix Unitaire
            ligneCommande.setPrixUnitaire(BigDecimal.valueOf(object.getDouble("price")));
            //Insertion
            ligneCommandeService.createLigneCommande(ligneCommande);
        }
        //-----------------------------------------fin ligne de commande-----------------------------------------------

        //Envoyer une réponse à React FrontEnd
        return ResponseEntity.status(HttpStatus.CREATED).body(newPaiement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable(value = "id") Long paiementId)
            throws ResourceNotFoundException {
        Paiement paiement = paiementService.getPaiementById(paiementId);
        return ResponseEntity.ok().body(paiement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paiement> updatePaiement(@PathVariable(value = "id") Long paiementId,
                                                   @RequestBody Paiement paiementDetails) throws ResourceNotFoundException {
        Paiement updatedPaiement = paiementService.updatePaiement(paiementId, paiementDetails);
        return ResponseEntity.ok(updatedPaiement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaiement(@PathVariable(value = "id") Long paiementId)
            throws ResourceNotFoundException {
        paiementService.deletePaiement(paiementId);
        return ResponseEntity.noContent().build();
    }
}