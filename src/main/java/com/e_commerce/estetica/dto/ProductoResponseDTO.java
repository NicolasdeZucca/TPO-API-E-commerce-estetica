package com.e_commerce.estetica.dto;

import com.e_commerce.estetica.model.Producto;
import com.e_commerce.estetica.model.ProductoImagen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String marca;
    private Double precio;
    private Integer stock;
    private Long categoriaId;
    private String categoriaNombre;
    private List<String> imagenes;

    public ProductoResponseDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.marca = producto.getMarca();
        this.precio = producto.getPrecio();
        this.stock = producto.getStock();
        if (producto.getCategoria() != null) {
            this.categoriaId = producto.getCategoria().getId();
            this.categoriaNombre = producto.getCategoria().getNombre();
        }
        if (producto.getImagenes() != null) {
            this.imagenes = producto.getImagenes().stream()
                    .map(ProductoImagen::getUrlImagen)
                    .toList();
        }
    }
}
