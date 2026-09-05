package com.e_commerce.estetica.service;

import com.e_commerce.estetica.dto.ProductoRequestDTO;
import com.e_commerce.estetica.dto.ProductoResponseDTO;
import com.e_commerce.estetica.exception.BadRequestException;
import com.e_commerce.estetica.exception.ResourceNotFoundException;
import com.e_commerce.estetica.model.Producto;
import com.e_commerce.estetica.model.Categoria;
import com.e_commerce.estetica.repository.CategoriaRepository;
import com.e_commerce.estetica.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProductoResponseDTO> traerProductos() {
        return productoRepository.findAllByOrderByNombreAsc()
                .stream()
                .map(ProductoResponseDTO::new)
                .toList();
    }

    public ProductoResponseDTO buscarPorId(Long id) {
        Producto producto = buscarEntidadPorId(id);
        return new ProductoResponseDTO(producto);
    }

    public Producto buscarEntidadPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
    }

    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        validarProducto(dto);
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(buscarCategoriaPorId(dto.getCategoriaId()));
        Producto guardado = productoRepository.save(producto);
        return new ProductoResponseDTO(guardado);
    }

    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        Producto prodDb = buscarEntidadPorId(id);

        validarProducto(dto);

        prodDb.setNombre(dto.getNombre());
        prodDb.setDescripcion(dto.getDescripcion());
        prodDb.setMarca(dto.getMarca());
        prodDb.setPrecio(dto.getPrecio());
        prodDb.setStock(dto.getStock());
        prodDb.setCategoria(buscarCategoriaPorId(dto.getCategoriaId()));

        Producto actualizado = productoRepository.save(prodDb);
        return new ProductoResponseDTO(actualizado);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", id);
        }
        productoRepository.deleteById(id);
    }

    // Validaciones de negocio reutilizadas por crear y actualizar
    private void validarProducto(ProductoRequestDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del producto es obligatorio");
        }
        if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
            throw new BadRequestException("El precio debe ser mayor a cero");
        }
    }

    private Categoria buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
    }
}