package com.e_commerce.estetica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String marca;
    private Double precio;

    // Un producto puede tener muchas ofertas
    @OneToMany(mappedBy = "producto")
    @JsonIgnore // Evita loop infinito en JSON
    private List<Oferta> ofertas;
}
