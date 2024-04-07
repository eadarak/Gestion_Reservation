package sn.uasz.gestion_reservation_uasz.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.uasz.gestion_reservation_uasz.models.utilisateur;
import sn.uasz.gestion_reservation_uasz.services.UtilisateurService;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {
    
    private UtilisateurService utilisateurService ;

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody utilisateur utilisateur){
        this.utilisateurService.inscription(utilisateur);
    }
}
