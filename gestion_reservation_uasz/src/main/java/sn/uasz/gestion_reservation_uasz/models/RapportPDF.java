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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapportPDF {
    // private List<Reservation> reservations;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public void genererRapport(HttpServletResponse response) throws DocumentException, IOException{
        //creation du document
        Document document = new Document(PageSize.A4);

        //avoir l'instance du pdfWriter
        PdfWriter.getInstance(document, response.getOutputStream());

        //ouverture du document
        document.open();

        // Chemin de votre image (assurez-vous que l'image est accessible dans votre projet)
        String imagePath = "src/main/resources/assets/uasz.jpeg";

        // Chargez l'image dans le document PDF
        Image image = Image.getInstance(imagePath);
        
        // Définissez la taille de l'image en centimètres
        float widthInCm = 2.78f;
        float heightInCm = 2.78f;

        // Convertissez les centimètres en points (1 pouce = 2.54 cm, 1 pouce = 72 points)
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

        // Ajoutez les informations (Nom, Prénom, Date, Numéro de rapport) à la cellule
        infoCell.addElement(new Phrase("Nom: John Doe\nPrénom: Jane\nDate: 2022-04-06\nNuméro du rapport: 12345"));

        //Creation de l'entete
        PdfPTable headers = new PdfPTable(2);

        // Setting width of table, its columns and spacing
		headers.setWidthPercentage(100f);
		headers.setWidths(new int[] { 2, 1});
		

        headers.addCell(imageCell);
        headers.addCell(infoCell);
        document.add(headers);

        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        fontTitle.setStyle(Font.BOLD | Font.UNDERLINE);
        
        
        
        //Creation du pragraphe et aligner au centre
        Paragraph paragraph = new Paragraph("Rapport sur les reservations", fontTitle);
        paragraph.setAlignment(paragraph.ALIGN_CENTER);
        paragraph.setSpacingAfter(10);
        document.add(paragraph);

        //creation du tableau des  reservation
        PdfPTable table = new PdfPTable(6);

        // Setting width of table, its columns and spacing
		table.setWidthPercentage(100f);
		table.setWidths(new int[] {1, 5, 4, 2, 2,4 });
		table.setSpacingBefore(5);

        //Creation de l'entete du tableau
        PdfPCell cell = new PdfPCell();
        
        // Setting the background color and padding
		cell.setBackgroundColor(CMYKColor.BLACK);
		cell.setPadding(5);
        

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(14);
		font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell/ header
		// Adding Cell to table
        cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Materiel Reserve", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Date Prevue de Reservation", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Heure debut (en H) ", font));
		table.addCell(cell);
        cell.setPhrase(new Phrase("Heure Fin (en H) ", font));
		table.addCell(cell);
        table.addCell(cell);
		cell.setPhrase(new Phrase("Date de Reservation", font));


        // Créer un objet Font avec une taille de police de 12    
        Font fontCell = FontFactory.getFont(FontFactory.TIMES_ROMAN,   14);

        // Iterating over the list of reservations
        // for (Reservation r : reservations) {
        //     // Ajouter les cellules avec la police de taille 12
        //     PdfPCell cell0 = new PdfPCell(new Phrase(String.valueOf(r.getIdReservation()), fontCell));
        //     PdfPCell cell1 = new PdfPCell(new Phrase(r.getRessource().getLibelleRessource(), fontCell));
        //     PdfPCell cell2 = new PdfPCell(new Phrase(dateFormat.format(r.getDatePrevue()), fontCell));
        //     PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(r.getHeureDebut()), fontCell));
        //     PdfPCell cell4 = new PdfPCell(new Phrase(String.valueOf(r.getHeureFin()), fontCell));
        //     PdfPCell cell5 = new PdfPCell(new Phrase(dateFormat.format(r.getDateReservation()), fontCell));


        //     // Ajouter les cellules à la table
        //     table.addCell(cell0);
        //     table.addCell(cell1);
        //     table.addCell(cell2);
        //     table.addCell(cell3);
        //     table.addCell(cell4);
        //     table.addCell(cell5);
        // }
        // Adding the created table to document
		document.add(table);

        // Closing the document
		document.close();
    }
}
