package sn.uasz.gestion_reservation_uasz.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Utilisateur implements UserDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private String email;
    @Column(name = "mot_de_passe")
    private String mdp;
    private boolean actif = true;
    @OneToOne(cascade = CascadeType.MERGE)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur")
    private List<Ressource> ressources;

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur")
    private List<Reservation> reservations;

    // @JsonIgnore
    // @OneToMany(mappedBy = "utilisateur")
    // private List<Pret> prets;
    // @JsonIgnore
    // @OneToMany(mappedBy = "utilisateur")
    // private List<Retour> retours;

    



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.role.getLibelle()));
    }

    @Override
    public String getPassword() {
      return this.mdp;
    }

    @Override
    public String getUsername() {
       return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
       return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;

    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;

    }

    @Override
    public boolean isEnabled() {
        return this.actif;

    }
    
}
