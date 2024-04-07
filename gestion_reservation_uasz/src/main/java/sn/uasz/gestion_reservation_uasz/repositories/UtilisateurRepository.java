package sn.uasz.gestion_reservation_uasz.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import sn.uasz.gestion_reservation_uasz.models.utilisateur;


public interface UtilisateurRepository extends CrudRepository<utilisateur , Integer>{

    Optional<utilisateur> findByEmail(String email);
  
}
