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

    @GetMapping
    public ResponseEntity<List<Oferta>> traerOfertas() {
        return ResponseEntity.ok(ofertaService.traerOfertas());
    }

    @PostMapping
    public ResponseEntity<Oferta> crearOferta(@RequestBody Oferta nuevaOferta) {
        return new ResponseEntity<>(ofertaService.crearOferta(nuevaOferta), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oferta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ofertaService.buscarOfertaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oferta> actualizarOferta(@PathVariable Long id, @RequestBody Oferta oferta) {
        return ResponseEntity.ok(ofertaService.actualizarOferta(id, oferta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Long id) {
        ofertaService.eliminarOferta(id);
        return ResponseEntity.noContent().build();
    }
}
