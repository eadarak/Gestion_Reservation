package sn.uasz.gestion_reservation_uasz.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import sn.uasz.gestion_reservation_uasz.models.Rapport;
import sn.uasz.gestion_reservation_uasz.models.Reservation;
import sn.uasz.gestion_reservation_uasz.models.Utilisateur;
import sn.uasz.gestion_reservation_uasz.repositories.RapportRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class RapportService {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RapportRepository rapportRepository;

    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public Rapport obtenirDernierRapport() {
        return rapportRepository.findTopByOrderByDateCreationDesc();
    }

    public byte[] telechargerRapportMensuel() throws IOException {
        genererEtEnregistrerRapportMensuel();

        Rapport dernierRapport = obtenirDernierRapport();

        if (dernierRapport != null) {
            return dernierRapport.getContenuPDF();
        } else {
            throw new IOException("Aucun rapport mensuel trouvé");
        }
    }

    public void genererEtEnregistrerRapportMensuel() throws IOException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
                List<Reservation> reservationsDuMois = reservationService.listerReservationsDuMois();

                byte[] contenuPDF = genererContenuPDF(reservationsDuMois, utilisateur);

                Calendar calendar = Calendar.getInstance();
                int moisActuel = calendar.get(Calendar.MONTH) + 1;

                String titre = "Rapport mensuel des réservations_" + obtenirNomMois(moisActuel);
                Rapport rapport = new Rapport();
                rapport.setTitre(titre);
                rapport.setContenuPDF(contenuPDF);
                rapportRepository.save(rapport);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private String obtenirNomMois(int numeroMois) {
        String[] mois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
                "Octobre", "Novembre", "Décembre" };
        return mois[numeroMois - 1];
    }

    private byte[] genererContenuPDF(List<Reservation> reservations, Utilisateur utilisateur) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            int nombreTotalRapports = getNombreTotalRapports();
            addReservationsToDocument(document, reservations, utilisateur, nombreTotalRapports);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public int getNombreTotalRapports() {
        return (int) rapportRepository.count();
    }

    private void addReservationsToDocument(Document document, List<Reservation> reservations, Utilisateur utilisateur,
            int nombreRapports) throws DocumentException, IOException {
        // Chemin de votre image (assurez-vous que l'image est accessible dans votre
        // projet)
        String imagePath = "src/main/resources/assets/uasz.jpeg";

        // Chargez l'image dans le document PDF
        Image image = Image.getInstance(imagePath);

        // Définissez la taille de l'image en centimètres
        float widthInCm = 2.78f;
        float heightInCm = 2.78f;

        // Convertissez les centimètres en points (1 pouce = 2.54 cm, 1 pouce = 72
        // points)
        float widthInPoints = widthInCm * 72 / 2.54f;
        float heightInPoints = heightInCm * 72 / 2.54f;

        // Définissez la taille de l'image en points
        image.scaleAbsolute(widthInPoints, heightInPoints);

        // Créez une cellule PDF pour contenir l'image
        PdfPCell imageCell = new PdfPCell();
        imageCell.addElement(image);
        imageCell.setBorder(PdfPCell.NO_BORDER);
        imageCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(PdfPCell.NO_BORDER);
        infoCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        // Ajoutez les informations de l'utilisateur et le nombre de rapports à la
        // cellule
        String infoUtilisateur = "Nom: " + utilisateur.getNom() + "\nPrénom: " + utilisateur.getPrenom() + "Email :"
                + utilisateur.getEmail() + "\nRaport #" + nombreRapports;
        infoCell.addElement(new Phrase(infoUtilisateur));

        // Création de l'en-tête avec l'image et les informations de l'utilisateur
        PdfPTable headers = new PdfPTable(2);
        headers.setWidthPercentage(100f);
        headers.setWidths(new int[] { 2, 1 });
        headers.addCell(imageCell);
        headers.addCell(infoCell);
        document.add(headers);

        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        fontTitle.setStyle(Font.BOLD | Font.UNDERLINE);

        // Création du paragraphe pour le titre du rapport
        Paragraph paragraph = new Paragraph("Rapport sur les réservations", fontTitle);
        paragraph.setAlignment(paragraph.ALIGN_CENTER);
        paragraph.setSpacingAfter(10);
        document.add(paragraph);

        // Création du tableau des réservations
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 1, 5, 4, 2, 2, 4 });
        table.setSpacingBefore(5);

        // Création de l'entête du tableau
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.BLACK);
        cell.setPadding(5);
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontHeader.setSize(14);
        fontHeader.setColor(CMYKColor.WHITE);
        cell.setPhrase(new Phrase("ID", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Matériel Réservé", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date Prévue de Réservation", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Heure début (en H) ", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Heure Fin (en H) ", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date de Réservation", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Réservée par", fontHeader));
        table.addCell(cell);

        // Créer un objet Font avec une taille de police de 12
        Font fontCell = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);

        // Itération sur la liste des réservations
        for (Reservation r : reservations) {
            // Ajouter les cellules avec la police de taille 12
            PdfPCell cell0 = new PdfPCell(new Phrase(String.valueOf(r.getIdReservation()), fontCell));
            PdfPCell cell1 = new PdfPCell(new Phrase(r.getRessource().getLibelleRessource(), fontCell));
            PdfPCell cell2 = new PdfPCell(new Phrase(dateFormat.format(r.getDatePrevue()), fontCell));
            PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(r.getHeureDebut()), fontCell));
            PdfPCell cell4 = new PdfPCell(new Phrase(String.valueOf(r.getHeureFin()), fontCell));
            PdfPCell cell5 = new PdfPCell(new Phrase(dateFormat.format(r.getDateReservation()), fontCell));
            PdfPCell cell6 = new PdfPCell(new Phrase(r.getUtilisateur().getEmail(), fontCell));

            // Ajouter les cellules à la table
            table.addCell(cell0);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
        }

        // Ajout du tableau au document
        document.add(table);
    }

}
