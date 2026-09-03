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

    // Inyección por constructor
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> traerTodos() { 
        return new ResponseEntity<>(productoService.traerProductos(), HttpStatus.OK); 
    }
    
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto prod) { 
        return new ResponseEntity<>(productoService.crearProducto(prod), HttpStatus.CREATED); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        Producto p = productoService.buscarPorId(id);
        return p != null ? new ResponseEntity<>(p, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto prod) {
        Producto p = productoService.actualizarProducto(id, prod);
        return p != null ? new ResponseEntity<>(p, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}