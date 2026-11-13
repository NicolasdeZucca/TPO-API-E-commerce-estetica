package com.e_commerce.estetica.service;

import com.e_commerce.estetica.exception.BadRequestException;
import com.e_commerce.estetica.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class ImagenService {

    // Tipos de imagen aceptados y la extensión con la que se guardan
    private static final Map<String, String> TIPOS_PERMITIDOS = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp",
            "image/gif", ".gif"
    );

    private final Path directorio;

    public ImagenService(@Value("${app.uploads.dir:uploads}") String directorio) {
        this.directorio = Paths.get(directorio).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.directorio);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear la carpeta de imágenes: " + this.directorio, e);
        }
    }

    /**
     * Guarda la imagen en disco con un nombre único y devuelve ese nombre.
     */
    public String guardar(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new BadRequestException("No se envió ninguna imagen");
        }

        String extension = TIPOS_PERMITIDOS.get(archivo.getContentType());
        if (extension == null) {
            throw new BadRequestException("Formato de imagen no permitido. Usá JPG, PNG, WEBP o GIF");
        }

        String nombre = UUID.randomUUID() + extension;
        try (InputStream in = archivo.getInputStream()) {
            Files.copy(in, directorio.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo guardar la imagen", e);
        }
        return nombre;
    }

    /**
     * Devuelve la imagen guardada como recurso para servirla por HTTP.
     */
    public Resource cargar(String nombre) {
        Path archivo = directorio.resolve(nombre).normalize();
        // Evita que se pidan archivos fuera de la carpeta de uploads (ej: ../../etc/passwd)
        if (!archivo.startsWith(directorio) || !Files.isRegularFile(archivo)) {
            throw new ResourceNotFoundException("Imagen " + nombre + " no encontrada");
        }
        try {
            return new UrlResource(archivo.toUri());
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("Imagen " + nombre + " no encontrada");
        }
    }

    public String tipoDeContenido(String nombre) {
        String lower = nombre.toLowerCase();
        return TIPOS_PERMITIDOS.entrySet().stream()
                .filter(e -> lower.endsWith(e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("application/octet-stream");
    }
}
