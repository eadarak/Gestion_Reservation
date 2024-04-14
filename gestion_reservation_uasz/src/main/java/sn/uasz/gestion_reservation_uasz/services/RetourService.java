package sn.uasz.gestion_reservation_uasz.services;

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

/**
 * Service pour la gestion des retours de ressources.
 */
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

    /**
     * Liste tous les retours de ressources.
     * 
     * @return La liste de tous les retours de ressources.
     */
    public List<Retour> listerRetours() {
        return repository.findAll();
    }

    /**
     * Recherche un retour de ressource par son identifiant.
     * 
     * @param id L'identifiant du retour de ressource à rechercher.
     * @return Le retour de ressource trouvé.
     */
    public Retour rechercherRetour(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Crée un nouveau retour de ressource.
     * 
     * @param r Le retour de ressource à créer.
     * @return Le retour de ressource créé, ou null si la ressource associée est
     *         introuvable ou n'est pas en prêt.
     */
    public Retour creerRetour(Retour r) {
        Ressource ressource = r.getRessource();
        Long rID = ressource.getIdRessource();
        Ressource existingRessource = ressourceRepository.findById(rID).orElse(null);

        if (existingRessource != null && existingRessource.getEtat().equalsIgnoreCase("En pret")) {
            List<Reservation> reservations = reservationRepository.findFutureReservationsForRessource(existingRessource,
                    r.getDateRetour());
            if (reservations.isEmpty()) {
                existingRessource.setEtat("Disponible");
            } else {
                existingRessource.setEtat("Reservee");
            }

            ressourceRepository.save(existingRessource);
            r.setRessource(existingRessource);
            return repository.save(r);
        } else {
            log.info("Ressource inexistante ou non en prêt");
            return null;
        }
    }
}
