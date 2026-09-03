package com.e_commerce.estetica.service;

import com.e_commerce.estetica.model.Oferta;
import com.e_commerce.estetica.repository.OfertaRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OfertaService {

   private final OfertaRepository ofertaRepository;

    public OfertaService(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    public List<Oferta> traerOfertas() {
        // agregar validaciones más adelante
        return ofertaRepository.findAll();
    }

    public Oferta crearOferta(Oferta nuevaOferta) {
        // validar que la oferta no sea nula eje
        return ofertaRepository.save(nuevaOferta);
    }


     // --- LOS 3 MÉTODOS NUEVOS PARA HACER CRUD ---

    public Oferta buscarOfertaPorId(Long id) {
        return ofertaRepository.findById(id).orElse(null);
    }

    public Oferta actualizarOferta(Long id, Oferta ofertaActualizada) {
        Oferta ofertaExistente = buscarOfertaPorId(id);
        if (ofertaExistente != null) {
            ofertaExistente.setTitulo(ofertaActualizada.getTitulo());
            ofertaExistente.setDescripcion(ofertaActualizada.getDescripcion());
            ofertaExistente.setPrecioDescuento(ofertaActualizada.getPrecioDescuento());
            return ofertaRepository.save(ofertaExistente);
        }
        return null;
    }

    public void eliminarOferta(Long id) {
        ofertaRepository.deleteById(id);
    }




}
