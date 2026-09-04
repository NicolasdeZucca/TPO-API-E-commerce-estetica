package com.e_commerce.estetica.service;

import com.e_commerce.estetica.exception.BadRequestException;
import com.e_commerce.estetica.exception.ResourceNotFoundException;
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
        return ofertaRepository.findAll();
    }

    public Oferta buscarOfertaPorId(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta", id));
    }

    public Oferta crearOferta(Oferta nuevaOferta) {
        validarOferta(nuevaOferta);
        return ofertaRepository.save(nuevaOferta);
    }

    public Oferta actualizarOferta(Long id, Oferta ofertaActualizada) {
        Oferta ofertaExistente = buscarOfertaPorId(id);

        validarOferta(ofertaActualizada);

        ofertaExistente.setTitulo(ofertaActualizada.getTitulo());
        ofertaExistente.setDescripcion(ofertaActualizada.getDescripcion());
        ofertaExistente.setPrecioDescuento(ofertaActualizada.getPrecioDescuento());

        return ofertaRepository.save(ofertaExistente);
    }

    public void eliminarOferta(Long id) {
        if (!ofertaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Oferta", id);
        }
        ofertaRepository.deleteById(id);
    }

    private void validarOferta(Oferta oferta) {
        if (oferta.getTitulo() == null || oferta.getTitulo().isBlank()) {
            throw new BadRequestException("El título de la oferta es obligatorio");
        }
        if (oferta.getPrecioDescuento() == null || oferta.getPrecioDescuento() <= 0) {
            throw new BadRequestException("El precio con descuento debe ser mayor a cero");
        }
    }
}
