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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ressource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRessource; 
    private String libelleRessource;
    private String etat;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "ressource")
    private List<Categorie> categorie;

    // @JsonIgnore
    // @OneToMany(mappedBy = "ressource")
    // private List<Reservation> reservations;
}
