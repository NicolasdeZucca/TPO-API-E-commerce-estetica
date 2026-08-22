package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.model.Oferta;
import com.e_commerce.estetica.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaRepository ofertaService;

    // ENDPOINT TRAER TODAS LAS OFERTAS (CON GET)
    @GetMapping
    public ResponseEntity<List<Oferta>> traerOfertas() {
        List<Oferta> ofertas = ofertaRepository.findAll();
        return new ResponseEntity<>(ofertas, HttpStatus.OK);
    }

    // ENDPOINT CREAR UNA NUEVA OFERTA (POST)
    @PostMapping
    public ResponseEntity<Oferta> crearOferta(@RequestBody Oferta nuevaOferta) {
        Oferta ofertaGuardada = ofertaRepository.save(nuevaOferta);
        return new ResponseEntity<>(ofertaGuardada, HttpStatus.CREATED);
    }
}
