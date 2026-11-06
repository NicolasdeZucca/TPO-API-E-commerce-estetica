package com.e_commerce.estetica.service;

import com.e_commerce.estetica.dto.CarritoItemRequest;
import com.e_commerce.estetica.dto.CarritoItemResponse;
import com.e_commerce.estetica.dto.FavoritosRequest;
import com.e_commerce.estetica.exception.BadRequestException;
import com.e_commerce.estetica.exception.ResourceNotFoundException;
import com.e_commerce.estetica.model.*;
import com.e_commerce.estetica.repository.CarritoRepository;
import com.e_commerce.estetica.repository.ProductoRepository;
import com.e_commerce.estetica.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CuentaService {
    private final UsuarioRepository usuarioRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    public CuentaService(
            UsuarioRepository usuarioRepository,
            CarritoRepository carritoRepository,
            ProductoRepository productoRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
    }

    public Usuario obtenerUsuario(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", email));
    }

    public List<CarritoItemResponse> obtenerCarrito(String email) {
        return carritoRepository.findByUsuario_Email(email)
                .map(carrito -> carrito.getItems().stream().map(this::aRespuesta).toList())
                .orElseGet(List::of);
    }

    public List<CarritoItemResponse> reemplazarCarrito(String email, List<CarritoItemRequest> items) {
        Usuario usuario = obtenerUsuario(email);
        Carrito carrito = carritoRepository.findByUsuario_Email(email).orElseGet(() -> {
            Carrito nuevo = new Carrito();
            nuevo.setUsuario(usuario);
            return nuevo;
        });

        Map<Long, Integer> cantidadesSolicitadas = items.stream().collect(Collectors.groupingBy(
                CarritoItemRequest::productoId,
                Collectors.summingInt(CarritoItemRequest::cantidad)
        ));
        cantidadesSolicitadas.forEach((productoId, cantidad) -> {
            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", productoId));
            if (cantidad > producto.getStock()) {
                throw new BadRequestException("No hay stock suficiente para " + producto.getNombre());
            }
        });

        carrito.getItems().clear();
        for (CarritoItemRequest item : items) {
            if (item.cantidad() < 1) {
                throw new BadRequestException("La cantidad de cada producto debe ser al menos 1");
            }
            Producto producto = productoRepository.findById(item.productoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", item.productoId()));
            CarritoItem carritoItem = new CarritoItem();
            carritoItem.setCantidad(item.cantidad());
            carritoItem.setProducto(producto);
            carritoItem.setFormato(item.formato());
            carrito.agregarItem(carritoItem);
        }

        return carritoRepository.save(carrito).getItems().stream().map(this::aRespuesta).toList();
    }

    public void vaciarCarrito(String email) {
        carritoRepository.findByUsuario_Email(email).ifPresent(Carrito::vaciar);
    }

    public List<Long> obtenerFavoritos(String email) {
        return obtenerUsuario(email).getFavoritos().stream()
                .map(Producto::getId)
                .sorted()
                .toList();
    }

    public List<Long> reemplazarFavoritos(String email, FavoritosRequest request) {
        Usuario usuario = obtenerUsuario(email);
        Set<Long> ids = new HashSet<>(request.productoIds());
        List<Producto> productos = productoRepository.findAllById(ids);
        if (productos.size() != ids.size()) {
            Set<Long> encontrados = productos.stream().map(Producto::getId).collect(Collectors.toSet());
            Long faltante = ids.stream().filter(id -> !encontrados.contains(id)).findFirst().orElse(null);
            throw new ResourceNotFoundException("Producto", faltante);
        }
        usuario.getFavoritos().clear();
        usuario.getFavoritos().addAll(productos);
        usuarioRepository.save(usuario);
        return usuario.getFavoritos().stream().map(Producto::getId).sorted().toList();
    }

    private CarritoItemResponse aRespuesta(CarritoItem item) {
        Producto producto = item.getProducto();
        String categoria = producto.getCategoria() == null ? "" : producto.getCategoria().getNombre();
        String imagen = producto.getImagenes() == null ? null : producto.getImagenes().stream()
                .sorted(Comparator.comparing(ProductoImagen::getId))
                .map(ProductoImagen::getUrlImagen)
                .findFirst()
                .orElse(null);
        return new CarritoItemResponse(
                producto.getId(), producto.getNombre(), categoria, producto.getPrecio(),
                item.getCantidad(), item.getFormato(), imagen
        );
    }
}
