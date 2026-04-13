# Culebrita Game — Arquitectura en Tiempo Real con WebSockets

<p align="center">
  <img src="https://img.shields.io/badge/Angular-Frontend-DD0031?logo=angular&logoColor=white" />
  <img src="https://img.shields.io/badge/SpringBoot-Backend-6DB33F?logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Apache_Cassandra-Database-1287B1?logo=apachecassandra&logoColor=white" />
  <img src="https://img.shields.io/badge/WebSockets-RealTime-F7DF1E?logo=websocket&logoColor=black" />
  <img src="https://img.shields.io/badge/Docker-Containerized-2496ED?logo=docker&logoColor=white" />
</p>

<p align="center">
  <b>Arquitectura distribuida • Comunicación en tiempo real • Backend-driven logic</b>
</p>

---

## 🧩 Descripción del Sistema

**Culebrita Game** es una aplicación distribuida en tiempo real basada en el clásico juego Snake, diseñada bajo una arquitectura moderna cliente-servidor altamente desacoplada.

A diferencia de implementaciones tradicionales, **toda la lógica del juego reside en el backend**, permitiendo una ejecución consistente, escalable y controlada. El frontend actúa únicamente como una capa de visualización reactiva y captura de eventos.

El sistema implementa una **máquina de estados**, gestionando el ciclo de vida de cada partida (espera, ejecución, pausa y finalización). Cada interacción del usuario se envía al backend, donde se procesa la lógica completa del juego (movimientos, colisiones, puntuación), retornando el estado actualizado en tiempo real mediante **WebSockets**.

Adicionalmente, el sistema persiste el historial de partidas en una base de datos distribuida, garantizando alta disponibilidad y tolerancia a fallos.

---

## 🏗️ Arquitectura del Sistema

El sistema implementa un enfoque híbrido basado en:

- **MVC (Backend)** → Control de lógica y orquestación
- **MVVM (Frontend)** → Sincronización reactiva de la UI
- **WebSockets** → Comunicación bidireccional en tiempo real

### 🔎 Distribución de responsabilidades

| Capa | Tecnología | Responsabilidad |
|------|----------|---------------|
| Frontend | Angular | Renderizado y captura de eventos |
| Backend | Spring Boot | Lógica de negocio y control |
| Modelo | Java + State Pattern | Gestión del estado del juego |
| Persistencia | Cassandra | Almacenamiento distribuido |
| Comunicación | WebSockets | Sincronización en tiempo real |

---

## 🛠️ Stack Tecnológico

### 🔧 Tecnologías principales

| Categoría | Tecnología | Descripción |
|----------|----------|------------|
| Frontend | Angular + TypeScript | SPA reactiva basada en componentes |
| Backend | Spring Boot | Framework empresarial para lógica de negocio |
| Base de datos | Apache Cassandra | NoSQL distribuida y altamente disponible |
| Comunicación | WebSockets | Canal full-duplex de baja latencia |
| 🐳 Infraestructura | Docker + Docker Compose | Contenerización y orquestación |

### Características clave

- Arquitectura desacoplada cliente-servidor  
- Procesamiento centralizado en backend  
- Comunicación en tiempo real sin polling  
- Persistencia distribuida y escalable  
- Despliegue reproducible mediante contenedores  

---

## ⚡ Despliegue del Sistema

### 📦 Requisitos Previos

Para ejecutar correctamente el sistema es necesario contar con un entorno preparado para contenerización:

- 🐳 Docker  
- 🐳 Docker Compose  
- 🔧 Git  

> No es necesario instalar Node.js, Java o Cassandra manualmente.

---

### 🔽 Paso 1: Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio
```

---

### 🐳 Paso 2: Construcción y despliegue

```bash
docker-compose up --build
```

Este proceso ejecuta automáticamente:

- Build del backend (Spring Boot)  
- Build del frontend (Angular)  
- Inicialización de Cassandra  
- Creación de red interna  
- Integración entre servicios  

---

### 🌐 Paso 3: Acceso al sistema

| Servicio | URL |
|--------|-----|
| Frontend | http://localhost:4200 |
| Backend | http://localhost:8080 |
| WebSocket | ws://localhost:8080/ws |

---

### ⚙️ Paso 4: Ejecución alternativa

Backend + DB con Docker:

```bash
cd backend
docker build -t culebrita-backend .
docker-compose up
```

Frontend local:

```bash
cd frontend
npm install
ng serve
```

---

## 🔄 Flujo del Sistema (End-to-End)

El sistema opera bajo un flujo reactivo en tiempo real:

```text
🎮 Usuario → Angular → WebSocket → Spring Boot
       → Lógica del juego → Estado actualizado
       → WebSocket → Renderizado UI
       → Persistencia en Cassandra
```

### 🔁 Secuencia detallada

1. 🎮 El usuario genera eventos de entrada (teclado)  
2. 📡 Angular envía la acción al backend  
3. ⚙️ Spring Boot procesa la lógica  
4. 🧠 Se actualiza el estado del juego  
5. 🔁 Se genera nuevo estado del tablero  
6. 🎨 Angular renderiza en tiempo real  
7. 💾 Se persiste la partida en Cassandra  
8. 📊 Se consulta historial desde el frontend  

---

## 🛑 Detener el sistema

```bash
docker-compose down
```

Este comando detiene y elimina los contenedores activos.

---
