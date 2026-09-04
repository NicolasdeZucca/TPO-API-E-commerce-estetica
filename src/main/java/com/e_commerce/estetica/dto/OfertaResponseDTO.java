package com.e_commerce.estetica.dto;

import com.e_commerce.estetica.model.Oferta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfertaResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private Double porcentajeDescuento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean activa;
    private Long productoId;
    private String productoNombre;

    public OfertaResponseDTO(Oferta oferta) {
        this.id = oferta.getId();
        this.titulo = oferta.getTitulo();
        this.descripcion = oferta.getDescripcion();
        this.porcentajeDescuento = oferta.getPorcentajeDescuento();
        this.fechaInicio = oferta.getFechaInicio();
        this.fechaFin = oferta.getFechaFin();
        this.activa = oferta.getActiva();
        if (oferta.getProducto() != null) {
            this.productoId = oferta.getProducto().getId();
            this.productoNombre = oferta.getProducto().getNombre();
        }
    }

    // Compatibilidad por si algún componente anterior aún lee precioDescuento
    public Double getPrecioDescuento() {
        return porcentajeDescuento;
    }
}
