# Proyecto Spring Boot + Oracle XE

Este proyecto es una aplicación Java con Spring Boot que se conecta a una base de datos Oracle XE.

## ⚙️ Tecnologías utilizadas

- Java 17+
- Spring Boot
- Oracle Database XE 21c
- Docker + Docker Compose

## 🧰 Requisitos

- Docker y Docker Compose instalados
- (Opcional) SQL Developer u otra herramienta para conectarse a Oracle

## 🚀 Levantar el entorno de desarrollo

Desde la raíz del proyecto, ejecuta:

```bash
docker-compose up --build

Si presenta problemas ejecute primero:
```bash
docker-compose up oracle-xe --build

y luego:
```bash
docker-compose up spring-app --build

En el directorio SQL encontrará el script para la creación de objetos en la BD.
