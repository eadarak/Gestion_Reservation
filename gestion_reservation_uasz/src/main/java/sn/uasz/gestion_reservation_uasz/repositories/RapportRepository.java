package sn.uasz.gestion_reservation_uasz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.uasz.gestion_reservation_uasz.models.Rapport;

public interface RapportRepository extends JpaRepository<Rapport, Long>{

    Rapport findTopByOrderByDateCreationDesc();

}