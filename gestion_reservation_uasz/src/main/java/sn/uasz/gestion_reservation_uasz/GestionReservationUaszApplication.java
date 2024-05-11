package sn.uasz.gestion_reservation_uasz;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import sn.uasz.gestion_reservation_uasz.models.Reservation;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.models.Role;
import sn.uasz.gestion_reservation_uasz.models.TypeRole;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.RoleRepository;
import sn.uasz.gestion_reservation_uasz.repositories.UtilisateurRepository;
import sn.uasz.gestion_reservation_uasz.services.ReservationService;
import sn.uasz.gestion_reservation_uasz.services.RessourceService;

/**
 * Classe principale de l'application de gestion des réservations UASZ.
 * Implémente l'interface CommandLineRunner pour exécuter des opérations lors du
 * démarrage de l'application.
 */
@Slf4j
// @SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@SpringBootApplication
public class GestionReservationUaszApplication implements CommandLineRunner {

	@Autowired
	private RessourceService ressourceService;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UtilisateurRepository uRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Point d'entrée de l'application.
	 * Lance l'exécution de l'application Spring Boot.
	 * 
	 * @param args Les arguments de la ligne de commande.
	 */
	public static void main(String[] args) {
		SpringApplication.run(GestionReservationUaszApplication.class, args);
	}

	/**
	 * Méthode exécutée lors du démarrage de l'application.
	 * Charge les rôles et un utilisateur administrateur dans la base de données.
	 * 
	 * @param args Les arguments de la ligne de commande.
	 * @throws Exception Si une erreur survient lors de l'exécution.
	 */
	@Override
	public void run(String... args) throws Exception {
		log.info("Application en cours d'exécution...");

		// // Création des rôles
		// Role role1 = new Role(null, TypeRole.UTILISATEUR,null);
		// Role role2 = new Role(null, TypeRole.RESPONSABLE, null);
		// Role role3 = new Role(null, TypeRole.ADMINISTRATEUR,null);

		// // Sauvegarde des rôles dans la base de données
		// roleRepository.save(role1);
		// roleRepository.save(role2);
		// roleRepository.save(role3);

		// // Création de l'utilisateur administrateur
		// Utilisateur admin = new Utilisateur(null, "admin", "admin", null,
		// "admin@uasz.sn",passwordEncoder.encode("admin") , true, role3, null, null);

		// // Sauvegarde de l'utilisateur administrateur dans la base de données
		// uRepository.save(admin);

	}

}
