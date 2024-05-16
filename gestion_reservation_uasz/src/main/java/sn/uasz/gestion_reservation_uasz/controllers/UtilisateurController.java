package sn.uasz.gestion_reservation_uasz.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

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

    /**
     * Ajoute un nouvel utilisateur.
     * 
     * @param utilisateur L'utilisateur à ajouter.
     * @return ResponseEntity<Utilisateur> L'utilisateur ajouté avec un code de statut HTTP.
     */
    @PostMapping(path = "utilisateur")
    public ResponseEntity<Utilisateur> ajouter_utilisateur(@RequestBody Utilisateur utilisateur) {
        log.info("Ajout d'un utilisateur");
        Utilisateur user = uService.ajouter_utilisateur(utilisateur);
        return new ResponseEntity<Utilisateur>(user, HttpStatus.OK);
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     * 
     * @return ResponseEntity<List<Utilisateur>> Liste des utilisateurs avec un code de statut HTTP.
     */
    @GetMapping(path = "utilisateur")
    public ResponseEntity<List<Utilisateur>> lister_utilisateur() {
        List<Utilisateur> users = uService.lister_utilisateur();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Inscription d'un nouvel utilisateur.
     * 
     * @param utilisateur L'utilisateur à inscrire.
     * @return ResponseEntity<Utilisateur> L'utilisateur inscrit avec un code de statut HTTP.
     */
    @PostMapping(path = "inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur) {
        log.info("Tentative d'inscription pour l'utilisateur avec l'email : {}", utilisateur.getEmail());
        Utilisateur user = uService.inscrire(utilisateur);
        log.info("Inscription réussie pour l'utilisateur avec l'email : {}", utilisateur.getEmail());
        return new ResponseEntity<Utilisateur>(user, HttpStatus.OK);
    }

    /**
     * Connexion d'un utilisateur.
     * 
     * @param authenticationDTO Les informations d'authentification de l'utilisateur.
     * @return Map<String, String> Le jeton JWT généré en cas de succès.
     */
    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthenticationDTO authenticationDTO) {
        log.info("Tentative de connexion");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password()));

        if (authentication.isAuthenticated()) {
            log.info("Connexion réussie");
            return jwtService.generate(authenticationDTO.username());
        }

        return null;
    }

    /**
     * Déconnexion d'un utilisateur.
     */
    @PostMapping(path = "/deconnexion")
    public void deconnexion() {
        this.jwtService.deconnexion();
    }

    @GetMapping(path = "/total-user")
    public ResponseEntity<Long> allUsersCount() {
        Long users = uService.numberUsers();
        return new ResponseEntity<Long>(users, HttpStatus.OK);
    }
    
}
