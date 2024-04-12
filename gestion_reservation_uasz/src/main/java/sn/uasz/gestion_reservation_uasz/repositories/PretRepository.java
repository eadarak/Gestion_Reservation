package sn.uasz.gestion_reservation_uasz.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.uasz.gestion_reservation_uasz.models.Pret;
import sn.uasz.gestion_reservation_uasz.models.Ressource;

public interface PretRepository  extends JpaRepository<Pret, Long>{
    @Query("SELECT p FROM Pret p WHERE p.ressource = :ressource AND p.datePret >= :currentDate")
    List<Pret> findFutureReservationsForRessource(@Param("ressource") Ressource ressource, @Param("currentDate") Date currentDate);
}
