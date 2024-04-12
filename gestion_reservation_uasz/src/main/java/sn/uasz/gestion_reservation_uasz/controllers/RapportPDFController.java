package sn.uasz.gestion_reservation_uasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import sn.uasz.gestion_reservation_uasz.services.RapportPDFService;

@RestController
@RequestMapping(path = "rapport")
public class RapportPDFController {
    @Autowired
    private RapportPDFService rapportPDFService;

    @GetMapping(value = "/reservation-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public void genererRapportPDF(HttpServletResponse response) {
        rapportPDFService.genererRapportPDF(response);
    }

    @GetMapping(value = "/mensuel", produces = MediaType.APPLICATION_PDF_VALUE)
    public void genererRapportMensuel(HttpServletResponse response) {
        rapportPDFService.genererRapportMensuel(response);
    }
}
