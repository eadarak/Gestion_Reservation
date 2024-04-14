package sn.uasz.gestion_reservation_uasz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sn.uasz.gestion_reservation_uasz.models.Categorie;
import sn.uasz.gestion_reservation_uasz.repositories.CategorieRepository;

/**
 * Service pour la gestion des catégories.
 */
@Service
@Transactional
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Ajoute une nouvelle catégorie.
     * 
     * @param c Catégorie à ajouter
     * @return La catégorie ajoutée
     */
    public Categorie ajouterCategorie(Categorie c) {
        return categorieRepository.save(c);
    }

    /**
     * Récupère la liste de toutes les catégories.
     * 
     * @return Liste de catégories
     */
    public List<Categorie> listerCategories() {
        return categorieRepository.findAll();
    }

    /**
     * Recherche une catégorie par son identifiant.
     * 
     * @param id Identifiant de la catégorie
     * @return La catégorie trouvée
     */
    public Categorie rechercherCategorie(Long id) {
        return categorieRepository.findById(id).get();
    }

    /**
     * Modifie une catégorie existante.
     * 
     * @param c  Nouvelle catégorie
     * @param id Identifiant de la catégorie à modifier
     * @return La catégorie modifiée
     */
    public Categorie modifierCategorie(Categorie c, Long id) {
        c.setIdCategorie(id);
        return categorieRepository.save(c);
    }

    /**
     * Supprime une catégorie par son identifiant.
     * 
     * @param id Identifiant de la catégorie à supprimer
     */
    public void supprimerCategorie(Long id) {
        categorieRepository.deleteById(id);
    }
}
