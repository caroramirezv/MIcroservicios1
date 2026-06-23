# Tienda de Ropa — Arquitectura de Microservicios

## 📋 Descripción del contexto / dominio del proyecto

Este proyecto implementa el backend de una **tienda de ropa online** mediante una arquitectura de **microservicios independientes** construidos con Spring Boot. Cada microservicio gestiona una entidad o proceso del negocio (usuarios, catálogo de ropa, categorías, carrito, pedidos, pagos, inventario, envíos, búsqueda y notificaciones), con su propia base de datos MySQL y comunicación entre servicios mediante **Feign Client**. Todos los microservicios se registran en un servidor **Eureka** para descubrimiento de servicios.

El sistema cubre el flujo completo de una tienda: un usuario se registra, navega el catálogo de ropa organizado por categorías, realiza búsquedas filtradas, agrega productos a su carrito, genera un pedido, este se paga, se controla el stock en inventario, se gestiona el envío y se notifica al usuario en cada etapa relevante.

---

##Integrantes

| Nombre |
|---|
| Mhyjal Rozas |
| Carolina Ramirez |

---

## 🧩 Microservicios Implementados

| # | Microservicio | Puerto | Base de datos |
|---|---|---|---|
| 1 | **ms-usuario** | 8081 | db_usuario |
| 2 | **ms-ropa** | 8082 | db_ropa |
| 3 | **ms-pedido** | 8083 | db_pedidos |
| 4 | **ms-pagos** | 8084 | db_pagos |
| 5 | **ms-notificaciones** | 8085 | db_notificaciones |
| 6 | **ms-categoria** | 8086 | db_categoria |
| 7 | **ms-carrito** | 8087 | db_carrito |
| 8 | **ms-busqueda** | 8088 | db_busqueda |
| 9 | **ms-inventario** | 8089 | db_inventario |
| 10 | **ms-envio** | 8090 | db_envio |

Adicionalmente, el proyecto incluye un servidor **Eureka** (puerto `8182`) donde los 10 microservicios se registran como clientes de descubrimiento.

### Resumen de funcionalidades por microservicio

- **ms-usuario**: CRUD de usuarios, búsqueda por email, consulta de usuario con su pedido asociado (Feign → ms-pedido).
- **ms-ropa**: CRUD del catálogo de prendas, búsqueda por nombre. Incluye carga de datos de prueba con **DataFaker**.
- **ms-pedido**: CRUD de pedidos, cambio de estado, filtro por usuario y por estado, consulta de pedido con datos del cliente (Feign → ms-usuario).
- **ms-pagos**: CRUD de pagos, filtro por estado y método de pago, consulta de pago con datos del pedido (Feign → ms-pedido).
- **ms-notificaciones**: CRUD de notificaciones, búsqueda por nombre, consulta con datos del usuario (Feign → ms-usuario).
- **ms-categoria**: CRUD de categorías de ropa.
- **ms-carrito**: CRUD de carritos, listado por usuario, consulta con detalle de prendas y usuario (Feign → ms-ropa, ms-usuario).
- **ms-busqueda**: CRUD de búsquedas, filtro por categoría, consulta con detalle de resultados (Feign → ms-ropa).
- **ms-inventario**: CRUD de inventario, actualización de cantidad, verificación de stock por producto (Feign → ms-ropa).
- **ms-envio**: CRUD de envíos, filtro por estado y por pedido, consulta con datos del pedido (Feign → ms-pedido).

---

## 🌐 Rutas principales del Gateway

> ⚠️ **Estado actual: el módulo de API Gateway aún no está implementado en este proyecto.** Por ahora, cada microservicio se consume directamente en su propio puerto (ver tabla de la sección anterior). Cuando se incorpore un Spring Cloud Gateway, esta sección se actualizará con el prefijo de cada ruta enrutada (por ejemplo `http://localhost:8080/usuario/**` → `ms-usuario`, `http://localhost:8080/ropa/**` → `ms-ropa`, etc.).

Mientras tanto, las rutas base de cada microservicio son:

| Microservicio | Ruta base |
|---|---|
| ms-usuario | `http://localhost:8081/api/v1/usuario` |
| ms-ropa | `http://localhost:8082/api/v1/ropa` |
| ms-pedido | `http://localhost:8083/api/v1/pedidos` |
| ms-pagos | `http://localhost:8084/api/v1/pagos` |
| ms-notificaciones | `http://localhost:8085/api/v1/notificaciones` |
| ms-categoria | `http://localhost:8086/api/v1/categoria` |
| ms-carrito | `http://localhost:8087/api/v1/carrito` |
| ms-busqueda | `http://localhost:8088/api/v1/busqueda` |
| ms-inventario | `http://localhost:8089/api/v1/inventario` |
| ms-envio | `http://localhost:8090/api/v1/envio` |

---

## 📖 Documentación Swagger

Cada microservicio expone su propia documentación interactiva vía **Swagger / OpenAPI** en la siguiente ruta, una vez que el servicio está corriendo localmente:

| Microservicio | Swagger UI (local) |
|---|---|
| ms-usuario | http://localhost:8081/doc/swagger-ui.html |
| ms-ropa | http://localhost:8082/doc/swagger-ui.html |
| ms-pedido | http://localhost:8083/doc/swagger-ui.html |
| ms-pagos | http://localhost:8084/doc/swagger-ui.html |
| ms-notificaciones | http://localhost:8085/doc/swagger-ui.html |
| ms-categoria | http://localhost:8086/doc/swagger-ui.html |
| ms-carrito | http://localhost:8087/doc/swagger-ui.html |
| ms-busqueda | http://localhost:8088/doc/swagger-ui.html |
| ms-inventario | http://localhost:8089/doc/swagger-ui.html |
| ms-envio | http://localhost:8090/doc/swagger-ui.html |

> 🌍 **Documentación remota**: actualmente el proyecto no está desplegado en un entorno remoto (Railway, Render, etc.). Esta sección se completará con los enlaces correspondientes una vez realizado el despliegue.

---

## 🚀 Instrucciones de ejecución

### Ejecución local

**Requisitos previos:**
- Java 21
- Laragon con MySQL activo en el puerto `3306`
- Maven (o usar el wrapper `./mvnw` incluido en cada microservicio)
- IntelliJ IDEA o Visual Studio Code
- Postman (opcional, para probar los endpoints)

**Pasos:**

1. **Iniciar Laragon** y verificar que MySQL esté activo en el puerto `3306`.
2. **Verificar credenciales** en cada `application.properties` (usuario `root`, contraseña según tu instalación de Laragon).
3. Las bases de datos se crean automáticamente gracias a `createDatabaseIfNotExist=true` en la URL de conexión.
4. **Levantar primero el servidor Eureka**, ya que todos los microservicios dependen de él para registrarse:
   ```
   cd eureka/eureka
   ./mvnw spring-boot:run
   ```
   Verificar en `http://localhost:8182` que el panel de Eureka esté disponible.

5. **Levantar los microservicios en este orden** (los que dependen de otros vía Feign deben arrancar después):
   ```
   1°  ms-usuario        (puerto 8081)
   2°  ms-ropa            (puerto 8082)
   3°  ms-pedido          (puerto 8083)
   4°  ms-pagos           (puerto 8084)
   5°  ms-notificaciones  (puerto 8085)
   6°  ms-categoria       (puerto 8086)
   7°  ms-carrito         (puerto 8087)
   8°  ms-busqueda        (puerto 8088)
   9°  ms-inventario      (puerto 8089)
   10° ms-envio           (puerto 8090)
   ```
   Desde la carpeta de cada microservicio:
   ```
   ./mvnw spring-boot:run
   ```
   O bien, abrir cada carpeta como proyecto en IntelliJ IDEA y ejecutar la clase `*Application.java`.

6. **Verificar el registro en Eureka**: en `http://localhost:8182` deberían aparecer los 10 microservicios como instancias activas (`UP`).

7. **Probar los endpoints** desde Postman o el navegador usando las rutas base indicadas más arriba, o explorar cada API desde su Swagger UI.

### Ejecución remota

> 🌍 Actualmente el proyecto no cuenta con un despliegue remoto activo (Docker, Railway, Render u otra plataforma). Esta sección se completará con las instrucciones de despliegue y las URLs públicas una vez configurado el entorno remoto.

---

## 🛠️ Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.15**
- **Spring Cloud 2025.0.3** (Eureka Client/Server, OpenFeign)
- **Spring Data JPA + Hibernate**
- **MySQL**
- **Springdoc OpenAPI** (Swagger UI)
- **DataFaker** (datos de prueba en ms-ropa)
- **JUnit 5 + Mockito** (pruebas unitarias)
- **Lombok**
- **Maven**
└── ms-envio/
