package mg.inclusiv.mihary.service;

import mg.inclusiv.mihary.entity.Approvisionnement;
import mg.inclusiv.mihary.entity.Produit;
import mg.inclusiv.mihary.entity.Utilisateur;
import mg.inclusiv.mihary.repository.ApprovisionnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApprovisionnementService {

    @Autowired

    private ApprovisionnementRepository approvisionnementRepository;

    public List<Approvisionnement> getAllApprovisionnements() {
        return approvisionnementRepository.findAll();
    }

    public Approvisionnement getApprovisionnementById(Long id) throws ResourceNotFoundException {
        return approvisionnementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Approvisionnement introuvable avec l'id : " + id));
    }

    public Approvisionnement saveApprovisionnement(Approvisionnement approvisionnement) {
        return approvisionnementRepository.save(approvisionnement);
    }

    public List<Object[]> getAllApprovisionnementsWithProductName() {
        return approvisionnementRepository.getAllApprovisionnementsWithProductName();
    }

    public List<Approvisionnement> getAllApprovisionnementsWithProductIdAndUserId(Integer userId, Long productId) {
        return approvisionnementRepository.getAllApprovisionnementsWithProductIdAndUserId(userId,productId);
    }

    public List<Approvisionnement> getApprovisionnementsByUtilisateur(Utilisateur utilisateur) {
        return approvisionnementRepository.findByUtilisateur(utilisateur);
    }

    public List<Object[]> getAllApprovisionnementsWithUtilisateurId(Integer userId) {
        return approvisionnementRepository.getAllApprovisionnementsWithUtilisateurId(userId);
    }
    public List<Object[]> getAllApprovisionnementsWithCooperativeId(Integer userId) {
        return approvisionnementRepository.getAllApprovisionnementsWithCooperativeId(userId);
    }
    public List<Produit> getProduitsByApprovisionnement(Approvisionnement approvisionnement) {
        return (List<Produit>) approvisionnement.getProduit();
    }

    public List<Object[]> getAllApprovisionnementsWithProductNameByUserIdAndDate(Integer userId, LocalDate dateApprovisionnement) {
        return approvisionnementRepository.getAllApprovisionnementsWithProductNameByUserIdAndDate(userId, dateApprovisionnement);
    }

    public Approvisionnement updateApprovisionnement(Long id, Approvisionnement approvisionnementDetails) throws ResourceNotFoundException {
        Approvisionnement approvisionnement = approvisionnementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Approvisionnement introuvable avec l'id : " + id));

        approvisionnement.setProduit(approvisionnementDetails.getProduit());
        approvisionnement.setQuantiteApprovisionnement(approvisionnementDetails.getQuantiteApprovisionnement());
        approvisionnement.setPrixUnitaire(approvisionnementDetails.getPrixUnitaire());
        approvisionnement.setDateApprovisionnement(approvisionnementDetails.getDateApprovisionnement());
        approvisionnement.setUtilisateur(approvisionnementDetails.getUtilisateur());
        approvisionnement.setStatutPaiement(approvisionnementDetails.getStatutPaiement());
        return approvisionnementRepository.save(approvisionnement);
    }

    public void deleteApprovisionnement(Long id) throws ResourceNotFoundException {
        Approvisionnement approvisionnement = approvisionnementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Approvisionnement introuvable avec l'id : " + id));
        approvisionnementRepository.delete(approvisionnement);
    }
    public List<Object[]> getAllApprovisionnementsWithProductNameByUserIdAndDateInter(Integer userId, LocalDate startDate, LocalDate endDate) {
        return approvisionnementRepository.getAllApprovisionnementsWithProductNameByUserIdAndDateInter(userId, startDate, endDate);
    }
}
