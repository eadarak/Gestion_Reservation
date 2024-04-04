package sn.uasz.gestion_reservation_uasz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.uasz.gestion_reservation_uasz.models.Categorie;
import sn.uasz.gestion_reservation_uasz.services.CategorieService;

@RestController
@RequestMapping(path = "categories")
public class CategorieController {
    @Autowired
    private CategorieService cService;
    
    @GetMapping
    public ResponseEntity<List<Categorie>> listerCategories() {
        List<Categorie> categories = cService.listerCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> find_ressource(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cService.rechercherCategorie(id), HttpStatus.OK);
    }

    @PostMapping()
    public  ResponseEntity<Categorie> add_ressource(@RequestBody Categorie c) {
        return new ResponseEntity<>(cService.ajouterCategorie(c), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public  ResponseEntity<Categorie> put_ressource(@PathVariable("id") Long id, @RequestBody Categorie c) {
      return  new ResponseEntity<>(cService.modifierCategorie(c, id), HttpStatus.OK);
    }
    
    @DeleteMapping("/id")
    public ResponseEntity<String> delete_ressource(@PathVariable("id") Long id){
        cService.supprimerCategorie(id);
        return ResponseEntity.status(HttpStatus.OK).body("La categorie" + id +" a ete supprime.");
    }
}
