Tienda de Ropa — Arquitectura de Microservicios
Asignatura: DSY1103 Desarrollo FullStack 1
Evaluación: Parcial 2 
Integrantes:
Nombre


Mhyjal Rozas


Carolina Ramirez



Descripción del Proyecto:
Sistema de backend distribuido para una tienda de ropa online, construido bajo una arquitectura de microservicios independientes con Spring Boot. Cada microservicio tiene su propia base de datos, sus propias reglas de negocio y se comunica con otros servicios a través de Feign Client.
El sistema cubre el flujo completo de una tienda: gestión de usuarios, catálogo de ropa por categorías, búsqueda y filtrado de productos, carrito de compras, procesamiento de pedidos y pagos, control de inventario, logística de envíos y notificaciones.
Microservicios Implementados:
Microservicio
Puerto
  Base de datos
Descripción
ms-usuario
8081
db_usuario
Gestión de clientes registrados
ms-ropa
8082
db_ropa
Catálogo de prendas con filtros por nombre y precio
ms-pedido
8083
db_pedidos
Creación y seguimiento de pedidos
ms-pagos
8084
db_pagos
Procesamiento y registro de pagos
ms-notificaciones
8085
db_notificaciones
Envío de notificaciones a usuarios
ms-categoria
8086
db_categoria
Categorización de prendas
ms-carrito
8087
db_carrito
Carrito de compras por usuario
ms-busqueda
8088
db_busqueda
Búsqueda y filtrado de productos
ms-inventario
8089
db_inventario
Control de stock por producto
ms-envio
8090
db_envio
Gestión de envíos y seguimiento

Funcionalidades Implementadas:
Gestión de Usuarios (ms-usuario — puerto 8081)
Crear, listar, actualizar y eliminar usuarios
Consulta de usuario con datos de su último pedido (via Feign → ms-pedido)
Búsqueda de usuario por email
Catálogo de Ropa (ms-ropa — puerto 8082)
CRUD completo de prendas de vestir
Filtrado por nombre (búsqueda parcial)
Filtrado por rango de precio
Pedidos (ms-pedido — puerto 8083)
Creación y gestión de pedidos por usuario
Cambio de estado del pedido (PATCH)
Consulta de pedido con datos del cliente (via Feign → ms-usuario)
Filtrado de pedidos por usuario y por estado
Pagos (ms-pagos — puerto 8084)
Registro de pagos asociados a pedidos
Consulta de pago con datos del pedido (via Feign → ms-pedido)
Filtrado por estado de pago y método de pago
CRUD completo incluyendo actualización y eliminación
Notificaciones (ms-notificaciones — puerto 8085)
Registro de notificaciones asociadas a usuarios
Consulta de notificación con datos del usuario (via Feign → ms-usuario)
Búsqueda por nombre/tipo de notificación
CRUD completo
Categorías (ms-categoria — puerto 8086)
CRUD completo de categorías de ropa
Consulta de categoría con datos de producto relacionado (via Feign → ms-ropa)
Carrito de Compras (ms-carrito — puerto 8087)
Gestión de carritos con lista de productos y total
Consulta enriquecida con datos de ropa y usuario (via Feign → ms-ropa, ms-usuario)
Listado de carritos por usuario
Búsqueda (ms-busqueda — puerto 8088)
Registro de búsquedas con filtros de categoría y rango de precio
Consulta de resultados con detalle de prendas (via Feign → ms-ropa)
Filtrado por categoría
Inventario (ms-inventario — puerto 8089)
Control de stock por producto
Actualización parcial de cantidad (PATCH)
Verificación de disponibilidad por ropaId (via Feign → ms-ropa)
Listado de productos con stock bajo
Envíos (ms-envio — puerto 8090)
Registro y seguimiento de envíos asociados a pedidos
Consulta de envío con datos del pedido (via Feign → ms-pedido)
Cambio de estado del envío (PENDIENTE → EN_CAMINO → ENTREGADO)
Filtrado de envíos por estado
Mapa de Comunicación entre Microservicios:
ms-usuario  ←──── ms-pedido
ms-usuario  ←──── ms-notificaciones
ms-usuario  ←──── ms-carrito
ms-ropa     ←──── ms-inventario
ms-ropa     ←──── ms-carrito
ms-ropa     ←──── ms-busqueda
ms-ropa     ←──── ms-categoria
ms-pedido   ←──── ms-pagos
ms-pedido   ←──── ms-envio
ms-pedido   ←──── ms-usuario (buscarPedidoCompleto)

Pasos para Ejecutar el Proyecto:
1. Iniciar Laragon
Abrir Laragon y hacer clic en Start All para levantar el servidor MySQL en el puerto 3306.
2. Verificar credenciales de base de datos
Todos los microservicios están configurados con:
Usuario: root
Contraseña:
Host: localhost:3306
3. Las bases de datos se crean automáticamente
Cada microservicio tiene configurado createDatabaseIfNotExist=true en la URL de conexión, por lo que no es necesario crear las bases de datos manualmente. Se crearán solas al primer arranque.
4. Orden de arranque recomendado
Respetar este orden para evitar errores de conexión entre microservicios:
1° ms-usuario       → puerto 8081
2° ms-ropa          → puerto 8082
3° ms-pedido        → puerto 8083
4° ms-pagos         → puerto 8084
5° ms-notificaciones → puerto 8085
6° ms-categoria     → puerto 8086
7° ms-carrito       → puerto 8087
8° ms-busqueda      → puerto 8088
9° ms-inventario    → puerto 8089
10° ms-envio        → puerto 8090


5. Ejecutar cada microservicio
Desde la carpeta raíz de cada microservicio, ejecutar el archivo MicroservicioApplication.java
6. Verificar que están corriendo
Probar en el navegador o Postman que cada servicio responde:
GET http://localhost:8081/api/v1/usuario
GET http://localhost:8082/api/v1/ropa
GET http://localhost:8083/api/v1/pedidos
GET http://localhost:8084/api/v1/pagos
GET http://localhost:8085/api/v1/notificaciones
GET http://localhost:8086/api/v1/categorias
GET http://localhost:8087/api/v1/carrito
GET http://localhost:8088/api/v1/busqueda
GET http://localhost:8089/api/v1/inventario
GET http://localhost:8090/api/v1/envio
Todos deben retornar un JSON (lista vacía [] o con los datos insertados por Flyway).
Estructura de Carpetas
microservicios/
├── ms-usuario/
│   └── usuario/
│       └── src/main/java/com/prueba/usuario/
│           ├── Controller/
│           ├── Service/
│           ├── Repository/
│           ├── Model/
│           └── Client/
├── ms-ropa/
├── ms-pedido/
├── ms-pagos/
├── ms-notificaciones/
├── ms-categoria/
├── ms-carrito/
├── ms-busqueda/
├── ms-inventario/
└── ms-envio/
