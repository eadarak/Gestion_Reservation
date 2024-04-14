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
 * Cette classe représente le retour d'une ressource dans le système de gestion des réservations.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Retour {
    /**
     * Identifiant unique du retour.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRetour;

    /**
     * Date du retour de la ressource.
     */
    @Temporal(TemporalType.DATE)
    private Date dateRetour;

    /**
     * Heure du retour de la ressource.
     */
    private int heureRetour;

    /**
     * Ressource retournée.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ressource")
    private Ressource ressource;
}
