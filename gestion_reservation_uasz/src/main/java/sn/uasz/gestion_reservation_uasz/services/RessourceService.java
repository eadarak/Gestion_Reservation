package sn.uasz.gestion_reservation_uasz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.repositories.RessourceRepository;

@Service
@Transactional
public class RessourceService {
    @Autowired
    private RessourceRepository ressourceRepository;

    public Ressource creerRessource(Ressource ressource){
        ressource.setEtat("Disponible");
        return ressourceRepository.save(ressource);
    }

    public List<Ressource> listerRessources(){
        return ressourceRepository.findAll();
    }

    public Ressource rechercherRessource(Long id){
        return ressourceRepository.findById(id).get();
    }

    public Ressource UpdateRessource(Ressource r, Long id){
        r.setIdRessource(id);
        return ressourceRepository.save(r);
    }

    public void DeleteRessource(Long id){
        ressourceRepository.deleteById(id);
    }
}
