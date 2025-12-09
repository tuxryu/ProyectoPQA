# Sistema de Gestión de Pólizas (SistPolizas)

Sistema integral desarrollado en **Java Spring Boot** para la administración de seguros. Este proyecto implementa una **arquitectura híbrida** que permite a la aplicación funcionar simultáneamente como cliente web, servidor API REST y sincronizador de datos.

## 📋 Requisitos y Herramientas Necesarias

Para ejecutar este proyecto en tu entorno local, necesitas tener instalado lo siguiente:

### 1. Java Development Kit (JDK)
* **Versión:** Java 17 o superior.
* **Verificación:** Ejecuta `java -version` en tu terminal.

### 2. Gestor de Dependencias
* **Herramienta:** Apache Maven (3.8+).
* **Función:** Se encarga de descargar las librerías de Spring Boot, Jackson y el driver de PostgreSQL.

### 3. Base de Datos
* **Motor:** PostgreSQL (Versión 15).
* **Herramienta de Gestión (Opcional):** pgAdmin 4 o DBeaver para visualizar las tablas.

### 4. Entorno de Desarrollo (IDE)
* Recomendado: IntelliJ IDEA, NetBeans.

---

##  Configuración de la Base de Datos

El archivo `application.properties` ya está configurado para conectar con los siguientes credenciales.

Antes de ejecutar la aplicación, debes abrir tu gestor de PostgreSQL (o la terminal `psql`) y ejecutar estos comandos para crear la base de datos y el usuario:

```sql
-- 1. Crear el usuario (rol) con la contraseña configurada
CREATE USER uacm WITH PASSWORD '123456';

-- 2. Crear la base de datos
CREATE DATABASE sistema_polizas;

-- 3. Asignar permisos al usuario sobre la base de datos
GRANT ALL PRIVILEGES ON DATABASE sistema_polizas TO uacm;
```
estructura del  proyecto

sistpolizas
    ├── apiClient
    │   ├── ApiClient.java
    │   ├── ApiClientLocal.java
    │   └── ApiClientRemoto.java
    ├── AuxiliarF
    │   └── Convertir.java
    ├── controller
    │   ├── ClienteContreller.java
    │   ├── IndexController.java
    │   └── PilizaController.java
    ├── httpClient
    │   ├── BeneficiarioHttpClient.java
    │   ├── ClienteHttpClient.java
    │   └── PolizaHttpClient.java
    ├── modelo
    │   ├── Beneficiario.java
    │   ├── Cliente.java
    │   ├── IdBeneficiario.java
    │   └── Poliza.java
    ├── repository
    │   ├── BeneficiarioRepository.java
    │   ├── ClienteRepository.java
    │   └── PolizaRepository.java
    ├── restController
    │   ├── BeneficiarioRestController.java
    │   ├── ClienteRestController.java
    │   └── PolizaRestController.java
    ├── service
    │   ├── BeneficiarioService.java
    │   ├── ClienteService.java
    │   └── PolizaService.java
    └── SistpolizasApplication.java
Paquete	         Descripción
apiClient	       Lógica para decidir si usar datos locales o remotos.
controller	     Controladores web (Thymeleaf) para las vistas HTML.
restController   API REST interna (/api/v1) que expone los datos JSON.
service	         Reglas de negocio y validaciones.
repository	     Interfaces JPA para comunicación con PostgreSQL.
modelo	         Entidades (Cliente, Poliza, Beneficiario).

##Compilar y Ejecutar
Abre una terminal en la carpeta raíz del proyecto (donde está el pom.xml) y ejecuta los siguientes comandos:

Bash

### 1. Limpiar, descargar dependencias y compilar
mvn clean install

### 2. Iniciar el servidor Spring Boot
mvn spring-boot:run

3. Verificar
Una vez que la consola muestre que el servidor ha iniciado, accede desde tu navegador:

Aplicación Web: http://localhost:8080/

API REST: http://localhost:8080/api/clientes
          http://localhost:8080/api/polizas
          http://localhost:8080/api/beneficiarios
