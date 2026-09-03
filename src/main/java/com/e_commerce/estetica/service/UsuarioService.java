package com.e_commerce.estetica.service;

import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.repository.UsuarioRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //agrego metodos del usuario 
    
    public List<Usuario> traerUsuarios() {
        return usuarioRepository.findAll();
    }
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

}