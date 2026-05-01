# 🚀 Spring Boot + Nginx Reverse Proxy (File Upload API)

This project demonstrates a modern containerized architecture using Docker Compose. It implements a Spring Boot API for managing file uploads and uses Nginx as a Reverse Proxy to serve these static files lightning-fast via a shared Docker volume.

---

## 🏗️ Project Architecture

1. **Spring Boot (Backend):** Listens on port `8080` (internally). Handles `POST` requests for file uploads and saves them in the container's `/uploaded/` directory.
2. **Nginx (Reverse Proxy & Web Server):** Listens on port `80` (exposed to the host).
   - Routes root traffic (`/`) to the Spring Boot application.
   - Intercepts requests to `/uploaded/` to serve files directly without relying on the Java server.
3. **Shared Docker Volume (`uploaded-files`):** The "bridge" between the two containers. Spring Boot writes to it, and Nginx reads from it.

---

## 📂 Directory Structure

```
.
├── src/                    # Spring Boot source code
├── nginx-config/           # Nginx configuration
│   ├── default.conf        # Reverse-proxy routing rules
│   └── nginx.Dockerfile    # Custom Docker image for Nginx
├── Dockerfile              # Multi-stage Docker image for Spring Boot
├── docker-compose.yaml     # Service and volume orchestration
├── pom.xml                 # Maven dependencies
└── README.md               # Documentation
```

---

## 🚀 How to Run the Entire App

Make sure you have **Docker** and **Docker Compose** installed. To build the images and start the complete application stack in the background, simply run the following command at the root of the project (where your `docker-compose.yaml` is located):

```bash
docker compose up --build -d
```

To verify that both the Spring Boot and Nginx containers are running correctly, you can check their status with:

```bash
docker compose ps
```

---

## 🧪 Testing the API

### 1. How to Add an Image Using the Endpoint

You can upload an image by sending a `POST` request to the Spring Boot upload endpoint. You can use tools like **Postman**, or do it directly from your terminal using `curl`.

**Using cURL:**

> Make sure to replace `/path/to/your/image.jpg` with the actual path to an image on your computer.

```bash
curl -X POST http://localhost:8080/upload -F "file=@/path/to/your/image.jpg"
```

**Expected Response:** If successful, the server will respond with a `200 OK` status and a message confirming the upload path:

```
Fichier uploadé avec succès vers : /uploaded/image.jpg
```

### 2. How to Read the Uploaded Image

Once the image is successfully uploaded to the shared volume, Nginx takes over to serve it.

Open your web browser and navigate to the Nginx server on port `80`:

```
http://localhost/uploaded/image.jpg
```

> Replace `image.jpg` with the exact filename you just uploaded. You should instantly see your image displayed in the browser!

---

## 🧹 Cleanup and Teardown

To **stop** the application but keep your uploaded files safe in the volume for next time:

```bash
docker compose stop
```

To completely **destroy** the containers, the network, and the shared volume *(this will delete all uploaded files)*:

```bash
docker compose down -v
```