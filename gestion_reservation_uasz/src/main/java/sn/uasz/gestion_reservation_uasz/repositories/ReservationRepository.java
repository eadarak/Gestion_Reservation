package sn.uasz.gestion_reservation_uasz.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.uasz.gestion_reservation_uasz.models.Reservation;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;

/**
 * Interface décrivant les méthodes de persistance pour l'entité Reservation.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Recherche les réservations effectuées entre deux dates spécifiées.
     * 
     * @param debut La date de début.
     * @param fin   La date de fin.
     * @return Une liste de réservations effectuées entre les deux dates spécifiées.
     */
    List<Reservation> findByDateReservationBetween(Date debut, Date fin);

    /**
     * Recherche les réservations pour une ressource donnée à une date prévue spécifique.
     * 
     * @param ressource   La ressource concernée.
     * @param datePrevue  La date prévue.
     * @return Une liste de réservations pour la ressource donnée à la date prévue spécifiée.
     */
    List<Reservation> findByRessourceAndDatePrevue(Ressource ressource, Date datePrevue);

    /**
     * Recherche les réservations pour une ressource donnée à une date et une heure de début spécifiques.
     * 
     * @param ressource    La ressource concernée.
     * @param datePrevue   La date prévue.
     * @param heureDebut   L'heure de début.
     * @return Une liste de réservations pour la ressource donnée à la date et l'heure de début spécifiées.
     */
    @Query("SELECT r FROM Reservation r WHERE r.ressource = :ressource AND r.datePrevue = :date AND r.heureDebut = :heureDebut ")
    List<Reservation> findByRessourceDatePrevueAndHeureDebut(@Param("ressource") Ressource ressource,
            @Param("date") Date datePrevue, @Param("heureDebut") int heureDebut);

    /**
     * Recherche les réservations futures pour une ressource donnée à partir de la date actuelle.
     * 
     * @param ressource      La ressource concernée.
     * @param currentDate    La date actuelle.
     * @return Une liste de réservations futures pour la ressource donnée à partir de la date actuelle.
     */
    @Query("SELECT r FROM Reservation r WHERE r.ressource = :ressource AND r.dateReservation >= :currentDate")
    List<Reservation> findFutureReservationsForRessource(@Param("ressource") Ressource ressource,
            @Param("currentDate") Date currentDate);

    /**
     * Recherche les réservations effectuées par un utilisateur donné.
     * 
     * @param utilisateur   L'utilisateur concerné.
     * @return Une liste de réservations effectuées par l'utilisateur donné.
     */
    List<Reservation> findByUtilisateur(Utilisateur utilisateur);

    /**
     * Recherche les réservations effectuées ce mois-ci.
     * 
     * @return Une liste de réservations effectuées ce mois-ci.
     */
    @Query("SELECT r FROM Reservation r WHERE MONTH(r.dateReservation) = MONTH(CURRENT_DATE) AND YEAR(r.dateReservation) = YEAR(CURRENT_DATE)")
    List<Reservation> findReservationsThisMonth();
}
