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
    
    /**
     * Récupère la liste de toutes les ressources.
     * 
     * @return ResponseEntity<List<Ressource>> Liste des ressources avec un code de statut HTTP.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ressource>> lister_ressources() {
        List<Ressource> ressources = rService.listerRessources();
        return new ResponseEntity<>(ressources, HttpStatus.OK);
    }

    /**
     * Récupère la liste des ressources reservables.
     * 
     * @return ResponseEntity<List<Ressource>> Liste des ressources reservables avec un code de statut HTTP.
     */
    @GetMapping("/reservables")
    public ResponseEntity<List<Ressource>> lister_ressources_reservable() {
        List<Ressource> ressources = rService.listerRessourcesDisponiblesOuReservees();
        return new ResponseEntity<>(ressources, HttpStatus.OK);
    }

    /**
     * Récupère la liste des ressources pretables.
     * 
     * @return ResponseEntity<List<Ressource>> Liste des ressources pretables avec un code de statut HTTP.
     */
    @GetMapping("/pretables")
    public ResponseEntity<List<Ressource>> lister_ressources_pretables() {
        List<Ressource> ressources = rService.listerRessourcesReservees();
        return new ResponseEntity<>(ressources, HttpStatus.OK);
    }

    /**
     * Récupère la liste des ressources en retour.
     * 
     * @return ResponseEntity<List<Ressource>> Liste des ressources en retour avec un code de statut HTTP.
     */
    @GetMapping("/retours")
    public ResponseEntity<List<Ressource>> lister_ressources_retours() {
        List<Ressource> ressources = rService.listerRessourcesEnPret();
        return new ResponseEntity<>(ressources, HttpStatus.OK);
    }

    /**
     * Recherche une ressource par son identifiant.
     * 
     * @param id Identifiant de la ressource à rechercher.
     * @return ResponseEntity<Ressource> La ressource recherchée avec un code de statut HTTP.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ressource> rechercher_ressource(@PathVariable("id") Long id) {
        return new ResponseEntity<>(rService.rechercherRessource(id), HttpStatus.OK);
    }

    /**
     * Ajoute une nouvelle ressource.
     * 
     * @param r La ressource à ajouter.
     * @return ResponseEntity<Ressource> La ressource ajoutée avec un code de statut HTTP.
     */
    @PostMapping()
    public  ResponseEntity<Ressource> ajouter_ressource(@RequestBody Ressource r) {
        return new ResponseEntity<>(rService.creerRessource(r), HttpStatus.CREATED);
    }
    
    /**
     * Modifie une ressource existante.
     * 
     * @param id Identifiant de la ressource à modifier.
     * @param r La ressource modifiée.
     * @return ResponseEntity<Ressource> La ressource modifiée avec un code de statut HTTP.
     */
    @PutMapping("/{id}")
    public  ResponseEntity<Ressource> modifier_ressource(@PathVariable("id") Long id, @RequestBody Ressource r) {
      return  new ResponseEntity<>(rService.updateRessource(r, id), HttpStatus.OK);
    }
    
    /**
     * Supprime une ressource par son identifiant.
     * 
     * @param id Identifiant de la ressource à supprimer.
     * @return ResponseEntity<?> Un message de confirmation avec un code de statut HTTP.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete_ressource(@PathVariable("id") Long id){
        rService.DeleteRessource(id);
        return ResponseEntity.status(HttpStatus.OK).body("La ressource" + id +" a ete supprime.");
    }    
}
