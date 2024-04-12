package sn.uasz.gestion_reservation_uasz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.uasz.gestion_reservation_uasz.models.Retour;

public interface RetourRepository extends JpaRepository<Retour, Long> {
    
}
