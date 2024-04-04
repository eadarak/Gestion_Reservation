package sn.uasz.gestion_reservation_uasz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sn.uasz.gestion_reservation_uasz.models.Categorie;
import sn.uasz.gestion_reservation_uasz.repositories.CategorieRepository;

@Service
@Transactional
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie ajouterCategorie(Categorie c){
       return categorieRepository.save(c);
    }

    public List<Categorie> listerCategories(){
        return categorieRepository.findAll();
    }

    public Categorie rechercherCategorie(Long id){
        return categorieRepository.findById(id).get();
    }

    public Categorie modifierCategorie(Categorie c, Long id){
        c.setIdCategorie(id);
        return categorieRepository.save(c);
    }

    public void supprimerCategorie(Long id){
        categorieRepository.deleteById(id);
    }
}
