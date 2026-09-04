package com.e_commerce.estetica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfertaRequestDTO {

    private String titulo;
    private String descripcion;
    private Double precioDescuento;
}
