package com.e_commerce.estetica.dto;

import com.e_commerce.estetica.model.Usuario;

public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.email = usuario.getEmail();
        this.rol = usuario.getRol().name();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
}