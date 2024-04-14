package sn.uasz.gestion_reservation_uasz.models;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe pour générer un rapport PDF des réservations.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapportPDF {

    private List<Reservation> reservations;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Génère un rapport PDF à partir de la liste de réservations donnée et envoie le résultat à la réponse HTTP.
     * 
     * @param response la réponse HTTP à laquelle envoyer le rapport PDF
     * @throws DocumentException si une erreur survient lors de la création du document PDF
     * @throws IOException si une erreur survient lors de l'écriture du document PDF dans la réponse HTTP
     */
    public void genererRapport(HttpServletResponse response) throws DocumentException, IOException {
        // Création du document PDF avec taille de page A4
        Document document = new Document(PageSize.A4);

        // Obtenir l'instance du PdfWriter à partir de la réponse HTTP
        PdfWriter.getInstance(document, response.getOutputStream());

        // Ouverture du document
        document.open();

        // Chemin de l'image à utiliser dans le rapport (assurez-vous que l'image est accessible dans votre projet)
        String imagePath = "src/main/resources/assets/uasz.jpeg";

        // Chargement de l'image dans le document PDF
        Image image = Image.getInstance(imagePath);

        // Définition de la taille de l'image en centimètres
        float widthInCm = 2.78f;
        float heightInCm = 2.78f;

        // Conversion des centimètres en points (1 pouce = 2.54 cm, 1 pouce = 72 points)
        float widthInPoints = widthInCm * 72 / 2.54f;
        float heightInPoints = heightInCm * 72 / 2.54f;

        // Définition de la taille de l'image en points
        image.scaleAbsolute(widthInPoints, heightInPoints);

        // Création d'une cellule PDF pour contenir l'image
        PdfPCell imageCell = new PdfPCell();
        imageCell.addElement(image);
        imageCell.setBorder(PdfPCell.NO_BORDER);
        imageCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        // Création d'une cellule PDF pour les informations
        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(PdfPCell.NO_BORDER);
        infoCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        // Ajout des informations (Nom, Prénom, Date, Numéro de rapport) à la cellule
        infoCell.addElement(new Phrase("Nom: John Doe\nPrénom: Jane\nDate: 2022-04-06\nNuméro du rapport: 12345"));

        // Création de l'entête du rapport
        PdfPTable headers = new PdfPTable(2);
        headers.setWidthPercentage(100f);
        headers.setWidths(new int[] { 2, 1 });
        headers.addCell(imageCell);
        headers.addCell(infoCell);
        document.add(headers);

        // Création du titre du rapport
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        fontTitle.setStyle(Font.BOLD | Font.UNDERLINE);
        Paragraph paragraph = new Paragraph("Rapport sur les réservations", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph.setSpacingAfter(10);
        document.add(paragraph);

        // Création du tableau des réservations
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 1, 5, 4, 2, 2, 4 });
        table.setSpacingBefore(5);

        // Création de l'en-tête du tableau
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.BLACK);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(14);
        font.setColor(CMYKColor.WHITE);
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Matériel Réservé", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date Prévue de Réservation", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Heure Début (en H)", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Heure Fin (en H)", font));
        table.addCell(cell);
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date de Réservation", font));

        // Création d'un objet Font avec une taille de police de 12
        Font fontCell = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);

        // Itération sur la liste des réservations
        for (Reservation r : reservations) {
            // Ajout des cellules à la table
            PdfPCell cell0 = new PdfPCell(new Phrase(String.valueOf(r.getIdReservation()), fontCell));
            PdfPCell cell1 = new PdfPCell(new Phrase(r.getRessource().getLibelleRessource(), fontCell));
            PdfPCell cell2 = new PdfPCell(new Phrase(dateFormat.format(r.getDatePrevue()), fontCell));
            PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(r.getHeureDebut()), fontCell));
            PdfPCell cell4 = new PdfPCell(new Phrase(String.valueOf(r.getHeureFin()), fontCell));
            PdfPCell cell5 = new PdfPCell(new Phrase(dateFormat.format(r.getDateReservation()), fontCell));
            table.addCell(cell0);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
        }

        // Ajout du tableau créé au document
        document.add(table);

        // Fermeture du document
        document.close();
    }
}
