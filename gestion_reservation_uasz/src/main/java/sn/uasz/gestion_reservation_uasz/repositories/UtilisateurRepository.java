package sn.uasz.gestion_reservation_uasz.repositories;

import org.springframework.data.repository.CrudRepository;

import sn.uasz.gestion_reservation_uasz.models.Utilisateur;


import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String email);
}
