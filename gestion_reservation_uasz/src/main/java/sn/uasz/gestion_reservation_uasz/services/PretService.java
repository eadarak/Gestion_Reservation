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

/**
 * Service pour la gestion des prêts.
 */
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

    /**
     * Crée un nouveau prêt.
     * 
     * @param p Prêt à créer
     * @return Le prêt créé
     */
    public Pret creerPret(Pret p) {
        Ressource r = p.getRessource();
        Long rID = r.getIdRessource();
        Ressource existingRessource = ressourceRepository.findById(rID).orElse(null);

        if (existingRessource != null) {
            // Vérifier si la ressource est réservée à la date prévue
            List<Reservation> reservations = reservationRepository
                    .findByRessourceDatePrevueAndHeureDebut(existingRessource, p.getDatePret(), p.getHeureDebutPret());
            if (!reservations.isEmpty()) {
                existingRessource.setEtat("En Pret");
                ressourceRepository.save(existingRessource);
                p.setRessource(existingRessource);
                p.setDuree(reservations.get(0).getHeureFin() - reservations.get(0).getHeureDebut());
                log.info("Le Prêt de la ressource " + existingRessource.getIdRessource() + " est effectif");
                return pRepository.save(p);
            }

            log.info("La ressource " + existingRessource.getIdRessource()
                    + " n'est pas réservée à la date prévue pour le prêt.");
            return null;

        } else {
            log.error("La ressource " + rID + " n'existe pas.");
            return null;
        }
    }

    /**
     * Recherche un prêt par son identifiant.
     * 
     * @param id Identifiant du prêt
     * @return Le prêt trouvé
     */
    public Pret rechercherPret(Long id) {
        return pRepository.findById(id).orElse(null);
    }

    /**
     * Récupère la liste de tous les prêts.
     * 
     * @return Liste des prêts
     */
    public List<Pret> listePrets() {
        return pRepository.findAll();
    }

    /**
     * Supprime un prêt par son identifiant.
     * 
     * @param id Identifiant du prêt à supprimer
     */
    public void supprimerPret(Long id) {
        pRepository.deleteById(id);
    }

    public long totalPret(){
        return  pRepository.count();
    }
}
