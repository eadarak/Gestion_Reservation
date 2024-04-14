package sn.uasz.gestion_reservation_uasz.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente un rapport dans le système de gestion de réservation.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rapport {

    /**
     * Identifiant unique du rapport.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titre du rapport.
     */
    private String titre;

    /**
     * Date de création du rapport.
     */
    @Column(name = "date_creation")
    private Date dateCreation;

    /**
     * Contenu PDF du rapport.
     */
    @Lob
    private byte[] contenuPDF;

    /**
     * Utilisateur associé à ce rapport.
     */
    @ManyToOne
    private Utilisateur utilisateur;
}
