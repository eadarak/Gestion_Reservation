package sn.uasz.gestion_reservation_uasz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.uasz.gestion_reservation_uasz.models.Pret;
import sn.uasz.gestion_reservation_uasz.services.PretService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(path = "prets")
public class PretController {
    @Autowired
    private PretService pService;

    @GetMapping
    public ResponseEntity<List<Pret>> listerPrets() {
       List<Pret> prets = pService.listePrets();
       return new ResponseEntity<List<Pret>>(prets, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pret> rechercherPret(@RequestParam Long  id) {
        return  new ResponseEntity<Pret>(pService.rechercherPret(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> ajouterPret(@RequestBody Pret p) {
        Pret pret = pService.creerPret(p);
        if (pret == null) {
            return new ResponseEntity<String>("Pret impossible", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Pret>( pret, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerPret(@PathVariable Long id){
        pService.supprimerPret(id);
        return ResponseEntity.status(HttpStatus.OK).body("Le pret " + id +" a ete supprime.");
    }
    

}
