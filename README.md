
# hackaton2025-2‑Fup‑nexus‑3

Proyecto hackathon — Fup Nexus (2025). Aplicación fullstack con frontend en React + TypeScript y backend en Java con Maven.

## Tabla de contenidos
1. Descripción
2. Características
3. Arquitectura y stack
4. Estructura del repositorio
5. Requisitos previos
6. Instalación y ejecución (desarrollo)
7. Variables de entorno sugeridas
8. Construcción y despliegue (producción)
9. Pruebas
10. Contribuir
11. Licencia y créditos
12. Contacto

## Descripción
`hackaton2025‑2‑Fup‑nexus‑3` es una aplicación creada para un hackathon (2025) que incluye un frontend moderno en React + TypeScript y un backend Java + Maven. 
El objetivo es ofrecer una base funcional para gestión de productos/ventas/inventario y una interfaz para consumir la API.

## Características
- Gestión de productos (CRUD)
- Autenticación básica (token / sesión)
- Endpoints REST para operaciones retail
- Interfaz SPA en React + TypeScript
- Estructura modular para facilitar desarrollo y pruebas

## Arquitectura y stack
- Frontend: React + TypeScript
- Backend: Java con Maven
- API REST
- Build: Maven (backend), npm/yarn (frontend)

## Estructura del repositorio
backend/
└─ retail‑backend/
   ├─ src/
   ├─ pom.xml
frontend/
└─ src/
   └─ package.json

## Requisitos previos
- Node.js >= 18
- Java JDK 11+
- Maven
- (Opcional) Docker y Docker Compose

## Instalación y ejecución (desarrollo)
### Backend
cd backend/retail‑backend
mvn clean install
mvn spring‑boot:run

### Frontend
cd frontend
npm install
npm start

## Variables de entorno sugeridas
### Backend
PORT=8080
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/dbname
SPRING_DATASOURCE_USERNAME=usuario
SPRING_DATASOURCE_PASSWORD=password
JWT_SECRET=tu_secreto_jwt

### Frontend
REACT_APP_API_URL=http://localhost:8080/api

## Construcción y despliegue
npm run build (frontend)
mvn package (backend)

## Pruebas
- Backend: mvn test
- Frontend: npm test

## Contribuir
1. Fork del repositorio.
2. Crear branch: git checkout -b feature/nombre
3. Commit y push.
4. Pull Request.

## Licencia y créditos
MIT License
© 2025 Equipo Fup Nexus

Autores:
- Eduart Francisco Morales Ussa
- Luis Felipe Robles Cifuentes
- John Kennedy Landazuri Sandoval

## Contacto
Para dudas o mejoras del proyecto, contactar a cualquiera de los autores.
