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

    
    // public Ressource creerRessource(Ressource ressource){
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication != null && authentication.isAuthenticated()) {
    //         Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
    //         ressource.setUtilisateur(utilisateur);
    //         ressource.setEtat("Disponible");
            

    //         Categorie categorie =ressource.getCategorie();
    //         Categorie existingCategorie = categorieRepository.findByLibelleCategorie(categorie.getLibelleCategorie());

    //         if (existingCategorie != null) {
    //             ressource.setCategorie(existingCategorie);
    //         } else {
    //             Categorie nouvelleCategorie = new Categorie();
    //             nouvelleCategorie.setLibelleCategorie(categorie.getLibelleCategorie());
    //             nouvelleCategorie.setType(categorie.getType());
    //             ressource.setCategorie(categorieRepository.save(nouvelleCategorie));
    //         }
    //         return ressourceRepository.save(ressource);
    //     } else {
    //         // Gérer le cas où l'utilisateur n'est pas authentifié
    //         throw new IllegalStateException("L'utilisateur n'est pas authentifié.");
    //     }
    // }
    

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

    public Ressource UpdateRessource(Ressource r, Long id){
        r.setIdRessource(id);
        return ressourceRepository.save(r);
    }

    public void DeleteRessource(Long id){
        ressourceRepository.deleteById(id);
    }
}
