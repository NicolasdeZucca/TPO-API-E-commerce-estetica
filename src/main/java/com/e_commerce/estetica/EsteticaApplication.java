package com.e_commerce.estetica;

import com.e_commerce.estetica.model.Role;
import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.service.CategoriaService;
import com.e_commerce.estetica.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@AllArgsConstructor
public class EsteticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsteticaApplication.class, args);
	}
	private final PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	void mostrarMensajeDeInicio() {
		System.out.println("API de Estetica escuchando en http://localhost:8080");
	}

	@Bean
	CommandLineRunner cargarCategoriasPorDefecto(CategoriaService categoriaService) {
		return args -> {
			categoriaService.crearSiNoExiste("Skincare");
			categoriaService.crearSiNoExiste("Maquillaje");
		};
	}

	@Bean
	CommandLineRunner adminUserPorDefecto(UsuarioService usuarioService){
		return args -> {
			Usuario u = new Usuario();
			u.setNombre("Admin");
			u.setApellido("Admin");
			u.setEmail("admin@admin.com");
			u.setPassword(passwordEncoder.encode("admin123"));
			u.setRol(Role.ROLE_ADMIN);
			usuarioService.crearUsuarioSiNoExiste(u);
		};
	}

}
