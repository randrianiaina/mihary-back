package mg.inclusiv.mihary.service;

import com.stripe.Stripe;
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
        Stripe.apiKey="sk_test_VePHdqKTYQjKNInc7u56JBrQ";
        Map<String,Object> params= new HashMap<>();
        params.put("amount", paiement.getMontantPaiement());
        params.put("currency", "mga");
        params.put("description","achat de produits agricoles");
        params.put("source","tok_visa");
        return nouveauPaiement;
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