package com.e_commerce.estetica.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        LocalDateTime fecha,
        String estado,
        Double total,
        List<PedidoItemResponse> items
) {
}
