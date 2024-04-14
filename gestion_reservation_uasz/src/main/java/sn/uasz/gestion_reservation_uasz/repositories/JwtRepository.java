package sn.uasz.gestion_reservation_uasz.repositories;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sn.uasz.gestion_reservation_uasz.models.Jwt;

/**
 * Interface décrivant les méthodes de persistance pour l'entité Jwt.
 */
public interface JwtRepository extends JpaRepository<Jwt, Long> {

    /**
     * Recherche un jeton JWT par valeur, état de désactivation et état d'expiration.
     * 
     * @param valeur    La valeur du jeton JWT à rechercher.
     * @param desactive L'état de désactivation du jeton JWT.
     * @param expire    L'état d'expiration du jeton JWT.
     * @return          Un objet Optional contenant le jeton JWT correspondant, s'il existe.
     */
    Optional<Jwt> findByValeurAndDesactiveAndExpire(String valeur, boolean desactive, boolean expire);

    /**
     * Recherche un jeton JWT valide pour un utilisateur spécifié.
     * 
     * @param email     L'adresse e-mail de l'utilisateur.
     * @param desactive L'état de désactivation du jeton JWT.
     * @param expire    L'état d'expiration du jeton JWT.
     * @return          Un objet Optional contenant le jeton JWT valide correspondant à l'utilisateur spécifié, s'il existe.
     */
    @Query("FROM Jwt j WHERE j.expire = :expire AND j.desactive = :desactive AND j.utilisateur.email = :email")
    Optional<Jwt> findByUtilisateurValidToken(String email, boolean desactive, boolean expire);

    /**
     * Recherche tous les jetons JWT associés à un utilisateur spécifié.
     * 
     * @param email L'adresse e-mail de l'utilisateur.
     * @return      Un flux de jetons JWT associés à l'utilisateur spécifié.
     */
    @Query("FROM Jwt j WHERE j.utilisateur.email = :email")
    Stream<Jwt> findUtilisateur(String email);
}
