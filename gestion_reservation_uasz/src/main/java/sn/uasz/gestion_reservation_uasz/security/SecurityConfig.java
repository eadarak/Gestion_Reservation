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

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	private BCryptPasswordEncoder passwordEncoder;
	private JwtFilter jwtFilter;

	// @Bean
	// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	// Exception {
	// http
	// .csrf(AbstractHttpConfigurer::disable)
	// .authorizeHttpRequests((authorize) -> authorize
	// .requestMatchers("/inscription").permitAll()
	// .requestMatchers("/connexion").permitAll()

	// // .requestMatchers("/reservation").hasRole("UTILISATEUR")
	// // .requestMatchers("/ressource").hasRole("RESPONSABLE")
	// // .requestMatchers("categorie").hasRole("RESPONSABLE")
	// // .requestMatchers( "/utilisateur").hasRole("ADMINISTRATEUR")

	// .anyRequest().authenticated())
	// .sessionManagement((session) -> session
	// .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	// .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	// return http.build();
	// }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.cors() // Ajouter la configuration CORS
				.and()
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/inscription").permitAll()
						.requestMatchers("/connexion").permitAll()
						.anyRequest().authenticated())
				.sessionManagement((session) -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
		return daoAuthenticationProvider;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Permettre toutes les origines
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Méthodes HTTP autorisées
		configuration.setAllowedHeaders(Arrays.asList("*")); // Tous les en-têtes autorisés
		configuration.setAllowCredentials(true); // Autoriser les cookies
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}


}
