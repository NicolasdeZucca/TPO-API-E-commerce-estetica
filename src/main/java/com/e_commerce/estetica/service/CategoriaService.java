package com.e_commerce.estetica.service;

import com.e_commerce.estetica.exception.BadRequestException;
import com.e_commerce.estetica.exception.DuplicateResourceException;
import com.e_commerce.estetica.exception.ResourceNotFoundException;
import com.e_commerce.estetica.model.Categoria;
import com.e_commerce.estetica.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> traerCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
    }

    public Categoria crearCategoria(Categoria categoria) {
        validarNombre(categoria.getNombre());
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new DuplicateResourceException(
                    "Ya existe una categoría con el nombre: " + categoria.getNombre());
        }
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria datos) {
        Categoria existente = buscarPorId(id);
        validarNombre(datos.getNombre());

        if (!existente.getNombre().equals(datos.getNombre())
                && categoriaRepository.existsByNombre(datos.getNombre())) {
            throw new DuplicateResourceException(
                    "Ya existe una categoría con el nombre: " + datos.getNombre());
        }

        existente.setNombre(datos.getNombre());
        return categoriaRepository.save(existente);
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = buscarPorId(id);
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            throw new BadRequestException("No se puede eliminar una categoría que tiene productos asociados");
        }
        categoriaRepository.delete(categoria);
    }

    public void crearSiNoExiste(String nombre) {
        if (!categoriaRepository.existsByNombre(nombre)) {
            categoriaRepository.save(new Categoria(null, nombre, null));
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new BadRequestException("El nombre de la categoría es obligatorio");
        }
    }
}
