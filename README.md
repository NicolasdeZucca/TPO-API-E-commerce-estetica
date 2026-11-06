# 🛍️ E-Commerce REST API — Backend para Tienda de Estética

API RESTful desarrollada en **Java con Spring Boot** para la gestión backend de una plataforma e-commerce B2C especializada en productos de estética y cuidado personal. Diseñada bajo arquitectura por capas (Controller-Service-Repository), con persistencia de datos relacional y autenticación segura basada en tokens.

## 🌟 Características Principales

* **Seguridad & Autenticación (JWT):** Implementación de Spring Security y JSON Web Tokens (JWT) con gestión de roles (`Role`, `AuthController`, `CustomUserDetailsService`).
* **Gestión de Catálogo & Productos:** Endpoints para gestión de `Producto`, categorías (`Categoria`) y ofertas vigentes (`Oferta`).
* **Cuenta & Compras:** Carrito y favoritos persistidos por usuario autenticado, consulta de perfil y creación/historial de pedidos con validación de stock.
* **Arquitectura de Dominio (DTO & Services):**
  * Implementación de DTOs para desacoplar el modelo de dominio de los contratos de entrada/salida de la API.
  * Control de excepciones personalizado (`exception` package) y respuestas estandarizadas.
* **Containerización & Persistencia:** Configuración de MySQL y la aplicación completa mediante contenedores **Docker** (`docker-compose`).

## 🛠️ Stack

* **Lenguaje & Framework:** Java, Spring Boot, Spring Web, Spring Security.
* **ORM & Persistencia:** Spring Data JPA, Hibernate, MySQL Database.
* **Herramientas & Despliegue:** Lombok, JWT (JSON Web Tokens), Docker, Postman.

## Endpoints de cuenta (JWT requerido)

| Método | Endpoint | Uso |
|---|---|---|
| `GET` | `/api/auth/me` | Datos del usuario autenticado |
| `GET`, `PUT`, `DELETE` | `/api/carrito` | Consultar, reemplazar o vaciar carrito |
| `GET`, `PUT` | `/api/favoritos` | Consultar o reemplazar favoritos |
| `GET`, `POST` | `/api/pedidos` | Historial y confirmación de compra desde el carrito |

El frontend envía el token con `Authorization: Bearer <token>`. Al confirmar un pedido, el backend comprueba stock, calcula el costo de envío, persiste el pedido, descuenta las unidades vendidas y vacía el carrito en una transacción.

La configuración local predeterminada usa MySQL en Docker, en `localhost:3307` y la base `ecommerce_db3`. Al ejecutar la aplicación dentro de Docker Compose, `SPRING_DATASOURCE_URL` conecta al servicio `base-datos` por el puerto interno `3306`. La URL, el usuario y la contraseña pueden sobrescribirse mediante variables `SPRING_DATASOURCE_*`.

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
