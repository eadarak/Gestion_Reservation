package sn.uasz.gestion_reservation_uasz.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une catégorie d'éléments dans le système de gestion de réservation.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Categorie {

    /**
     * Identifiant unique de la catégorie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategorie;

    /**
     * Libellé de la catégorie.
     */
    private String libelleCategorie;

    /**
     * Type de la catégorie.
     */
    private String type;

    /**
     * Liste des ressources associées à cette catégorie.
     * 
     * <p>Cette liste est ignorée lors de la sérialisation/désérialisation JSON
     * pour éviter les boucles infinies.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "categorie")
    private List<Ressource> Ressource;
}
