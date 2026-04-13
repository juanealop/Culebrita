# Culebrita Game — Arquitectura en Tiempo Real con WebSockets

<p align="center">
  <img src="https://img.shields.io/badge/Angular-Frontend-red?logo=angular" />
  <img src="https://img.shields.io/badge/SpringBoot-Backend-green?logo=springboot" />
  <img src="https://img.shields.io/badge/Cassandra-Database-blue?logo=apachecassandra" />
  <img src="https://img.shields.io/badge/WebSocket-RealTime-orange" />
  <img src="https://img.shields.io/badge/Docker-Containerized-blue?logo=docker" />
</p>

---

## 🚀 Descripción del Sistema

**Culebrita Game** es una aplicación distribuida en tiempo real basada en el clásico juego Snake, implementada bajo una arquitectura moderna cliente-servidor. A diferencia de versiones tradicionales, la lógica del juego no reside en el cliente, sino completamente en el backend, permitiendo una ejecución desacoplada, escalable y consistente.

El sistema gestiona partidas mediante una **máquina de estados**, permitiendo transiciones controladas entre estados como espera, juego activo, pausa y finalización. La interacción del usuario se realiza a través de eventos enviados al backend, el cual procesa la lógica del juego (movimientos, colisiones, puntuación) y transmite continuamente el estado actualizado del tablero al frontend mediante **WebSockets**.

El frontend actúa exclusivamente como un renderizador reactivo y controlador de entrada, mientras que el backend centraliza toda la lógica de negocio. Adicionalmente, el sistema persiste el historial de partidas en una base de datos distribuida, garantizando disponibilidad y tolerancia a fallos.

---

## 🧠 Arquitectura del Sistema

El sistema sigue un enfoque híbrido basado en **MVC + MVVM + comunicación en tiempo real**:

- **Frontend (Vista - MVVM)**: Angular gestiona la interfaz y sincroniza el estado mediante data binding.
- **Backend (Controlador + Modelo - MVC)**: Spring Boot procesa la lógica del juego y coordina eventos.
- **Persistencia (Modelo)**: Cassandra almacena el historial de partidas.
- **Comunicación**: WebSockets permite interacción bidireccional en tiempo real.

---

## 🛠️ Tecnologías Utilizadas

### 🎨 Frontend
- **Angular** → Framework SPA basado en componentes
- **TypeScript** → Tipado estático para mayor mantenibilidad
- **Tailwind CSS** → Estilos rápidos y responsivos
- **RxJS** → Programación reactiva

### ⚙️ Backend
- **Spring Boot (Java)** → Framework empresarial para lógica de negocio
- **Spring WebSocket** → Comunicación en tiempo real
- **Spring Data Cassandra** → Acceso a datos desacoplado

### 🗄️ Base de Datos
- **Apache Cassandra** → Base de datos NoSQL distribuida
  - Alta disponibilidad
  - Escalabilidad horizontal
  - Optimizada para escritura

### 🔌 Comunicación
- **WebSockets** → Canal full-duplex persistente
  - Baja latencia
  - Actualización en tiempo real
  - Sin recarga de página

### 🐳 Infraestructura
- **Docker** → Contenerización de servicios
- **Docker Compose** → Orquestación multi-contenedor

---

## ⚡ Despliegue del Sistema

### 📦 Requisitos Previos

Para ejecutar correctamente el sistema es necesario contar con un entorno preparado para contenerización. Asegúrate de tener instalado:

- 🐳 Docker  
- 🐳 Docker Compose  
- 🔧 Git  

> No es necesario instalar Node.js, Java o Cassandra manualmente, ya que todo se ejecuta dentro de contenedores.

---

### 🔽 Paso 1: Clonar el repositorio

El primer paso consiste en obtener el código fuente del proyecto desde el repositorio público. Esto permitirá acceder a la configuración completa del sistema, incluyendo frontend, backend y archivos de Docker.

```bash
git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio
```

---

### 🐳 Paso 2: Construcción y despliegue del sistema

Una vez dentro del proyecto, se procede a levantar todos los servicios utilizando Docker Compose. Este comando se encarga de construir las imágenes necesarias y ejecutar los contenedores definidos.

```bash
docker-compose up --build
```

Durante este proceso ocurren automáticamente las siguientes acciones:

- 🔨 Se construye la imagen del backend basada en Spring Boot  
- 🎨 Se construye la imagen del frontend basada en Angular  
- 🗄️ Se levanta un contenedor de Cassandra como base de datos  
- 🌐 Se crea una red interna entre todos los servicios  
- 🔗 Se establece la comunicación entre frontend, backend y base de datos  

---

### 🌐 Paso 3: Acceso al sistema

Una vez todos los contenedores estén en ejecución, el sistema queda disponible a través de los siguientes endpoints:

- 🎨 Frontend (Interfaz de usuario): http://localhost:4200  
- ⚙️ Backend (API y WebSocket): http://localhost:8080  
- 📡 WebSocket (tiempo real): ws://localhost:8080/ws  

El frontend se conecta automáticamente al backend mediante WebSockets, permitiendo interacción en tiempo real sin recargas de página.

---

### ⚙️ Paso 4: Ejecución alternativa (modo híbrido)

En caso de requerir ejecutar el frontend de forma local (por ejemplo, para desarrollo), es posible levantar únicamente el backend y la base de datos con Docker.

```bash
cd backend
docker build -t culebrita-backend .
docker-compose up
```

Posteriormente, el frontend puede ejecutarse manualmente:

```bash
cd frontend
npm install
ng serve
```

En este escenario, el frontend se conectará al backend expuesto en `localhost:8080`.

---

### 🔄 Flujo del Sistema (End-to-End)

El funcionamiento completo del sistema sigue un flujo de interacción en tiempo real entre sus componentes:

1. 🎮 El usuario interactúa con el frontend mediante eventos de teclado (direcciones).  
2. 📡 Angular captura la acción y envía un mensaje al backend utilizando WebSocket.  
3. ⚙️ Spring Boot recibe el evento y lo procesa a través de los servicios de negocio.  
4. 🧠 Se actualiza el estado del juego (posición de la culebra, comida, colisiones y puntaje) mediante la máquina de estados.  
5. 🔁 El backend genera un nuevo estado del tablero y lo envía al frontend mediante WebSocket.  
6. 🎨 Angular recibe el estado actualizado y renderiza el tablero en tiempo real.  
7. 💾 Cuando la partida finaliza o se pausa, el backend persiste la información en Cassandra.  
8. 📊 El frontend puede consultar el historial de partidas almacenadas y mostrarlas al usuario.  

Este flujo garantiza una experiencia reactiva, con baja latencia y completamente desacoplada entre la lógica de negocio y la interfaz de usuario.

---

### 🛑 Detener el sistema

Para finalizar la ejecución y detener todos los contenedores activos, se utiliza el siguiente comando:

```bash
docker-compose down
```

Este comando detiene y elimina los contenedores, manteniendo las configuraciones listas para un próximo despliegue.
