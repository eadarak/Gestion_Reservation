package sn.uasz.gestion_reservation_uasz.security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import sn.uasz.gestion_reservation_uasz.models.Jwt;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.JwtRepository;
import sn.uasz.gestion_reservation_uasz.services.UtilisateurService;

@Transactional
@Service
@AllArgsConstructor
public class JwtService {
    private static final String BEARER = "bearer";
    private final Key ENCRYPTION_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private UtilisateurService uService;
    private JwtRepository jwtRepository;

    public Map<String, String> generate(String username){
        Utilisateur utilisateur = this.uService.loadUserByUsername(username);
        disableTokens(utilisateur);
        Map<String, String> jwtMap = this.generateJwt(utilisateur);
        Jwt jwt = Jwt
            .builder()
            .valeur(jwtMap.get(BEARER))
            .desactive(false)
            .expire(false)
            .utilisateur(utilisateur)
            .build();
        this.jwtRepository.save(jwt);
        return jwtMap;
    }

    private void disableTokens(Utilisateur utilisateur) {
        List<Jwt> jwtLists = jwtRepository.findUtilisateur(utilisateur.getEmail()).map(
            jwt -> {
                jwt.setDesactive(true);
                jwt.setExpire(true);
                return jwt;
            }
        ).collect(Collectors.toList());

        jwtRepository.saveAll(jwtLists);
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        long currentTime = System.currentTimeMillis();
        long expirationTime =  currentTime + 30 * 60 * 1000;

        Map<String, Object> claims = Map.of(
            "nom", utilisateur.getNom(),
            Claims.SUBJECT, utilisateur.getEmail(),
            Claims.EXPIRATION, new Date(expirationTime),
            "role", utilisateur.getRole().getLibelle()
        );

        

        String bearer = Jwts.builder()
                .issuedAt(new Date(currentTime))
                .expiration(new Date(expirationTime))
                .subject(utilisateur.getEmail())
                .claims(claims)
                .signWith(ENCRYPTION_KEY)
                .compact();

      return Map.of(BEARER, bearer);
    }

    public String extractUsername(String token) {
      return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(expirationDate);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    @SuppressWarnings("deprecation")
    private Claims getAllClaims(String token) {
       return Jwts.parser()
                    .setSigningKey(ENCRYPTION_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public Jwt tokenByValue(String valeur) {
        return this.jwtRepository
            .findByValeurAndDesactiveAndExpire(valeur,false,false)
            .orElseThrow(() -> new RuntimeException("Token Inconnu"));
    }

    public void deconnexion(){
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository
                .findByUtilisateurValidToken(utilisateur.getEmail(), false, false)
                .orElseThrow(() -> new RuntimeException("Token invalide"));
        
        jwt.setDesactive(true);
        jwt.setExpire(true);
        this.jwtRepository.save(jwt);
        

    }


}
