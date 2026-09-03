package com.e_commerce.estetica.repository;

import com.e_commerce.estetica.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
