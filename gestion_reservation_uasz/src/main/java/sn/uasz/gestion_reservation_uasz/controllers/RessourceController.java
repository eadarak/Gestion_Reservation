package sn.uasz.gestion_reservation_uasz.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RessourceController {
    
    @PostMapping(path = "teste")
    public  String teste(){
        String s = "Bonjour" ;
        return  s ;
    }

    @PostMapping(path = "teste1")
    public  String teste1(){
        String s = "Bonjour 1" ;
        return  s ;
    }
}
