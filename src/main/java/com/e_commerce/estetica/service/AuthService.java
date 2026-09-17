package com.e_commerce.estetica.service;

import com.e_commerce.estetica.config.JwtUtils;
import com.e_commerce.estetica.dto.AuthResponse;
import com.e_commerce.estetica.dto.LoginRequest;
import com.e_commerce.estetica.dto.RegisterRequest;
import com.e_commerce.estetica.exception.DuplicateResourceException;
import com.e_commerce.estetica.model.Role;
import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    // REGISTRO de un nuevo usuario con lógica de negocio desacoplada
    public AuthResponse register(RegisterRequest request) {

        // Si el email ya existe, lanza 409 Conflict (lo intercepta GlobalExceptionHandler)
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Ya existe un usuario registrado con el email: " + request.getEmail());
        }

        // Crear la entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword())); // Encriptación BCrypt

        // Todo nuevo usuario registrado es cliente por regla de negocio
        usuario.setRol(Role.ROLE_CLIENTE);

        // Guardar en base de datos a través del repositorio
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Generar el token JWT para el usuario registrado
        String token = jwtUtils.generateToken(usuarioGuardado.getEmail(), usuarioGuardado.getRol().name());

        // Devolver el DTO de respuesta
        return new AuthResponse(token, usuarioGuardado.getEmail(), usuarioGuardado.getRol().name());
    }

    // LOGIN de un usuario existente
    public AuthResponse login(LoginRequest request) {

        // Autenticación con Spring Security (si falla, propaga AuthenticationException -> 401 Unauthorized)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Obtener el usuario autenticado
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Generar el token JWT
        String token = jwtUtils.generateToken(usuario.getEmail(), usuario.getRol().name());

        // Devolver el DTO de respuesta
        return new AuthResponse(token, usuario.getEmail(), usuario.getRol().name());
    }
}
