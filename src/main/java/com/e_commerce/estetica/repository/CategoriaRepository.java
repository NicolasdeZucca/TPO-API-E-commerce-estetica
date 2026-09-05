package com.e_commerce.estetica.repository;

import com.e_commerce.estetica.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	boolean existsByNombre(String nombre);
}
