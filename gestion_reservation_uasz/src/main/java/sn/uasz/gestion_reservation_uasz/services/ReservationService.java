package sn.uasz.gestion_reservation_uasz.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import sn.uasz.gestion_reservation_uasz.models.Reservation;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.ReservationRepository;
import sn.uasz.gestion_reservation_uasz.repositories.RessourceRepository;
import sn.uasz.gestion_reservation_uasz.repositories.UtilisateurRepository;

@Slf4j
@Service
@Transactional
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RessourceRepository ressourceRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Reservation creationReservation(Reservation reservation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Ressource r = reservation.getRessource();
        Long rID = r.getIdRessource();
        Ressource existingRessource = ressourceRepository.findById(rID).orElse(null);
        
        if (authentication != null && authentication.isAuthenticated()) {
            Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
            reservation.setUtilisateur(utilisateur);
            if (existingRessource != null) {
                if (existingRessource.getEtat().equalsIgnoreCase("Disponible")) {
                    Date dateReservation = new Date(System.currentTimeMillis());
                    reservation.setDateReservation(dateReservation);
                    // Vérifier si la date prévue est postérieure ou égale à la date de réservation
                    if (reservation.getDatePrevue().compareTo(dateReservation) < 0) {
                        log.info("La date prévue doit être postérieure ou egale la date de réservation.");
                        return null;
                    }

                    existingRessource.setEtat("Reservee");
                    ressourceRepository.save(existingRessource);
                    reservation.setRessource(existingRessource);
                    return reservationRepository.save(reservation);
                    
                } else if (existingRessource.getEtat().equalsIgnoreCase("Reservee") || existingRessource.getEtat().equalsIgnoreCase("En Pret")) {
                    // La ressource est déjà réservée ou en prêt, vérifier les réservations existantes
                    List<Reservation> reservations = reservationRepository.findByRessourceAndDatePrevue(existingRessource, reservation.getDatePrevue());
                    for (Reservation res : reservations) {
                        if (res.getHeureDebut() == reservation.getHeureDebut()) {
                            // Les heures de début sont les mêmes, la réservation n'est pas possible
                            log.info("La ressource " + existingRessource.getIdRessource() + " a été réservée à la même date");
                            return null;
                        }
                    }
                    // Aucune réservation existante avec la même ressource, date prévue et heure de début
                    Date dateReservation = new Date(System.currentTimeMillis());
                    reservation.setDateReservation(dateReservation);
                    existingRessource.setEtat("Reservee");
                    ressourceRepository.save(existingRessource);
                    reservation.setRessource(existingRessource);
                    return reservationRepository.save(reservation);
                } else {
                    // La ressource est dans un autre état, la réservation n'est pas possible
                    log.info("La ressource " + existingRessource.getIdRessource() + " est Hors_Service");
                    return null;
                }
            } else {
                // La ressource n'existe pas
                log.info("La ressource " + reservation.getRessource().getIdRessource() + " n'existe pas");
                return null;
            }
        }
        else{
            throw new IllegalStateException("L'utilisateur n'est pas authentifié.");
        }
    }
            

    public List<Reservation> listReservations(){
        return reservationRepository.findAll();
    }

    public Reservation findReservation(Long id){
        return reservationRepository.findById(id).get();
    }


    public Reservation updateReservation(Reservation r, Long id) {
        Reservation existingReservation = reservationRepository.findById(id).orElse(null);
    
        if (existingReservation != null) {
            if (r.getDatePrevue().compareTo(existingReservation.getDateReservation()) < 0) {
                log.info("la modification de la réservation n'est pas autorisée ");
                return null;
            }
    
            // Si la date ou l'heure a été modifiée
            if (!existingReservation.getDatePrevue().equals(r.getDatePrevue()) || existingReservation.getHeureDebut() != r.getHeureDebut()) {
                // Vérifier si la nouvelle date et heure sont disponibles pour la ressource
                List<Reservation> reservations = reservationRepository.findByRessourceAndDatePrevue(r.getRessource(), r.getDatePrevue());
                for (Reservation res : reservations) {
                    if (res.getHeureDebut() == r.getHeureDebut()) {
                        // Si une réservation existante correspond à la nouvelle ressource, date et heure, la modification n'est pas possible
                        log.info("la modification de la réservation n'est pas autorisée ");
                        return null;
                    }
                }
            }
            
            r.setIdReservation(id);
            
            return reservationRepository.save(r);
        } else {
            log.info("la modification de la réservation n'est pas autorisée ");
            return null;
        }
    }
    
    
    public void DeleteReservation(Long id){
       reservationRepository.deleteById(id);
    }

    public List<Reservation> listReservationEntreDeuxDate(Date debut, Date fin){
        return reservationRepository.findByDateReservationBetween(debut, fin);
    }

    public void annuler_reservation(Long id){
        Reservation r = findReservation(id);
        if( r != null){
            Ressource ressource = r.getRessource();
            DeleteReservation(id);
            if (ressource != null) {
                List<Reservation> reservations = reservationRepository.findFutureReservationsForRessource(ressource, new Date());
                if (reservations.isEmpty()) {
                    ressource.setEtat("Disponible");
                }
                ressourceRepository.save(ressource);
            }
        }
    }
    
    public List<Reservation> listerReservationUtilisateur(Long id){
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Utilisateur n'existe pas !"));
        
        List<Reservation> reservations = reservationRepository.findByUtilisateur(utilisateur);
        if( reservations != null){
            return reservations;
        }else{
            return null;
        }
    }

    public List<Reservation> listerReservationsDuMois() {
        return reservationRepository.findReservationsThisMonth();
    }
}

