package mg.inclusiv.mihary.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mg.inclusiv.mihary.entity.Produit;
import mg.inclusiv.mihary.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/produits")
@Transactional
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/list")
    public List<Produit> getAllProduits() {
        return produitService.getAllProduits();
    }

    @PostMapping("/ajout")
    public ResponseEntity<?> createProduit(@RequestParam("file") MultipartFile file,
                                           @RequestParam("nomProduit") String nomProduit,
                                           @RequestParam("prixProduit") BigDecimal prixProduit,
                                           @RequestParam("categorieProduit") String categorieProduit,
                                           @RequestParam("uniteProduit") String uniteProduit,
                                           @RequestParam("stockProduit") int stockProduit,
                                           @RequestParam("referenceProduit") int referenceProduit,
                                           @RequestParam("descriptionProduit") String descriptionProduit) {
        try {
            Produit produit = new Produit(nomProduit, prixProduit, categorieProduit, uniteProduit, stockProduit, descriptionProduit, file.getBytes(),referenceProduit);
            produitService.addProduit(produit);
            return new ResponseEntity<>("Le produit a été créé avec succès.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement de la photo du produit.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable(value = "id") Long produitId) throws ResourceNotFoundException {
        Produit produit = produitService.getProduitById(produitId);
        if (produit != null) {
            return ResponseEntity.ok().body(produit);
        } else {
            throw new ResourceNotFoundException("Produit non trouvé pour cet identifiant :: " + produitId);
        }
    }

    @GetMapping("/reference/{reference}")
    public List<Produit> getProduitsByReference(@PathVariable(value = "reference") Integer reference) throws ResourceNotFoundException {
        return produitService.findProduitsByReference(reference);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduit(@PathVariable(value = "id") Long produitId,
                                           @RequestParam(value = "file", required = false) MultipartFile file,
                                           @RequestParam(value = "nomProduit", required = false) String nomProduit,
                                           @RequestParam(value = "prixProduit", required = false) Double prixProduit,
                                           @RequestParam(value = "categorieProduit", required = false) String categorieProduit,
                                           @RequestParam(value = "uniteProduit", required = false) String uniteProduit,
                                           @RequestParam(value = "stockProduit", required = false) Integer stockProduit,
                                           @RequestParam(value = "descriptionProduit", required = false) String descriptionProduit) throws ResourceNotFoundException {
        Produit produit = produitService.getProduitById(produitId);
        if (produit != null) {
            if (file != null) {
                try {
                    produit.setPhotoProduit(file.getBytes());
                } catch (IOException e
                ) {
                    e.printStackTrace();
                }
            }
            if (nomProduit != null) {
                produit.setNomProduit(nomProduit);
            }
            if (prixProduit != null) {
                produit.setPrixProduit(BigDecimal.valueOf(prixProduit));
            }
            if (categorieProduit != null) {
                produit.setCategorieProduit(categorieProduit);
            }
            if (uniteProduit != null) {
                produit.setUniteProduit(uniteProduit);
            }
            if (stockProduit != null) {
                produit.setStockProduit(stockProduit);
            }
            if (descriptionProduit != null) {
                produit.setDescriptionProduit(descriptionProduit);
            }
            final Produit updatedProduit = produitService.addProduit(produit);
            return ResponseEntity.ok(updatedProduit);
        } else {
            throw new ResourceNotFoundException("Produit non trouvé pour cet identifiant :: " + produitId);
        }
    }
}