package sn.uasz.gestion_reservation_uasz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sn.uasz.gestion_reservation_uasz.models.Categorie;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.CategorieRepository;
import sn.uasz.gestion_reservation_uasz.repositories.RessourceRepository;

@Service
@Transactional
public class RessourceService {
    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private  CategorieRepository categorieRepository;    

    public Ressource creerRessource(Ressource ressource) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
            ressource.setUtilisateur(utilisateur);
            ressource.setEtat("Disponible");
    
            Categorie categorie = ressource.getCategorie();
            if (categorie != null) {
                Categorie existingCategorie = categorieRepository.findByLibelleCategorie(categorie.getLibelleCategorie());
                if (existingCategorie != null) {
                    ressource.setCategorie(existingCategorie);
                } else {
                    ressource.setCategorie(categorieRepository.save(categorie));
                }
            } else {
                ressource.setCategorie(null);
            }
            
            return ressourceRepository.save(ressource);
        } else {
            // Gérer le cas où l'utilisateur n'est pas authentifié
            throw new IllegalStateException("L'utilisateur n'est pas authentifié.");
        }
    }
    

    public List<Ressource> listerRessources(){
        return ressourceRepository.findAll();
    }

    public Ressource rechercherRessource(Long id){
        return ressourceRepository.findById(id).get();
    }

    public Ressource updateRessource(Ressource r, Long id) {
        // Vérification de l'existence de la ressource
        Ressource existingRessource = rechercherRessource(id);
        if (existingRessource == null) {
            throw new RuntimeException("Ressource introuvable: " + id);
        }
    
        // Mise à jour des données de la ressource existante
        existingRessource.setEtat(r.getEtat());
        existingRessource.setLibelleRessource(r.getLibelleRessource());
        existingRessource.setDescription(r.getDescription());
    
        // Sauvegarde de la ressource mise à jour
        return ressourceRepository.save(existingRessource);
    }
    

    public void DeleteRessource(Long id){
        ressourceRepository.deleteById(id);
    }

    public List<Ressource> listerRessourcesDisponiblesOuReservees() {
        return ressourceRepository.findByEtatIn("Disponible", "Reservee");
    }

    public List<Ressource> listerRessourcesReservees() {
        return ressourceRepository.findByEtatIn( "Reservee");
    }

    public List<Ressource> listerRessourcesEnPret() {
        return ressourceRepository.findByEtatIn( "En Pret");
    }
}
