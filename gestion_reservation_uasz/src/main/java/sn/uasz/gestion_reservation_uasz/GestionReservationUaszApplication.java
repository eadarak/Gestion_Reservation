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

@Slf4j
// @SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@SpringBootApplication
public class GestionReservationUaszApplication implements CommandLineRunner{

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
	public static void main(String[] args) {
		SpringApplication.run(GestionReservationUaszApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
		log.info("Application is running...");

		// Ressource r1 = new Ressource(null,  "Projecteur interactif", null, "Projecteur ultra-courte focale avec fonctionnalité tactile pour les présentations interactives.", null,null, null, null, null);
		// Ressource r2 = new Ressource(null,  "Tablette graphique", null, "Tablette professionnelle avec stylet sensible à la pression, idéale pour le design graphique.", null,null, null, null, null);
		// Ressource r3 = new Ressource(null, "Écran interactif", null, "Grand écran tactile interactif pour des présentations dynamiques et collaboratives.", null,null, null, null, null);
		// Ressource r4 = new Ressource(null, "Imprimante 3D", null, "Imprimante 3D haute précision pour la fabrication de prototypes et d'objets personnalisés.", null,null, null, null, null);
		// Ressource r5 = new Ressource(null, "Scanner de documents", null, "Scanner professionnel de documents avec une vitesse de numérisation élevée et une qualité supérieure.", null,null, null, null, null);

		
		// Ressource[] ressources = {r1, r2, r3, r4, r5};

		// for (int i = 0; i < ressources.length; i++) {
		// 	ressourceService.creerRessource(ressources[i]);
		// }
		// for (Ressource ressource : ressources) {
		// 	for (int i = 1; i <= 5; i++) {
		// 		// Créer une nouvelle réservation pour la ressource actuelle
		// 		Reservation reservation = new Reservation();
		// 		reservation.setRessource(ressource);
		// 		reservation.setHeureDebut(9 + i);
		// 		reservation.setHeureFin(11 + i);
		
		// 		LocalDate datePrevue = LocalDate.of(2024, i + 5, 4 + 2 * i);
		// 		reservation.setDatePrevue(java.sql.Date.valueOf(datePrevue));
		
		// 		LocalDate dateReservation;
		// 		if (i % 2 == 0) {
		// 			// Définir la date de réservation pour le 4 janvier 2024
		// 			dateReservation = LocalDate.of(2024, 4, 12);
		// 		} else {
		// 			// Définir la date de réservation pour le 25 mars 2024
		// 			dateReservation = LocalDate.of(2024, 5, 25);
		// 		}
		// 		reservation.setDateReservation(java.sql.Date.valueOf(dateReservation));
		
		// 		reservationService.creationReservation(reservation);
		// 	}
		// }

		Role role1 = new Role(null, TypeRole.UTILISATEUR);
		Role role2 = new Role(null, TypeRole.RESPONSABLE);
		Role role3 = new Role(null, TypeRole.ADMINISTRATEUR);

		roleRepository.save(role1);
		roleRepository.save(role2);
		roleRepository.save(role3);
		
		// Utilisateur admin = new Utilisateur(null, "admin", "admin", null, "admin@uasz.sn",passwordEncoder.encode("admin") , true, role3, null, null);

		// uRepository.save(admin);

    }

}
