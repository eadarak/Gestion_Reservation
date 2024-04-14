package sn.uasz.gestion_reservation_uasz.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.http.HttpMethod;

import lombok.AllArgsConstructor;

/**
 * Configuration de la sécurité pour l'application.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	private BCryptPasswordEncoder passwordEncoder;
	private JwtFilter jwtFilter;

	/**
	 * Configure la chaîne de filtres de sécurité.
	 * 
	 * @param http Objet HttpSecurity
	 * @return Objet SecurityFilterChain
	 * @throws Exception Exception en cas de problème de configuration
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.cors() // Ajoute la configuration CORS
				.and()
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(
							"/api/v1/auth/**",
								"/v2/api-docs/**",
								"/v3/api-docs/**",
								"/v3/api-docs",
								"/swagger-resources/**",
								"/swagger-resources",
								"/configuration/ui",
								"/configuration/security",
								"/swagger-ui/**",
								"/webjars/**",
								"/swagger-ui.html")
						.permitAll()
						.requestMatchers("/inscription").permitAll()
						.requestMatchers("/connexion").permitAll()
						.anyRequest().authenticated())
				.sessionManagement((session) -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Configure le gestionnaire d'authentification.
	 * 
	 * @param authenticationConfiguration Objet AuthenticationConfiguration
	 * @return Gestionnaire d'authentification
	 * @throws Exception Exception en cas de problème de configuration
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * Configure le fournisseur d'authentification.
	 * 
	 * @param userDetailsService Objet UserDetailsService
	 * @return Fournisseur d'authentification
	 */
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
		return daoAuthenticationProvider;
	}

	/**
	 * Configure la politique CORS (Cross-Origin Resource Sharing).
	 * 
	 * @return Objet CorsConfigurationSource
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Autoriser toutes les origines
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Méthodes HTTP
																									// autorisées
		configuration.setAllowedHeaders(Arrays.asList("*")); // Tous les en-têtes autorisés
		configuration.setAllowCredentials(true); // Autoriser les cookies
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
