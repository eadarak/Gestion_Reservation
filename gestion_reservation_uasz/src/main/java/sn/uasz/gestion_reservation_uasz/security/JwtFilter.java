package sn.uasz.gestion_reservation_uasz.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sn.uasz.gestion_reservation_uasz.models.Jwt;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.services.UtilisateurService;

/**
 * Filtre pour la validation des jetons JWT (JSON Web Token).
 */
@Service
public class JwtFilter extends OncePerRequestFilter {

    private UtilisateurService uService;
    private JwtService jService;

    /**
     * Constructeur de JwtFilter.
     * 
     * @param uService Le service utilisateur.
     * @param jService Le service JWT.
     */
    public JwtFilter(UtilisateurService uService, JwtService jService) {
        this.uService = uService;
        this.jService = jService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String username = null;
        boolean isTokenExpired = true;
        Jwt jwtOnDB = null;

        String authorization = request.getHeader("Authorization");

        // Vérifie si l'en-tête Authorization contient un jeton JWT
        if (authorization != null && authorization.startsWith("Bearer")) {
            token = authorization.substring(7);
            jwtOnDB = this.jService.tokenByValue(token);
            isTokenExpired = jService.isTokenExpired(token);
            username = jService.extractUsername(token);
        }

        // Vérifie si le jeton n'est pas expiré, s'il correspond à l'utilisateur dans la base de données
        // et si l'authentification n'est pas déjà effectuée
        if (!isTokenExpired 
                && jwtOnDB != null
                && jwtOnDB.getUtilisateur().getEmail().equals(username)
                && SecurityContextHolder.getContext().getAuthentication() == null
            ) {
            // Charge les détails de l'utilisateur
            Utilisateur userDetails = uService.loadUserByUsername(username);
            // Crée une authentification basée sur le jeton JWT
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // Configure l'authentification dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Passe la requête au filtre suivant
        filterChain.doFilter(request, response);
    }
}
