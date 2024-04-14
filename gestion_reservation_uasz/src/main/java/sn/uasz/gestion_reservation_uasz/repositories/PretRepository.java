package sn.uasz.gestion_reservation_uasz.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.uasz.gestion_reservation_uasz.models.Pret;
import sn.uasz.gestion_reservation_uasz.models.Ressource;

/**
 * Interface décrivant les méthodes de persistance pour l'entité Pret.
 */
public interface PretRepository extends JpaRepository<Pret, Long> {

    /**
     * Recherche les réservations futures pour une ressource spécifiée.
     * 
     * @param ressource     La ressource pour laquelle rechercher les réservations futures.
     * @param currentDate   La date actuelle à partir de laquelle rechercher les réservations futures.
     * @return              La liste des réservations futures pour la ressource spécifiée.
     */
    @Query("SELECT p FROM Pret p WHERE p.ressource = :ressource AND p.datePret >= :currentDate")
    List<Pret> findFutureReservationsForRessource(@Param("ressource") Ressource ressource, @Param("currentDate") Date currentDate);
}
