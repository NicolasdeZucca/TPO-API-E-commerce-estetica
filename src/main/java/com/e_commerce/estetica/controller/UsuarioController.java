package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.dto.UsuarioResponse;
import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<UsuarioResponse>> traerTodos() {
        return ResponseEntity.ok(usuarioService.traerUsuarios());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.crearUsuario(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}