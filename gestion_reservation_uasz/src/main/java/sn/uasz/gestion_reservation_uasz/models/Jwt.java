package sn.uasz.gestion_reservation_uasz.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente un jeton JWT (JSON Web Token) dans le système de gestion de réservation.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Jwt {

    /**
     * Identifiant unique du jeton JWT.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Valeur du jeton JWT.
     */
    private String valeur;

    /**
     * Indique si le jeton JWT est désactivé.
     */
    private boolean desactive;

    /**
     * Indique si le jeton JWT a expiré.
     */
    private boolean expire;

    /**
     * Utilisateur associé à ce jeton JWT.
     */
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE} )
    @JoinColumn(name = "utilisateur")
    private Utilisateur utilisateur;
}
