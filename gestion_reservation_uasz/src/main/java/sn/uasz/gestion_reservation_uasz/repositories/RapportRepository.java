package sn.uasz.gestion_reservation_uasz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.uasz.gestion_reservation_uasz.models.Rapport;

/**
 * Interface décrivant les méthodes de persistance pour l'entité Rapport.
 */
public interface RapportRepository extends JpaRepository<Rapport, Long> {

    /**
     * Recherche le dernier rapport créé en fonction de la date de création, en ordre descendant.
     * 
     * @return Le dernier rapport créé.
     */
    Rapport findTopByOrderByDateCreationDesc();
}
