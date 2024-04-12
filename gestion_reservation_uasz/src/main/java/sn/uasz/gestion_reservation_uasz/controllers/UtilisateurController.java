package sn.uasz.gestion_reservation_uasz.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


import sn.uasz.gestion_reservation_uasz.dto.AuthenticationDTO;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.security.JwtService;
import sn.uasz.gestion_reservation_uasz.services.UtilisateurService;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;




@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class UtilisateurController {
    private UtilisateurService uService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping(path = "utilisateur")
    public ResponseEntity<Utilisateur> ajouter_utilisateur(@RequestBody Utilisateur utilisateur) {
        log.info("Ajout d'un utilisateur");
        Utilisateur user = uService.ajouter_utilisateur(utilisateur);
        return new ResponseEntity<Utilisateur>(user, HttpStatus.OK);
    }   

    @GetMapping(path = "utilisateur")
    public ResponseEntity<List<Utilisateur>> lister_utilisateur() {
        List<Utilisateur> users = uService.lister_utilisateur();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    


    @PostMapping(path = "inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur) {
        log.info("inscription");
        Utilisateur user = uService.inscrire(utilisateur);
        return new ResponseEntity<Utilisateur>(user, HttpStatus.OK);
    }   

    @PostMapping(path = "connexion")
    public Map<String, String>  connexion (@RequestBody AuthenticationDTO  authenticationDTO){
        log.info("connexion");
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generate(authenticationDTO.username());
        }
        
        return null;
    }

    @PostMapping(path = "/deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
    }

    // @PostMapping(path = "connexion")
    // public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthenticationDTO authenticationDTO) {
    //     log.info("connexion");

    //     try {
    //         final Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
    //         );

    //         log.info("Authentication result {}", authentication.isAuthenticated());
    //         // Authentification réussie, renvoyer une réponse appropriée
    //         Map<String, String> response = new HashMap<>();
    //         response.put("message", "Connexion réussie");
    //         return ResponseEntity.ok(response);
    //     } catch (AuthenticationException ex) {
    //         // Gérer les erreurs d'authentification
    //         log.error("Erreur d'authentification: {}", ex.getMessage());
    //         Map<String, String> errorResponse = new HashMap<>();
    //         errorResponse.put("error", "Erreur d'authentification");
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    //     }
    // }



}
