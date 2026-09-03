package com.e_commerce.estetica.service;

import com.e_commerce.estetica.model.Producto;
import com.e_commerce.estetica.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Aquí usamos inyección por constructor 
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> traerProductos() { return productoRepository.findAll(); }
    
    public Producto crearProducto(Producto producto) { return productoRepository.save(producto); }
    
    public Producto buscarPorId(Long id) { return productoRepository.findById(id).orElse(null); }
    
    public Producto actualizarProducto(Long id, Producto prodAct) {
        Producto prodDb = buscarPorId(id);
        if (prodDb != null) {
            prodDb.setNombre(prodAct.getNombre());
            prodDb.setMarca(prodAct.getMarca());
            prodDb.setPrecio(prodAct.getPrecio());
            return productoRepository.save(prodDb);
        }
        return null;
    }
    
    public void eliminarProducto(Long id) { productoRepository.deleteById(id); }
}