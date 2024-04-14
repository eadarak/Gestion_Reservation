package sn.uasz.gestion_reservation_uasz.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cette classe représente une réservation dans le système de gestion des réservations.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    /**
     * Identifiant unique de la réservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    /**
     * Date de la réservation.
     */
    @Temporal(TemporalType.DATE)
    private Date dateReservation;

    /**
     * Date prévue de la réservation.
     */
    @Temporal(TemporalType.DATE)
    private Date datePrevue;

    /**
     * Heure de début de la réservation.
     */
    private int heureDebut;

    /**
     * Heure de fin de la réservation.
     */
    private int heureFin;

    /**
     * La ressource réservée.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ressource")
    private Ressource ressource;

    /**
     * L'utilisateur effectuant la réservation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur")
    private Utilisateur utilisateur;
}
