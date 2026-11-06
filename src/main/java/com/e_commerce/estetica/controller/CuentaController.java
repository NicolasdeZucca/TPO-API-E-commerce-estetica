package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.dto.CarritoItemRequest;
import com.e_commerce.estetica.dto.CarritoItemResponse;
import com.e_commerce.estetica.dto.CarritoRequest;
import com.e_commerce.estetica.dto.FavoritosRequest;
import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.service.CuentaService;
import com.e_commerce.estetica.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/auth/me")
    public UsuarioResponse perfil(@AuthenticationPrincipal UserDetails principal) {
        return new UsuarioResponse(cuentaService.obtenerUsuario(principal.getUsername()));
    }

    @GetMapping("/carrito")
    public List<CarritoItemResponse> carrito(@AuthenticationPrincipal UserDetails principal) {
        return cuentaService.obtenerCarrito(principal.getUsername());
    }

    @PutMapping("/carrito")
    public List<CarritoItemResponse> actualizarCarrito(
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody CarritoRequest request
    ) {
        return cuentaService.reemplazarCarrito(principal.getUsername(), request.items());
    }

    @DeleteMapping("/carrito")
    public ResponseEntity<Void> vaciarCarrito(@AuthenticationPrincipal UserDetails principal) {
        cuentaService.vaciarCarrito(principal.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favoritos")
    public List<Long> favoritos(@AuthenticationPrincipal UserDetails principal) {
        return cuentaService.obtenerFavoritos(principal.getUsername());
    }

    @PutMapping("/favoritos")
    public List<Long> actualizarFavoritos(
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody FavoritosRequest request
    ) {
        return cuentaService.reemplazarFavoritos(principal.getUsername(), request);
    }
}
