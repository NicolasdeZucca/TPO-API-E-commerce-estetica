package com.e_commerce.estetica.service;

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

    public List<Usuario> traerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new BadRequestException("El email es obligatorio");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new DuplicateResourceException(
                    "Ya existe un usuario registrado con el email: " + usuario.getEmail());
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioDb = buscarPorId(id);

        // Si cambia el email, verificar que no lo tenga otro usuario
        if (usuario.getEmail() != null && !usuario.getEmail().equals(usuarioDb.getEmail())) {
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new DuplicateResourceException(
                        "Ya existe un usuario registrado con el email: " + usuario.getEmail());
            }
            usuarioDb.setEmail(usuario.getEmail());
        }

        usuarioDb.setNombre(usuario.getNombre());

        return usuarioRepository.save(usuarioDb);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", id);
        }
        usuarioRepository.deleteById(id);
    }
}