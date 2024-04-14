package sn.uasz.gestion_reservation_uasz.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.uasz.gestion_reservation_uasz.models.Utilisateur;

/**
 * Interface décrivant les méthodes de persistance pour l'entité Utilisateur.
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    /**
     * Recherche un utilisateur par son adresse e-mail.
     * 
     * @param email L'adresse e-mail de l'utilisateur à rechercher.
     * @return L'utilisateur correspondant à l'adresse e-mail, s'il existe.
     */
    Optional<Utilisateur> findByEmail(String email);
}
