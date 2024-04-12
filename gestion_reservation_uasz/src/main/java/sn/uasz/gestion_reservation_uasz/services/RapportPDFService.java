package sn.uasz.gestion_reservation_uasz.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import sn.uasz.gestion_reservation_uasz.models.RapportPDF;
import sn.uasz.gestion_reservation_uasz.models.Reservation;

@Service
@Transactional
public class RapportPDFService {
    @Autowired
    private ReservationService reservationService;

    public void genererRapportPDF(HttpServletResponse response) {
        try {
            List<Reservation> reservations = reservationService.listReservations(); 
            
            // Configuration de la réponse HTTP pour le téléchargement du fichier PDF
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=rapport_des_reservations_du_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);

            RapportPDF rapportPDF = new RapportPDF();
            rapportPDF.setReservations(reservations);
            rapportPDF.genererRapport(response);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void genererRapportMensuel(HttpServletResponse response){
        try{
            Calendar calendrier = Calendar.getInstance();
            int moisActuel = calendrier.get(Calendar.MONTH); // Ajoutez 1 car les mois commencent à partir de 0
            int anneeActuelle = calendrier.get(Calendar.YEAR);

             // Définir la date de début et de fin du mois actuel
             Calendar debutMois = Calendar.getInstance();
             debutMois.set(Calendar.YEAR, anneeActuelle);
             debutMois.set(Calendar.MONTH, moisActuel); 
             debutMois.set(Calendar.DAY_OF_MONTH, 1);
             debutMois.set(Calendar.HOUR_OF_DAY, 0);
             debutMois.set(Calendar.MINUTE, 0);
             debutMois.set(Calendar.SECOND, 0);
             
             Calendar finMois = Calendar.getInstance();
             finMois.set(Calendar.YEAR, anneeActuelle);
             finMois.set(Calendar.MONTH, moisActuel);
             finMois.set(Calendar.DAY_OF_MONTH, finMois.getActualMaximum(Calendar.DAY_OF_MONTH));
             finMois.set(Calendar.HOUR_OF_DAY, 23);
             finMois.set(Calendar.MINUTE, 59);
             finMois.set(Calendar.SECOND, 59);

            // Récupérer les réservations du mois en cours
            List<Reservation> reservations = reservationService.listReservationEntreDeuxDate(debutMois.getTime(), finMois.getTime());

            // Configuration de la réponse HTTP pour le téléchargement du fichier PDF
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("MM-yyyy");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=rapport_des_reservations_du_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);

            // Générer le rapport PDF avec les réservations filtrées
            RapportPDF rapportPDF = new RapportPDF();
            rapportPDF.setReservations(reservations);
            rapportPDF.genererRapport(response);


        }catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

    }
}
