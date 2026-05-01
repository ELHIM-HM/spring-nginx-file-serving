# 🚀 Spring Boot + Nginx Reverse Proxy (File Upload API)

This project demonstrates a modern containerized architecture using **Docker Compose**. It implements a Spring Boot API for managing file uploads and uses **Nginx** as a *Reverse Proxy* to serve these static files lightning-fast via a shared Docker volume.

## 🏗️ Project Architecture

1. **Spring Boot (Backend):** Listens on port 8080 (internally). Handles `POST` requests for file uploads and saves them in the container's `/uploaded/` directory.
2. **Nginx (Reverse Proxy & Web Server):** Listens on port 80 (exposed to the host).
   - Routes root traffic (`/`) to the Spring Boot application.
   - Intercepts requests to `/uploaded/` to serve files directly without relying on the Java server.
3. **Shared Docker Volume (`uploaded-files`):** The "bridge" between the two containers. Spring Boot writes to it, and Nginx reads from it.

## 📂 Directory Structure
```text
.
├── src/                    # Spring Boot source code
├── nginx-config/           # Nginx configuration
│   ├── default.conf        # Reverse-proxy routing rules
│   └── nginx.Dockerfile    # Custom Docker image for Nginx
├── Dockerfile              # Multi-stage Docker image for Spring Boot
├── docker-compose.yaml     # Service and volume orchestration
├── pom.xml                 # Maven dependencies
└── README.md               # Documentation