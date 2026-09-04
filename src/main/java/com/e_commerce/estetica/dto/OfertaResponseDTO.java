package com.e_commerce.estetica.dto;

import com.e_commerce.estetica.model.Oferta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfertaResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private Double precioDescuento;

    public OfertaResponseDTO(Oferta oferta) {
        this.id = oferta.getId();
        this.titulo = oferta.getTitulo();
        this.descripcion = oferta.getDescripcion();
        this.precioDescuento = oferta.getPrecioDescuento();
    }
}
