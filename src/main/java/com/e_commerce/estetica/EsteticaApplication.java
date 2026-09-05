package com.e_commerce.estetica;

import com.e_commerce.estetica.service.CategoriaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EsteticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsteticaApplication.class, args);
	}

	@Bean
	CommandLineRunner cargarCategoriasPorDefecto(CategoriaService categoriaService) {
		return args -> {
			categoriaService.crearSiNoExiste("Skincare");
			categoriaService.crearSiNoExiste("Maquillaje");
		};
	}

}
