package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.config.JwtUtils;
import com.e_commerce.estetica.dto.AuthResponse;
import com.e_commerce.estetica.dto.LoginRequest;
import com.e_commerce.estetica.dto.RegisterRequest;
import com.e_commerce.estetica.model.Role;
import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils,
                          AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    // REGISTRO de un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {

        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
        }

        // Crear el usuario nuevo
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword())); // Encripta con BCrypt
        usuario.setRol(Role.ROLE_CLIENTE); // Por defecto es cliente

        usuarioRepository.save(usuario);

        // Generar el token JWT
        String token = jwtUtils.generateToken(usuario.getEmail(), usuario.getRol().name());

        return new ResponseEntity<>(new AuthResponse(token, usuario.getEmail(), usuario.getRol().name()), HttpStatus.CREATED);
    }

    // LOGIN de un usuario existente
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        // Autenticar con Spring Security (valida email + password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Obtener el usuario autenticado
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Generar el token JWT
        String token = jwtUtils.generateToken(usuario.getEmail(), usuario.getRol().name());

        return new ResponseEntity<>(new AuthResponse(token, usuario.getEmail(), usuario.getRol().name()), HttpStatus.OK);
    }
}
