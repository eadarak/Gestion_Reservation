package sn.uasz.gestion_reservation_uasz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.uasz.gestion_reservation_uasz.models.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{

    Categorie findByLibelleCategorie(String categorieLibelle);
    
}
