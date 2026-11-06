package com.e_commerce.estetica.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FavoritosRequest(@NotNull List<@NotNull Long> productoIds) {
}
