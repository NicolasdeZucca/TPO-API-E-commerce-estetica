package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.model.Producto;
import com.e_commerce.estetica.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> traerTodos() {
        return ResponseEntity.ok(productoService.traerProductos());
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto prod) {
        return new ResponseEntity<>(productoService.crearProducto(prod), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto prod) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, prod));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}