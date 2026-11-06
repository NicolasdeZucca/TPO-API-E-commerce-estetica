package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.dto.PedidoResponse;
import com.e_commerce.estetica.service.PedidoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public PedidoResponse crear(@AuthenticationPrincipal UserDetails principal) {
        return pedidoService.crearPedido(principal.getUsername());
    }

    @GetMapping
    public List<PedidoResponse> listar(@AuthenticationPrincipal UserDetails principal) {
        return pedidoService.obtenerPedidos(principal.getUsername());
    }
}
