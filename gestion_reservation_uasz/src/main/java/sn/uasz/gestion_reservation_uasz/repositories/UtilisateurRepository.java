package sn.uasz.gestion_reservation_uasz.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
// import java.util.List;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
    Optional<Utilisateur> findByEmail(String Email);
}
