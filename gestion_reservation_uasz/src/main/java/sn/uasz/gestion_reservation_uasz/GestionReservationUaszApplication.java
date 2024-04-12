package sn.uasz.gestion_reservation_uasz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import sn.uasz.gestion_reservation_uasz.models.Ressource;
import sn.uasz.gestion_reservation_uasz.services.RessourceService;

@Slf4j
@SpringBootApplication
public class GestionReservationUaszApplication implements CommandLineRunner{

	@Autowired
	private RessourceService ressourceService;

	// @Autowired
	// private ReservationService reservationService;

	public static void main(String[] args) {
		SpringApplication.run(GestionReservationUaszApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
		log.info("Application is running...");

		Ressource r1 = new Ressource(null,  "Projecteur interactif", null, "Projecteur ultra-courte focale avec fonctionnalité tactile pour les présentations interactives.", null);
		Ressource r2 = new Ressource(null,  "Tablette graphique", null, "Tablette professionnelle avec stylet sensible à la pression, idéale pour le design graphique.", null);
		Ressource r3 = new Ressource(null, "Écran interactif", null, "Grand écran tactile interactif pour des présentations dynamiques et collaboratives.", null);
		Ressource r4 = new Ressource(null, "Imprimante 3D", null, "Imprimante 3D haute précision pour la fabrication de prototypes et d'objets personnalisés.", null);
		Ressource r5 = new Ressource(null, "Scanner de documents", null, "Scanner professionnel de documents avec une vitesse de numérisation élevée et une qualité supérieure.", null);

		
		Ressource[] ressources = {r1, r2, r3, r4, r5};

		for (int i = 0; i < ressources.length; i++) {
			ressourceService.creerRessource(ressources[i]);
		}
		// for (Ressource ressource : ressources) {
		// 	for (int i = 1; i <= 5; i++) {
		// 		// Créer une nouvelle réservation pour la ressource actuelle
		// 		Reservation reservation = new Reservation();
		// 		reservation.setRessource(ressource);
		// 		reservation.setHeureDebut(9 + i);
		// 		reservation.setHeureFin(11 + i);
		
		// 		LocalDate datePrevue = LocalDate.of(2024, i + 1, 4 + 2 * i);
		// 		reservation.setDatePrevue(java.sql.Date.valueOf(datePrevue));
		
		// 		LocalDate dateReservation;
		// 		if (i % 2 == 0) {
		// 			// Définir la date de réservation pour le 4 janvier 2024
		// 			dateReservation = LocalDate.of(2024, 1, 4);
		// 		} else {
		// 			// Définir la date de réservation pour le 25 mars 2024
		// 			dateReservation = LocalDate.of(2024, 3, 25);
		// 		}
		// 		reservation.setDateReservation(java.sql.Date.valueOf(dateReservation));
		
		// 		reservationService.creationReservation(reservation);
		// 	}
		// }
    }

}
