package com.e_commerce.estetica.service;

import com.e_commerce.estetica.dto.UsuarioResponse;
import com.e_commerce.estetica.exception.BadRequestException;
import com.e_commerce.estetica.exception.DuplicateResourceException;
import com.e_commerce.estetica.exception.ResourceNotFoundException;
import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponse> traerUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        return new UsuarioResponse(buscarEntidadPorId(id));
    }

    public Usuario buscarEntidadPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }

    public UsuarioResponse crearUsuario(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new BadRequestException("El email es obligatorio");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new DuplicateResourceException(
                    "Ya existe un usuario registrado con el email: " + usuario.getEmail());
        }
        return new UsuarioResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponse actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioDb = buscarEntidadPorId(id);

        if (usuario.getEmail() != null && !usuario.getEmail().equals(usuarioDb.getEmail())) {
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new DuplicateResourceException(
                        "Ya existe un usuario registrado con el email: " + usuario.getEmail());
            }
            usuarioDb.setEmail(usuario.getEmail());
        }

        usuarioDb.setNombre(usuario.getNombre());
        return new UsuarioResponse(usuarioRepository.save(usuarioDb));
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", id);
        }
        usuarioRepository.deleteById(id);
    }
}