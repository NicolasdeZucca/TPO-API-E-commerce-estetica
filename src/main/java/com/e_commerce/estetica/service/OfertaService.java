package com.e_commerce.estetica.service;

import com.e_commerce.estetica.dto.OfertaRequestDTO;
import com.e_commerce.estetica.dto.OfertaResponseDTO;
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

    public List<OfertaResponseDTO> traerOfertas() {
        return ofertaRepository.findAll()
                .stream()
                .map(OfertaResponseDTO::new)
                .toList();
    }

    public OfertaResponseDTO buscarOfertaPorId(Long id) {
        Oferta oferta = buscarEntidadPorId(id);
        return new OfertaResponseDTO(oferta);
    }

    public Oferta buscarEntidadPorId(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta", id));
    }

    public OfertaResponseDTO crearOferta(OfertaRequestDTO dto) {
        validarOferta(dto);
        Oferta nuevaOferta = new Oferta();
        nuevaOferta.setTitulo(dto.getTitulo());
        nuevaOferta.setDescripcion(dto.getDescripcion());
        nuevaOferta.setPorcentajeDescuento(dto.getPrecioDescuento());
        Oferta guardada = ofertaRepository.save(nuevaOferta);
        return new OfertaResponseDTO(guardada);
    }

    public OfertaResponseDTO actualizarOferta(Long id, OfertaRequestDTO dto) {
        Oferta existente = buscarEntidadPorId(id);

        validarOferta(dto);

        existente.setTitulo(dto.getTitulo());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPorcentajeDescuento(dto.getPrecioDescuento());

        Oferta actualizada = ofertaRepository.save(existente);
        return new OfertaResponseDTO(actualizada);
    }

    public void eliminarOferta(Long id) {
        if (!ofertaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Oferta", id);
        }
        ofertaRepository.deleteById(id);
    }

    private void validarOferta(OfertaRequestDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new BadRequestException("El título de la oferta es obligatorio");
        }
        if (dto.getPrecioDescuento() == null || dto.getPrecioDescuento() <= 0) {
            throw new BadRequestException("El precio de descuento debe ser mayor a cero");
        }
    }
}
