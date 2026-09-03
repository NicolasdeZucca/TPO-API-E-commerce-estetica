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


    // --- AGREGO 3 ENDPOINTS NUEVOS DEL CRUD ---

    @GetMapping("/{id}")
    public ResponseEntity<Oferta> buscarPorId(@PathVariable Long id) {
        Oferta oferta = ofertaService.buscarOfertaPorId(id);
        if (oferta != null) {
            return new ResponseEntity<>(oferta, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oferta> actualizarOferta(@PathVariable Long id, @RequestBody Oferta oferta) {
        Oferta actualizada = ofertaService.actualizarOferta(id, oferta);
        if (actualizada != null) {
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Long id) {
        ofertaService.eliminarOferta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 2ESTE ES EL CÓDIGO QUE SE AGREGA PARA ELIMINAR UNA OFERTA
    }




}
