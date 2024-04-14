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

/**
 * Service pour la gestion des utilisateurs.
 */
@Service
@Transactional
@AllArgsConstructor
public class UtilisateurService implements UserDetailsService {

    private UtilisateurRepository uRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    /**
     * Inscription d'un nouvel utilisateur.
     * 
     * @param utilisateur L'utilisateur à inscrire.
     */
    public void inscription(Utilisateur utilisateur) {
        uRepository.save(utilisateur);
    }

    /**
     * Inscription d'un nouvel utilisateur avec vérification de l'email et cryptage
     * du mot de passe.
     * 
     * @param utilisateur L'utilisateur à inscrire.
     * @return L'utilisateur inscrit.
     * @throws RuntimeException si l'email est invalide ou déjà utilisé, ou si le
     *                          rôle utilisateur n'a pas été trouvé.
     */
    public Utilisateur inscrire(Utilisateur utilisateur) {
        // Vérification de l'email
        if (utilisateur.getEmail().indexOf("@") == -1 || utilisateur.getEmail().indexOf(".") == -1) {
            throw new RuntimeException("Votre email est invalide !!");
        }

        // Vérification si l'email est déjà utilisé
        Optional<Utilisateur> utilisateurOption = uRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurOption.isPresent()) {
            throw new RuntimeException("L'email est déjà utilisé");
        }

        // Cryptage du mot de passe
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        // Récupération du rôle utilisateur
        Optional<Role> roleOption = roleRepository.findByLibelle(TypeRole.UTILISATEUR);
        if (roleOption.isEmpty()) {
            throw new RuntimeException("Le rôle utilisateur n'a pas été trouvé");
        }

        // Attribution du rôle à l'utilisateur
        utilisateur.setRole(roleOption.get());

        return uRepository.save(utilisateur);
    }

    /**
     * Ajout d'un nouvel utilisateur avec vérification de l'email et attribution
     * automatique du rôle responsable.
     * 
     * @param utilisateur L'utilisateur à ajouter.
     * @return L'utilisateur ajouté.
     * @throws RuntimeException si l'email est invalide ou déjà utilisé.
     */
    public Utilisateur ajouter_utilisateur(Utilisateur utilisateur) {
        // Vérification de l'email
        if (utilisateur.getEmail().indexOf("@") == -1 || utilisateur.getEmail().indexOf(".") == -1) {
            throw new RuntimeException("Votre email est invalide !!");
        }

        // Vérification si l'email est déjà utilisé
        Optional<Utilisateur> utilisateurOption = uRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurOption.isPresent()) {
            throw new RuntimeException("L'email est déjà utilisé");
        }

        // Cryptage du mot de passe (mot de passe par défaut "Passer123")
        String mdpCrypte = this.passwordEncoder.encode("Passer123");
        utilisateur.setMdp(mdpCrypte);

        // Attribution automatique du rôle responsable
        Optional<Role> role = roleRepository.findByLibelle(TypeRole.RESPONSABLE);
        if (role.isPresent()) {
            utilisateur.setRole(role.get());
        }

        return uRepository.save(utilisateur);
    }

    /**
     * Liste tous les utilisateurs.
     * 
     * @return La liste de tous les utilisateurs.
     */
    public List<Utilisateur> lister_utilisateur() {
        return uRepository.findAll();
    }

    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.uRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " pas de correspondance"));
    }
}
