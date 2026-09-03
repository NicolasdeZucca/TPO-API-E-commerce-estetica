package com.e_commerce.estetica.repository;

import com.e_commerce.estetica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}