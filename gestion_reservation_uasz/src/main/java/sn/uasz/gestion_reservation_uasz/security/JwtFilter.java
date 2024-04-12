package sn.uasz.gestion_reservation_uasz.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import sn.uasz.gestion_reservation_uasz.models.Jwt;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.services.UtilisateurService;

@Service
public class JwtFilter extends OncePerRequestFilter {
    
    private UtilisateurService uService;
    private JwtService jService;



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

        if(authorization != null && authorization.startsWith("Bearer")){
            token = authorization.substring(7);
            jwtOnDB = this.jService.tokenByValue(token);
            isTokenExpired = jService.isTokenExpired(token);
            username = jService.extractUsername(token);
        }

        if (!isTokenExpired 
                && jwtOnDB.getUtilisateur().getEmail().equals(username)
                && SecurityContextHolder.getContext().getAuthentication() == null
            ) {
            Utilisateur userDetails = uService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request, response);
    }
    
}
