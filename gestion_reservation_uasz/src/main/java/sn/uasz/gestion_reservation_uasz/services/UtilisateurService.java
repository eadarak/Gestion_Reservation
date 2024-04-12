package sn.uasz.gestion_reservation_uasz.services;

import java.util.List;
import java.util.Optional;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import sn.uasz.gestion_reservation_uasz.models.Role;
import sn.uasz.gestion_reservation_uasz.models.TypeRole;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.RoleRepository;
import sn.uasz.gestion_reservation_uasz.repositories.UtilisateurRepository;

@Service
@Transactional
@AllArgsConstructor
public class UtilisateurService implements UserDetailsService {

    private UtilisateurRepository uRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public void inscription(Utilisateur utilisateur){
        uRepository.save(utilisateur);
    }

    public Utilisateur inscrire(Utilisateur utilisateur){
        if (utilisateur.getEmail().indexOf("@") == -1) {
            throw new RuntimeException("Votre email est invalide !!");
        }

        if (utilisateur.getEmail().indexOf(".") == -1) {
            throw new RuntimeException("Votre email est invalide !!");
        }
        
        
        Optional<Utilisateur> utilisateurOption = uRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurOption.isPresent()) {
            throw new RuntimeException("L'email est deja utilise");
        }
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        // Role roleUtilisateur = new Role();
        // roleUtilisateur.setLibelle(TypeRole.UTILISATEUR);

        // utilisateur.setRole(roleUtilisateur);

        Optional<Role> role = roleRepository.findByLibelle(TypeRole.UTILISATEUR);
        if(role.isPresent()){
            utilisateur.setRole(role.get());
        }

        return uRepository.save(utilisateur);
    }

    public Utilisateur ajouter_utilisateur(Utilisateur utilisateur){
        if (utilisateur.getEmail().indexOf("@") == -1) {
            throw new RuntimeException("Votre email est invalide !!");
        }

        if (utilisateur.getEmail().indexOf(".") == -1) {
            throw new RuntimeException("Votre email est invalide !!");
        }
        
        
        Optional<Utilisateur> utilisateurOption = uRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurOption.isPresent()) {
            throw new RuntimeException("L'email est deja utilise");
        }
        String mdpCrypte = this.passwordEncoder.encode("Passer123");
        utilisateur.setMdp(mdpCrypte);

        Optional<Role> role = roleRepository.findByLibelle(TypeRole.RESPONSABLE);
        if(role.isPresent()){
            utilisateur.setRole(role.get());
        }
        

        return uRepository.save(utilisateur);
    }


    public List<Utilisateur> lister_utilisateur(){
        return uRepository.findAll();
    }

    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.uRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException( username +" pas de correspondance"));
    }

}
