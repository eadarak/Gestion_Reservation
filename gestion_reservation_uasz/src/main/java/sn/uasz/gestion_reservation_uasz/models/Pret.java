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
 * Représente un prêt dans le système de gestion de réservation.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pret {

    /**
     * Identifiant unique du prêt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPret;

    /**
     * Date du prêt.
     */
    @Temporal(TemporalType.DATE)
    private Date datePret;

    /**
     * Heure de début du prêt.
     */
    private int heureDebutPret;

    /**
     * Durée du prêt en heures.
     */
    private int duree;

    /**
     * Ressource associée à ce prêt.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ressource")
    private Ressource ressource;
}
