package sn.uasz.gestion_reservation_uasz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sn.uasz.gestion_reservation_uasz.models.Ressource;

/**
 * Interface décrivant les méthodes de persistance pour l'entité Ressource.
 */
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

    /**
     * Recherche les ressources qui sont dans un état "Réservée".
     * 
     * @return Une liste de ressources qui sont réservées.
     */
    @Query("SELECT r FROM Ressource r WHERE r.etat = 'Réservée'")
    List<Ressource> findRessourcesReservees();

    /**
     * Recherche les ressources qui sont dans l'un des états spécifiés.
     * 
     * @param etats Les états à rechercher.
     * @return Une liste de ressources dans les états spécifiés.
     */
    List<Ressource> findByEtatIn(String... etats);
}
