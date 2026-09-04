package com.e_commerce.estetica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private Double precioDescuento;

    // Muchas ofertas pueden aplicar a un producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
