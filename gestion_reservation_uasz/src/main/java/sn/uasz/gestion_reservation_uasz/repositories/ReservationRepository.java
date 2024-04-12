package sn.uasz.gestion_reservation_uasz.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.uasz.gestion_reservation_uasz.models.Reservation;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDateReservationBetween(Date debut, Date fin);
    List<Reservation> findByRessourceAndDatePrevue(Ressource ressource, Date datePrevue);
    @Query("SELECT r FROM Reservation r WHERE r.ressource = :ressource AND r.datePrevue = :date AND r.heureDebut = :heureDebut ")
    List<Reservation> findByRessourceDatePrevueAndHeureDebut(@Param("ressource") Ressource ressource,@Param("date") Date datePrevue,@Param("heureDebut") int heureDebut);


    @Query("SELECT r FROM Reservation r WHERE r.ressource = :ressource AND r.dateReservation >= :currentDate")
    List<Reservation> findFutureReservationsForRessource(@Param("ressource") Ressource ressource, @Param("currentDate") Date currentDate);

    List<Reservation> findByUtilisateur(Utilisateur utilisateur);
}
