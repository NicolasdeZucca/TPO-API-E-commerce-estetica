package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.model.Oferta;
import com.e_commerce.estetica.service.OfertaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")

   public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }


    // ENDPOINT TRAER TODAS LAS OFERTAS (CON GET)
    @GetMapping
    public ResponseEntity<List<Oferta>> traerOfertas() {
        List<Oferta> ofertas = ofertaService.traerOfertas();
        return new ResponseEntity<>(ofertas, HttpStatus.OK);
    }

    // ENDPOINT CREAR UNA NUEVA OFERTA (POST)
    @PostMapping
    public ResponseEntity<Oferta> crearOferta(@RequestBody Oferta nuevaOferta) {
        Oferta ofertaGuardada = ofertaService.crearOferta(nuevaOferta);
        return new ResponseEntity<>(ofertaGuardada, HttpStatus.CREATED);
    }
}
