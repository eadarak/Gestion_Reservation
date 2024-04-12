package sn.uasz.gestion_reservation_uasz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.services.RessourceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(path = "ressource")
public class RessourceController {
    @Autowired
    private RessourceService rService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ressource>> lister_ressources() {
        List<Ressource> ressources = rService.listerRessources();
        return new ResponseEntity<>(ressources, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ressource> rechercher_ressource(@PathVariable("id") Long id) {
        return new ResponseEntity<>(rService.rechercherRessource(id), HttpStatus.OK);
    }

    @PostMapping()
    public  ResponseEntity<Ressource> ajouter_ressource(@RequestBody Ressource r) {
        return new ResponseEntity<>(rService.creerRessource(r), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public  ResponseEntity<Ressource> modifier_ressource(@PathVariable("id") Long id, @RequestBody Ressource r) {
      return  new ResponseEntity<>(rService.UpdateRessource(r, id), HttpStatus.OK);
    }
    
    @DeleteMapping("/id")
    public ResponseEntity<?> delete_ressource(@PathVariable("id") Long id){
        rService.DeleteRessource(id);
        return ResponseEntity.status(HttpStatus.OK).body("La ressource" + id +" a ete supprime.");
    }
    
    
    
}
