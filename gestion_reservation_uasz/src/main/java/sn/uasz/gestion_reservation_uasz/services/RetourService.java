package sn.uasz.gestion_reservation_uasz.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import sn.uasz.gestion_reservation_uasz.models.Reservation;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.models.Retour;
import sn.uasz.gestion_reservation_uasz.repositories.ReservationRepository;
import sn.uasz.gestion_reservation_uasz.repositories.RessourceRepository;
import sn.uasz.gestion_reservation_uasz.repositories.RetourRepository;

@Slf4j
@Service
@Transactional
public class RetourService {
    @Autowired
    private RetourRepository repository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Retour> listerRetours(){
        return repository.findAll();
    }

    public Retour rechercherRetour(Long id){
        return repository.findById(id).get();
    }

    public Retour creerRetour(Retour r){
        Ressource ressource = r.getRessource();
        Long rID = ressource.getIdRessource();
        Ressource existingRessource = ressourceRepository.findById(rID).orElse(null);

        if (existingRessource != null && existingRessource.getEtat().equalsIgnoreCase("En pret")) {
            r.setDateRetour(new Date(System.currentTimeMillis()));

            List<Reservation> reservations = reservationRepository.findFutureReservationsForRessource(ressource, new Date());
            if (reservations.isEmpty()) {
                ressource.setEtat("Disponible");
            }else{
                ressource.setEtat("Reservee");
            }

            ressourceRepository.save(ressource);
            r.setRessource(existingRessource);
            return repository.save(r);


        }
        log.info("Ressource  inexistante ");
        return null;
        
    }

}
