package sn.uasz.gestion_reservation_uasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import sn.uasz.gestion_reservation_uasz.models.Rapport; // Importez votre modèle Rapport
import sn.uasz.gestion_reservation_uasz.services.RapportService;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping("/rapport-mensuel")
    public ResponseEntity<Resource> telechargerRapportMensuel() {
        try {
            byte[] contenuPDF = rapportService.telechargerRapportMensuel();
            
            // Récupérez le rapport pour obtenir le titre
            Rapport rapport = rapportService.obtenirDernierRapport();
            String titreRapport = rapport != null ? rapport.getTitre() : "rapport_mensuel";

            ByteArrayResource resource = new ByteArrayResource(contenuPDF);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(resource.contentLength());
            headers.setContentDispositionFormData("attachment", titreRapport + "_.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
