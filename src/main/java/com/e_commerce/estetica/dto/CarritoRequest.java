package com.e_commerce.estetica.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CarritoRequest(@NotNull List<@NotNull @Valid CarritoItemRequest> items) {
}
