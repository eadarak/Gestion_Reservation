package sn.uasz.gestion_reservation_uasz.security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import sn.uasz.gestion_reservation_uasz.models.Jwt;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.JwtRepository;
import sn.uasz.gestion_reservation_uasz.services.UtilisateurService;

/**
 * Service pour la gestion des jetons JWT (JSON Web Token).
 */
@Transactional
@Service
@AllArgsConstructor
public class JwtService {
    private static final String BEARER = "bearer";
    private final Key ENCRYPTION_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private UtilisateurService uService;
    private JwtRepository jwtRepository;

    /**
     * Génère un nouveau jeton JWT pour l'utilisateur donné.
     * 
     * @param username Le nom d'utilisateur.
     * @return Un map contenant le jeton JWT.
     */
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

    /**
     * Désactive tous les jetons pour un utilisateur donné.
     * 
     * @param utilisateur L'utilisateur.
     */
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

    /**
     * Génère le jeton JWT pour l'utilisateur donné.
     * 
     * @param utilisateur L'utilisateur.
     * @return Un map contenant le jeton JWT.
     */
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

    /**
     * Extrait le nom d'utilisateur du jeton JWT.
     * 
     * @param token Le jeton JWT.
     * @return Le nom d'utilisateur extrait du jeton.
     */
    public String extractUsername(String token) {
      return this.getClaim(token, Claims::getSubject);
    }

    /**
     * Vérifie si un jeton JWT est expiré.
     * 
     * @param token Le jeton JWT.
     * @return true si le jeton est expiré, sinon false.
     */
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(expirationDate);
    }

    /**
     * Récupère une revendication spécifique du jeton JWT.
     * 
     * @param token Le jeton JWT.
     * @param function La fonction pour extraire la revendication.
     * @return La revendication extraite.
     */
    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    /**
     * Récupère toutes les revendications du jeton JWT.
     * 
     * @param token Le jeton JWT.
     * @return Les revendications du jeton JWT.
     */
    @SuppressWarnings("deprecation")
    private Claims getAllClaims(String token) {
       return Jwts.parser()
                    .setSigningKey(ENCRYPTION_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    /**
     * Récupère un objet Jwt à partir de sa valeur.
     * 
     * @param valeur La valeur du jeton JWT.
     * @return L'objet Jwt correspondant à la valeur donnée.
     * @throws RuntimeException si aucun jeton n'est trouvé avec la valeur donnée.
     */
    public Jwt tokenByValue(String valeur) {
        return this.jwtRepository
            .findByValeurAndDesactiveAndExpire(valeur, false, false)
            .orElseThrow(() -> new RuntimeException("Token Inconnu"));
    }

    /**
     * Déconnecte l'utilisateur en invalidant son jeton JWT actif.
     */
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
