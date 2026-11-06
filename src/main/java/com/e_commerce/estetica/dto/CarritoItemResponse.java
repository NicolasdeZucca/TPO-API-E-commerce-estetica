package com.e_commerce.estetica.dto;

public record CarritoItemResponse(
        Long id,
        String nombre,
        String categoria,
        Double precio,
        Integer cantidad,
        String formato,
        String imagen
) {
}
