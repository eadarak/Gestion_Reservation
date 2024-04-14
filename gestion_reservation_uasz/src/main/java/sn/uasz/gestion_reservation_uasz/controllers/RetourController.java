package sn.uasz.gestion_reservation_uasz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.uasz.gestion_reservation_uasz.models.Retour;
import sn.uasz.gestion_reservation_uasz.services.RetourService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "retours")
public class RetourController {
    
    @Autowired
    private RetourService retourService;

    /**
     * Récupère la liste de tous les retours.
     * 
     * @return ResponseEntity<List<Retour>> Liste des retours avec un code de statut HTTP.
     */
    @GetMapping
    public ResponseEntity<List<Retour>> listerRetours() {
        List<Retour> retours = retourService.listerRetours();
        return new ResponseEntity<List<Retour>>(retours, HttpStatus.OK);
    }
    
    /**
     * Recherche un retour par son identifiant.
     * 
     * @param id Identifiant du retour à rechercher.
     * @return ResponseEntity<Retour> Le retour recherché avec un code de statut HTTP.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Retour> rechercherRetour(@RequestParam Long  id) {
        return  new ResponseEntity<Retour>(retourService.rechercherRetour(id), HttpStatus.OK);
    }

    /**
     * Ajoute un nouveau retour.
     * 
     * @param r Le retour à ajouter.
     * @return ResponseEntity<?> Le retour ajouté avec un code de statut HTTP.
     */
    @PostMapping
    public ResponseEntity<?> ajouterRetour(@RequestBody Retour r) {
        Retour retour = retourService.creerRetour(r);
        if (retour == null) {
            return new ResponseEntity<String>("Retour impossible", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Retour>( retour, HttpStatus.CREATED);
    }
}
