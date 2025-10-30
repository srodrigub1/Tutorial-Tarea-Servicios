# 🧱 Products API — Spring Boot + H2 + OpenAPI

Este proyecto corresponde al **Tutorial de Servicios REST con Spring Boot y OpenAPI**.  
Implementa una API REST para gestionar productos, documentada automáticamente con **Swagger UI (SpringDoc 2.8.13)** y persistencia en una base de datos **H2 en memoria**.

---

## 🚀 Ejecución del proyecto

### 🧩 Requisitos
- **Java 17** o superior  
- No necesitas instalar Maven (usa el wrapper `mvnw.cmd` o `mvnw` incluido)  

---

### ▶️ Comandos para ejecutar

Desde la carpeta donde está tu `pom.xml`, abre **PowerShell o CMD** y ejecuta:

.\mvnw.cmd spring-boot:run

Si usas Linux o macOS:

./mvnw spring-boot:run

Esto iniciará el servidor en el puerto **8080**.

---

## 🌐 Navegación

| Recurso | URL | Descripción |
|----------|-----|-------------|
| 🌍 **Raíz** | [http://localhost:8080/](http://localhost:8080/) | Redirige automáticamente a Swagger UI |
| 🧭 **Swagger UI** | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) | Interfaz gráfica de documentación y pruebas |
| 📜 **OpenAPI JSON** | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) | Esquema JSON de la API |
| 🗄️ **Consola H2** | [http://localhost:8080/h2-console](http://localhost:8080/h2-console) | Consola web para ver los datos persistidos |

**Credenciales H2:**

| Campo | Valor |
|--------|--------|
| JDBC URL | `jdbc:h2:mem:productsdb` |
| User | `sa` |
| Password | `password` |

---

## ⚙️ Configuración (`application.yml`)

Ubicación: `src/main/resources/application.yml`

server:
  port: 8080
  servlet:
    context-path: /

spring:
  application:
    name: products-api

  datasource:
    url: jdbc:h2:mem:productsdb
    driver-class-name: org.h2.Driver
    username: sa
    password: password

  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true

  h2:
    console:
      enabled: true
      path: /h2-console

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    try-it-out-enabled: true
    operations-sorter: method
    tags-sorter: alpha

logging:
  level:
    com.eafit.tutorial: DEBUG
    org.springframework.web: INFO

---

## 📦 Estructura del proyecto

src/
├─ main/
│  ├─ java/com/eafit/tutorial/
│  │  ├─ controller/
│  │  │  ├─ HomeController.java → redirige `/` hacia Swagger UI  
│  │  │  └─ ProductController.java → CRUD básico de productos  
│  │  ├─ model/Product.java → entidad JPA  
│  │  ├─ repository/ProductRepository.java → capa de persistencia  
│  │  ├─ service/ProductService.java y impl/ProductServiceImpl.java → lógica de negocio  
│  │  ├─ dto/ → objetos de transferencia de datos  
│  │  ├─ util/ProductMapper.java → conversión entidad ⇄ DTO  
│  │  └─ ProductsApiApplication.java → clase principal  
│  └─ resources/
│     └─ application.yml
└─ test/java/com/eafit/tutorial/ProductsApiApplicationTests.java

---

## 🧪 Endpoints principales

### 📘 Crear producto
**POST** `/api/products`

Cuerpo JSON:
{
  "name": "Laptop HP",
  "category": "Electronics",
  "price": 1200.50,
  "stock": 10
}

Respuesta:
{
  "id": 1,
  "name": "Laptop HP",
  "category": "Electronics",
  "price": 1200.50,
  "stock": 10,
  "createdAt": "2025-10-30T10:00:00"
}

---

### 📗 Obtener producto por ID
**GET** `/api/products/{id}`

Ejemplo:
GET http://localhost:8080/api/products/1

Respuesta:
{
  "id": 1,
  "name": "Laptop HP",
  "category": "Electronics",
  "price": 1200.50,
  "stock": 10,
  "createdAt": "2025-10-30T10:00:00"
}

---

## 🧭 Controlador raíz

Archivo: `src/main/java/com/eafit/tutorial/controller/HomeController.java`

package com.eafit.tutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}

---



