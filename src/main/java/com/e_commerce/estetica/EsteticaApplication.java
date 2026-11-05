package com.e_commerce.estetica;

import com.e_commerce.estetica.model.Role;
import com.e_commerce.estetica.model.Usuario;
import com.e_commerce.estetica.model.Producto;
import com.e_commerce.estetica.model.ProductoImagen;
import com.e_commerce.estetica.model.Categoria;
import com.e_commerce.estetica.repository.ProductoRepository;
import com.e_commerce.estetica.repository.CategoriaRepository;
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

import java.util.ArrayList;
import java.util.List;

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
	CommandLineRunner cargarDatosPorDefecto(CategoriaService categoriaService,
											CategoriaRepository categoriaRepository,
											ProductoRepository productoRepository) {
		return args -> {
			// 1. Crear Categorías iniciales
			categoriaService.crearSiNoExiste("Skincare");
			categoriaService.crearSiNoExiste("Maquillaje");

			// 2. Crear Productos iniciales si la tabla está vacía
			if (productoRepository.count() == 0) {
				Categoria skincare = categoriaRepository.findAllByOrderByIdAsc().stream()
						.filter(c -> c.getNombre().equalsIgnoreCase("Skincare"))
						.findFirst().orElse(null);

				Categoria maquillaje = categoriaRepository.findAllByOrderByIdAsc().stream()
						.filter(c -> c.getNombre().equalsIgnoreCase("Maquillaje"))
						.findFirst().orElse(null);

				if (skincare != null) {
					Producto p1 = new Producto(null, "Crema Hidratante Hydra Cashmere", "Hidratación que se siente, luminosidad que se ve. Crema ligera con ácido hialurónico y aloe vera.", "ALMA", 22.40, 25, skincare, new ArrayList<>(), new ArrayList<>());
					p1.getImagenes().add(new ProductoImagen(null, "/images/firming-cashmere.jpg", p1));
					productoRepository.save(p1);

					Producto p2 = new Producto(null, "Sérum Iluminador Bouncy Cactus", "Concentrado antioxidante con extracto puro de cactus para elasticidad y brillo inmediato.", "ALMA", 28.00, 18, skincare, new ArrayList<>(), new ArrayList<>());
					p2.getImagenes().add(new ProductoImagen(null, "/images/producto-3.jpg", p2));
					productoRepository.save(p2);

					Producto p3 = new Producto(null, "Crema Hidratante Botánica Custom", "Fórmula botánica con extractos vegetales activos que restauran el manto hidrolipídico.", "ALMA", 29.40, 15, skincare, new ArrayList<>(), new ArrayList<>());
					p3.getImagenes().add(new ProductoImagen(null, "/images/producto-2.jpg", p3));
					productoRepository.save(p3);
				}

				if (maquillaje != null) {
					Producto p4 = new Producto(null, "Bálsamo Labial con Péptidos Ribbon Tint", "Tratamiento restaurador para labios enriquecido con manteca de karité y péptidos activos.", "ALMA", 16.80, 40, maquillaje, new ArrayList<>(), new ArrayList<>());
					p4.getImagenes().add(new ProductoImagen(null, "/images/rhode-2.jpg", p4));
					productoRepository.save(p4);

					Producto p5 = new Producto(null, "Bálsamo Labial Péptidos Matcha Care", "Tratamiento nutritivo con extracto botánico de té verde matcha y acabado sedoso.", "ALMA", 16.80, 30, maquillaje, new ArrayList<>(), new ArrayList<>());
					p5.getImagenes().add(new ProductoImagen(null, "/images/rhode-1.jpg", p5));
					productoRepository.save(p5);

					Producto p6 = new Producto(null, "Lip Tint & Cheek Glow Look", "Tinte multiuso para labios y mejillas, acabado luminoso y cobertura modulable.", "ALMA", 19.60, 22, maquillaje, new ArrayList<>(), new ArrayList<>());
					p6.getImagenes().add(new ProductoImagen(null, "/images/producto-1.jpg", p6));
					productoRepository.save(p6);
				}
			}
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
