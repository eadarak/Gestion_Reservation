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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    @Temporal(TemporalType.DATE)
    private Date dateReservation;
    @Temporal(TemporalType.DATE)
    private Date datePrevue;
    private int heureDebut, heureFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ressource")
    private Ressource ressource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="utilisateur")
    private Utilisateur utilisateur;
}
