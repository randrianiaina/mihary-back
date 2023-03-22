package mg.inclusiv.mihary.controller;

import mg.inclusiv.mihary.entity.Produit;
import mg.inclusiv.mihary.repository.ProduitRepository;
import mg.inclusiv.mihary.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/list")
    public List<Produit> getAllProduits() {
        return produitService.getAllProduits();
    }

    @PostMapping("/ajouter")
    public Produit createProduit(@RequestBody Produit produit) {
        return produitService.addProduit(produit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable(value = "id") Long produitId) throws ResourceNotFoundException {
        Produit optionalProduit = produitService.getProduitById(produitId);
        if (optionalProduit != null) {

            return ResponseEntity.ok().body(optionalProduit);
        } else {
            throw new ResourceNotFoundException("Produit non trouvé pour cet identifiant :: " + produitId);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable(value = "id") Long produitId,
                                                 @RequestBody Produit produitDetails) throws ResourceNotFoundException {
        Produit optionalProduit = produitService.getProduitById(produitId);
        if(optionalProduit != null) {
            Produit produit = optionalProduit;
            produit.setNomProduit(produitDetails.getNomProduit());
            produit.setPrixProduit(produitDetails.getPrixProduit());
            produit.setCategorieProduit(produitDetails.getCategorieProduit());
            produit.setUniteProduit(produitDetails.getUniteProduit());
            produit.setStockProduit(produitDetails.getStockProduit());
            produit.setDescriptionProduit(produitDetails.getDescriptionProduit());
            produit.setPhotoProduit(produitDetails.getPhotoProduit());
            final Produit updatedProduit = produitService.addProduit(produit);
            return ResponseEntity.ok(updatedProduit);
        } else {
            throw new ResourceNotFoundException("Produit non trouvé pour cet identifiant :: " + produitId);
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteProduit(@PathVariable(value = "id") Long produitId)
            throws ResourceNotFoundException {
        Produit produit = produitService.getProduitById(produitId);
        if (produit !=null) {

            produitService.deleteProduit(produit.getIdProduit());
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
        } else {
            throw new ResourceNotFoundException("Produit non trouvé pour cet identifiant :: " + produitId);
        }
    }

}

