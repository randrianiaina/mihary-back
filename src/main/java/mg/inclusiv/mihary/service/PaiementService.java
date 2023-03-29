package mg.inclusiv.mihary.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import mg.inclusiv.mihary.entity.Paiement;
import mg.inclusiv.mihary.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public Paiement createPaiement(Paiement paiement) {
        Paiement nouveauPaiement = paiementRepository.save(paiement);
        Stripe.apiKey="sk_test_51MqBM9AD78j1yqjL9qIU7ENwKYw5A9wuVgFKwlGAmjZaUNoNf0AjlBECN5IR4mXlZAgWSUWOafTOl8MIXBv7UN3V00L6fN9uWK";
        Map<String,Object> params= new HashMap<>();
        params.put("amount", paiement.getMontantPaiement());
        params.put("currency", "mga");
        params.put("description","achat de produits agricoles");
        params.put("source","tok_visa");

        try {
            Charge charge = Charge.create(params);
            paiement.setChargeId(charge.getId()); // sauvegarder l'ID de la charge dans l'objet Paiement
            paiementRepository.save(paiement); // sauvegarder le paiement dans la base de données
            return paiement;
        } catch (StripeException e) {
        // gestion d'erreur ici
            return null;
        }
    }


    public Paiement getPaiementById(Long paiementId) throws ResourceNotFoundException {
        return paiementRepository.findById(paiementId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé pour cet identifiant :: " + paiementId));
    }

    public Paiement updatePaiement(Long paiementId, Paiement paiementDetails) throws ResourceNotFoundException {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé pour cet identifiant :: " + paiementId));

        paiement.setMontantPaiement(paiementDetails.getMontantPaiement());
        paiement.setModePaiement(paiementDetails.getModePaiement());
        paiement.setStatutPaiement(paiementDetails.getStatutPaiement());
        return paiementRepository.save(paiement);
    }

    public void deletePaiement(Long paiementId) throws ResourceNotFoundException {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé pour cet identifiant :: " + paiementId));
        paiementRepository.delete(paiement);
    }
}