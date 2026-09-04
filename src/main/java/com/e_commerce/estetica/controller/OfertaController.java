package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.dto.OfertaRequestDTO;
import com.e_commerce.estetica.dto.OfertaResponseDTO;
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
    public ResponseEntity<List<OfertaResponseDTO>> traerOfertas() {
        return ResponseEntity.ok(ofertaService.traerOfertas());
    }

    @PostMapping
    public ResponseEntity<OfertaResponseDTO> crearOferta(@RequestBody OfertaRequestDTO dto) {
        return new ResponseEntity<>(ofertaService.crearOferta(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ofertaService.buscarOfertaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfertaResponseDTO> actualizarOferta(@PathVariable Long id, @RequestBody OfertaRequestDTO dto) {
        return ResponseEntity.ok(ofertaService.actualizarOferta(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Long id) {
        ofertaService.eliminarOferta(id);
        return ResponseEntity.noContent().build();
    }
}
