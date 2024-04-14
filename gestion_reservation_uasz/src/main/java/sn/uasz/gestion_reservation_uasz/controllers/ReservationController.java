package sn.uasz.gestion_reservation_uasz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.uasz.gestion_reservation_uasz.models.Reservation;
import sn.uasz.gestion_reservation_uasz.services.ReservationService;

@RestController
@RequestMapping(path = "reservation")
public class ReservationController {
    @Autowired
    private ReservationService rService;
    
    @GetMapping
    public ResponseEntity<List<Reservation>> list_reservations() {
        List<Reservation> reservations = rService.listReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/mois")
    public ResponseEntity<List<Reservation>> list_reservations_du_mois() {
        List<Reservation> reservations = rService.listerReservationsDuMois();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Reservation> find_reservation(@PathVariable("id") Long id) {
        return new ResponseEntity<>(rService.findReservation(id), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> add_reservation(@RequestBody Reservation r) {
        Reservation createdReservation = rService.creationReservation(r);
        if (createdReservation == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Reservation impossible.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    
    @PutMapping("/{id}")
    public  ResponseEntity<?> put_reservation(@PathVariable("id") Long id, @RequestBody Reservation r) {
        Reservation res = rService.updateReservation(r, id);
        if(res == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("La ressource est déjà réservée ou indisponible.");
        }
      return  new ResponseEntity<>(res, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete_reservation(@PathVariable("id") Long id){
        rService.DeleteReservation(id);
        return ResponseEntity.status(HttpStatus.OK).body("La reservation " + id +" a ete supprime.");
    }

    @DeleteMapping("/annuler-reservation/{id}")
    public ResponseEntity<?> annuler_reservation(@PathVariable("id") Long id){
        rService.annuler_reservation(id);
        return ResponseEntity.status(HttpStatus.OK).body("La reservation " + id +" a ete annulee.");
    }
    
}