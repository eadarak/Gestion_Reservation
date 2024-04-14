package sn.uasz.gestion_reservation_uasz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sn.uasz.gestion_reservation_uasz.models.Ressource;

public interface RessourceRepository extends JpaRepository<Ressource,Long>{
    @Query("SELECT r FROM Ressource r WHERE r.etat = 'Réservée'")
    List<Ressource> findRessourcesReservees();

    List<Ressource> findByEtatIn(String... etats);

}
