package com.e_commerce.estetica.service;

import com.e_commerce.estetica.model.Oferta;
import com.e_commerce.estetica.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    public List<Oferta> traerOfertas() {
        // agregar validaciones más adelante
        return ofertaRepository.findAll();
    }

    public Oferta crearOferta(Oferta nuevaOferta) {
        // validar que la oferta no sea nula eje
        return ofertaRepository.save(nuevaOferta);
    }
}
