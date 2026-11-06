package com.e_commerce.estetica.repository;

import com.e_commerce.estetica.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByUsuario_EmailOrderByFechaDesc(String email);
}
