package com.e_commerce.estetica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfertaRequestDTO {

    @NotBlank(message = "El título de la oferta es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción de la oferta es obligatoria")
    private String descripcion;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Positive(message = "El porcentaje de descuento debe ser mayor a cero")
    private Double porcentajeDescuento;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime fechaFin;

    private Boolean activa = false;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    // Métodos de compatibilidad con código previo que referenciaba precioDescuento
    public Double getPrecioDescuento() {
        return porcentajeDescuento;
    }

    public void setPrecioDescuento(Double precioDescuento) {
        this.porcentajeDescuento = precioDescuento;
    }
}
