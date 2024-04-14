package sn.uasz.gestion_reservation_uasz.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cette classe représente une ressource dans le système de gestion des réservations.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ressource {
    /**
     * Identifiant unique de la ressource.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRessource; 

    /**
     * Libellé de la ressource.
     */
    private String libelleRessource;

    /**
     * État de la ressource.
     */
    private String etat;

    /**
     * Description de la ressource.
     */
    private String description;
    
    /**
     * Liste des réservations associées à cette ressource.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "ressource")
    private List<Reservation> reservations;

    /**
     * Liste des prêts associés à cette ressource.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "ressource")
    private List<Pret> prets;

    /**
     * Liste des retours associés à cette ressource.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "ressource")
    private List<Retour> retours;

    /**
     * Catégorie à laquelle appartient cette ressource.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categorie")
    private Categorie categorie;

    /**
     * Utilisateur responsable de cette ressource.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="utilisateur")
    private Utilisateur utilisateur;
}
