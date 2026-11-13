package com.e_commerce.estetica.controller;

import com.e_commerce.estetica.service.ImagenService;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    /**
     * Sube una imagen (multipart/form-data, campo "archivo") y devuelve la URL
     * con la que se puede guardar en el producto: { "url": "/api/imagenes/xxx.jpg" }
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> subir(@RequestParam("archivo") MultipartFile archivo) {
        String nombre = imagenService.guardar(archivo);
        return new ResponseEntity<>(Map.of("url", "/api/imagenes/" + nombre), HttpStatus.CREATED);
    }

    @GetMapping("/{nombre:.+}")
    public ResponseEntity<Resource> obtener(@PathVariable String nombre) {
        Resource imagen = imagenService.cargar(nombre);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imagenService.tipoDeContenido(nombre)))
                .cacheControl(CacheControl.maxAge(7, TimeUnit.DAYS))
                .body(imagen);
    }
}
