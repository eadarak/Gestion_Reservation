package sn.uasz.gestion_reservation_uasz.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.RuntimeException;
import java.util.Optional;

import lombok.AllArgsConstructor;
import sn.uasz.gestion_reservation_uasz.models.utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.UtilisateurRepository;

@Service
@AllArgsConstructor
public class UtilisateurService {
    
    private UtilisateurRepository utilisateurRepository ;

    private BCryptPasswordEncoder passwordEncoder ;

    public void inscription(utilisateur utilisateur){
         
        // verrification de la validite de l'email
        if (!utilisateur.getEmail().contains("@")) {
            throw new RuntimeException(" Votre email est invalide !") ;
        }
        if (!utilisateur.getEmail().contains(".")) {
            throw new RuntimeException(" Votre email est invalide !") ;
        }

        // verrifier si l'email est deja utiliser ou pas 

        Optional<utilisateur> utilisateurOptional =this.utilisateurRepository.findByEmail(utilisateur.getEmail()) ;
        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException(" Votre email est deja utiliser!") ;
        }

        // Cryptage du mot de passe avant son enregistrement dans la base de donne 
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp()) ;
        utilisateur.setMdp(mdpCrypte);


        this.utilisateurRepository.save(utilisateur) ;

    }
}
