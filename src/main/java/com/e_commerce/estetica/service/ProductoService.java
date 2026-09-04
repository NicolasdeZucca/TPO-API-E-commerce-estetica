package com.e_commerce.estetica.service;

import com.e_commerce.estetica.exception.BadRequestException;
import com.e_commerce.estetica.exception.ResourceNotFoundException;
import com.e_commerce.estetica.model.Producto;
import com.e_commerce.estetica.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> traerProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
    }

    public Producto crearProducto(Producto producto) {
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto prodAct) {
        Producto prodDb = buscarPorId(id);   // si no existe, ya lanza 404 acá

        validarProducto(prodAct);

        prodDb.setNombre(prodAct.getNombre());
        prodDb.setMarca(prodAct.getMarca());
        prodDb.setPrecio(prodAct.getPrecio());

        return productoRepository.save(prodDb);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", id);
        }
        productoRepository.deleteById(id);
    }

    // Validaciones de negocio reutilizadas por crear y actualizar
    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new BadRequestException("El precio debe ser mayor a cero");
        }
    }
}