package com.e_commerce.estetica.service;

import com.e_commerce.estetica.dto.PedidoItemResponse;
import com.e_commerce.estetica.dto.PedidoResponse;
import com.e_commerce.estetica.exception.BadRequestException;
import com.e_commerce.estetica.exception.ResourceNotFoundException;
import com.e_commerce.estetica.model.*;
import com.e_commerce.estetica.repository.CarritoRepository;
import com.e_commerce.estetica.repository.PedidoRepository;
import com.e_commerce.estetica.repository.ProductoRepository;
import com.e_commerce.estetica.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            CarritoRepository carritoRepository,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.carritoRepository = carritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    public PedidoResponse crearPedido(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", email));
        Carrito carrito = carritoRepository.findByUsuario_Email(email)
                .orElseThrow(() -> new BadRequestException("El carrito está vacío"));
        if (carrito.getItems().isEmpty()) {
            throw new BadRequestException("El carrito está vacío");
        }

        Map<Long, Integer> cantidadesSolicitadas = carrito.getItems().stream().collect(Collectors.groupingBy(
                item -> item.getProducto().getId(),
                Collectors.summingInt(CarritoItem::getCantidad)
        ));
        cantidadesSolicitadas.forEach((productoId, cantidad) -> {
            Producto producto = carrito.getItems().stream()
                    .map(CarritoItem::getProducto)
                    .filter(item -> item.getId().equals(productoId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", productoId));
            if (cantidad > producto.getStock()) {
                throw new BadRequestException("No hay stock suficiente para " + producto.getNombre());
            }
        });

        double subtotal = 0;
        for (CarritoItem item : carrito.getItems()) {
            Producto producto = item.getProducto();
            subtotal += producto.getPrecio() * item.getCantidad();
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(Math.round((subtotal + (subtotal >= 50 ? 0 : 4.99)) * 100.0) / 100.0);

        for (CarritoItem item : carrito.getItems()) {
            Producto producto = item.getProducto();
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            PedidoItem pedidoItem = new PedidoItem();
            pedidoItem.setNombreProducto(producto.getNombre());
            pedidoItem.setCantidad(item.getCantidad());
            pedidoItem.setPrecioUnitario(producto.getPrecio());
            pedido.agregarItem(pedidoItem);
        }

        Pedido guardado = pedidoRepository.save(pedido);
        carrito.vaciar();
        return aRespuesta(guardado);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> obtenerPedidos(String email) {
        return pedidoRepository.findAllByUsuario_EmailOrderByFechaDesc(email).stream()
                .map(this::aRespuesta)
                .toList();
    }

    private PedidoResponse aRespuesta(Pedido pedido) {
        List<PedidoItemResponse> items = pedido.getItems().stream()
                .map(item -> new PedidoItemResponse(
                        item.getNombreProducto(), item.getCantidad(), item.getPrecioUnitario()
                ))
                .toList();
        return new PedidoResponse(pedido.getId(), pedido.getFecha(), pedido.getEstado(), pedido.getTotal(), items);
    }
}
