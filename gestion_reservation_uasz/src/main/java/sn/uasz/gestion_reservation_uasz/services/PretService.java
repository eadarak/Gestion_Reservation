package sn.uasz.gestion_reservation_uasz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import sn.uasz.gestion_reservation_uasz.models.Pret;
import sn.uasz.gestion_reservation_uasz.models.Reservation;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.repositories.PretRepository;
import sn.uasz.gestion_reservation_uasz.repositories.ReservationRepository;
import sn.uasz.gestion_reservation_uasz.repositories.RessourceRepository;

@Slf4j
@Service
@Transactional
public class PretService {
    @Autowired
    private PretRepository pRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public Pret creerPret(Pret p) {
        Ressource r = p.getRessource();
        Long rID = r.getIdRessource();
        Ressource existingRessource = ressourceRepository.findById(rID).orElse(null);
    
        if (existingRessource != null) {
            // Vérifier si la ressource est réservée à la date prévue
            List<Reservation> reservations = reservationRepository.findByRessourceDatePrevueAndHeureDebut(existingRessource, p.getDatePret(), p.getHeureDebutPret());
            if (!reservations.isEmpty()) {
                
                existingRessource.setEtat("En Pret");
                ressourceRepository.save(existingRessource);
                p.setRessource(existingRessource);
                p.setDuree(reservations.getFirst().getHeureFin() - reservations.getFirst().getHeureDebut());
                log.info("Le Pret de la ressource " + existingRessource.getIdRessource() + " est effectif");
                return pRepository.save(p);
            }
            
            log.info("La ressource " + existingRessource.getIdRessource() + " n'est réservée à la date prévue pour le prêt.");
            return null;
          
        } else {
            log.error("La ressource " + rID + " n'existe pas.");
            return null;
        }
    }
    


    public Pret rechercherPret(Long id){
        return pRepository.findById(id).get();
    }

    public List<Pret> listePrets(){
        return pRepository.findAll();
    }

    public void supprimerPret(Long id){
        pRepository.deleteById(id);
    }
    
    
}
