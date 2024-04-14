package sn.uasz.gestion_reservation_uasz.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration pour le cryptage des mots de passe.
 */
@Configuration
public class ConfigurationPasswordCrypt {

    /**
     * Définit un bean pour l'encodeur de mots de passe BCrypt.
     * 
     * @return Un objet BCryptPasswordEncoder pour l'encodage des mots de passe.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
