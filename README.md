# 🛍️ E-Commerce REST API — Backend para Tienda de Estética

API RESTful desarrollada en **Java con Spring Boot** para la gestión backend de una plataforma e-commerce B2C especializada en productos de estética y cuidado personal. Diseñada bajo arquitectura por capas (Controller-Service-Repository), con persistencia de datos relacional y autenticación segura basada en tokens.

## 🌟 Características Principales

* **Seguridad & Autenticación (JWT):** Implementación de Spring Security y JSON Web Tokens (JWT) con gestión de roles (`Role`, `AuthController`, `CustomUserDetailsService`).
* **Gestión de Catálogo & Productos:** Endpoints para gestión de `Producto`, categorías (`Categoria`) y ofertas vigentes (`Oferta`).
* **Arquitectura de Dominio (DTO & Services):**
  * Implementación de DTOs para desacoplar el modelo de dominio de los contratos de entrada/salida de la API.
  * Control de excepciones personalizado (`exception` package) y respuestas estandarizadas.
* **Containerización & Persistencia:** Configuración de MySQL y la aplicación completa mediante contenedores **Docker** (`docker-compose`).

## 🛠️ Stack

* **Lenguaje & Framework:** Java, Spring Boot, Spring Web, Spring Security.
* **ORM & Persistencia:** Spring Data JPA, Hibernate, MySQL Database.
* **Herramientas & Despliegue:** Lombok, JWT (JSON Web Tokens), Docker, Postman.

## 📂 Estructura del Proyecto

```text
src/main/java/
└── com/estetica/
    ├── controller/      # Auth, Categoria, Oferta, Producto, Usuario Controllers
    ├── dto/             # Objetos de Transferencia de Datos
    ├── exception/       # Manejo global de excepciones
    ├── model/           # Entidades JPA (Usuario, Categoria, Producto, Carrito, etc.)
    ├── repository/      # Interfaces de acceso a datos (Spring Data JPA)
    └── service/         # Lógica de negocio y Auth Services
```
