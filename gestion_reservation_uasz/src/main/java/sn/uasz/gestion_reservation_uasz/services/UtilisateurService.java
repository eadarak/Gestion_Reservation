package sn.uasz.gestion_reservation_uasz.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sn.uasz.gestion_reservation_uasz.models.Role;
import sn.uasz.gestion_reservation_uasz.models.TypeDeRole;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.UtilisateurRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {
    private UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder passwordEncoder;
    // private ValidationService validationService;
    public void inscription(Utilisateur utilisateur) {

        if(!utilisateur.getEmail().contains("@")) {
            throw  new RuntimeException("Votre mail invalide");
        }
        if(!utilisateur.getEmail().contains(".")) {
            throw  new RuntimeException("Votre mail invalide");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if(utilisateurOptional.isPresent()) {
            throw  new RuntimeException("Votre mail est déjà utilisé");
        }
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        Role roleUtilisateur = new Role();
        roleUtilisateur.setLibelle(TypeDeRole.admin_primaire);
        utilisateur.setRole(roleUtilisateur);

        utilisateur = this.utilisateurRepository.save(utilisateur);
        
    }

    // public void activation(Map<String, String> activation) {
    //     Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
    //     if(Instant.now().isAfter(validation.getExpiration())){
    //         throw  new RuntimeException("Votre code a expiré");
    //     }
    //     Utilisateur utilisateurActiver = this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
    //     utilisateurActiver.setActif(true);
    //     this.utilisateurRepository.save(utilisateurActiver);
    // }

    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }
}
