package com.e_commerce.estetica.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    // Útil para mensajes uniformes: ("Producto", 5) -> "Producto no encontrado con id: 5"
    public ResourceNotFoundException(String recurso, Object id) {
        super(recurso + " no encontrado con id: " + id, HttpStatus.NOT_FOUND);
    }
}
