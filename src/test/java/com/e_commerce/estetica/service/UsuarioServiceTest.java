package com.e_commerce.estetica.service;

import com.e_commerce.estetica.dto.UsuarioResponse;
import com.e_commerce.estetica.model.Role;
import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void crearUsuarioDebeRetornarUsuarioResponse() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");
        usuario.setApellido("García");
        usuario.setEmail("ana@test.com");
        usuario.setPassword("123456");
        usuario.setRol(Role.ROLE_CLIENTE);

        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario guardado = invocation.getArgument(0);
            guardado.setId(1L);
            return guardado;
        });

        UsuarioResponse response = usuarioService.crearUsuario(usuario);

        assertNotNull(response);
        assertEquals("ana@test.com", response.getEmail());
        assertEquals("Ana", response.getNombre());
    }
}
