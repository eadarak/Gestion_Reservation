package sn.uasz.gestion_reservation_uasz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.uasz.gestion_reservation_uasz.models.Pret;
import sn.uasz.gestion_reservation_uasz.services.PretService;

@RestController
@RequestMapping(path = "prets")
public class PretController {
    
    @Autowired
    private PretService pService;

    /**
     * Récupère la liste de tous les prêts.
     * 
     * @return ResponseEntity<List<Pret>> Liste des prêts avec un code de statut HTTP.
     */
    @GetMapping
    public ResponseEntity<List<Pret>> listerPrets() {
       List<Pret> prets = pService.listePrets();
       return new ResponseEntity<List<Pret>>(prets, HttpStatus.OK);
    }
    
    /**
     * Recherche un prêt par son identifiant.
     * 
     * @param id Identifiant du prêt à rechercher.
     * @return ResponseEntity<Pret> Le prêt recherché avec un code de statut HTTP.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pret> rechercherPret(@RequestParam Long  id) {
        return  new ResponseEntity<Pret>(pService.rechercherPret(id), HttpStatus.OK);
    }

    /**
     * Ajoute un nouveau prêt.
     * 
     * @param p Le prêt à ajouter.
     * @return ResponseEntity<?> Le prêt ajouté avec un code de statut HTTP.
     */
    @PostMapping
    public ResponseEntity<?> ajouterPret(@RequestBody Pret p) {
        Pret pret = pService.creerPret(p);
        if (pret == null) {
            return new ResponseEntity<String>("Pret impossible", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Pret>( pret, HttpStatus.CREATED);
    }
    
    /**
     * Supprime un prêt par son identifiant.
     * 
     * @param id Identifiant du prêt à supprimer.
     * @return ResponseEntity<String> Un message de confirmation avec un code de statut HTTP.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerPret(@PathVariable Long id){
        pService.supprimerPret(id);
        return ResponseEntity.status(HttpStatus.OK).body("Le pret " + id +" a ete supprime.");
    }    
}
