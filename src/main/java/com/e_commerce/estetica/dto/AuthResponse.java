package com.e_commerce.estetica.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    @JsonIgnore
    private String token;

    private String email;
    private String rol;
}
