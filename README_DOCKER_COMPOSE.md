# Spring Boot + MySQL with Docker Compose

This project demonstrates how to run a Spring Boot application with a MySQL database using **Docker Compose**.

---

## 🧱 Prerequisites

- Docker & Docker Compose installed
- Spring Boot application with a valid `Dockerfile` in the same directory
- Maven to build the project if not already packaged

---

## 📂 Project Structure

```
project-root/
├── Dockerfile
├── docker-compose.yml
├── src/
│   └── main/java/... (your Spring Boot application code)
└── ...
```


---

## 🐳 docker-compose.yml

```yaml
services:
  spring-app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: docker-exp-app
    depends_on:
      - mysql-db
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-db:3306/mydb
      SPRING_DATASOURCE_USERNAME: user
      SPRING_DATASOURCE_PASSWORD: user
    networks:
      - spring-net

  mysql-db:
    image: mysql:8.3
    container_name: mysql-db
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: mydb
      MYSQL_USER: user
      MYSQL_PASSWORD: user
    ports:
      - "3306:3306"
    networks:
      - spring-net

networks:
  spring-net:
```
---

## ⚙️ How It Works

This setup defines two services:

1. **spring-app**: Your Spring Boot application built from the local `Dockerfile`.
2. **mysql-db**: A MySQL 8.3 database container.

Both services share a Docker network named `spring-net` for internal communication.

---

## 🚀 Steps to Run

### 1. Build the Spring Boot Application (if not yet built)

```bash
mvn clean package
```

---

### 2. Start the Application and Database

```bash
docker compose up --build
```

- `--build`: Ensures the Spring Boot app image is built fresh using the provided Dockerfile.
- Docker Compose will automatically pull MySQL 8.3 if it's not present locally.

---

### 3. Verify Application is Running

Once containers are up:

- Spring Boot API: [http://localhost:8080](http://localhost:8080)
- MySQL DB: Accessible internally as `mysql-db:3306`

---

## ⚙️ Configuration Details

### Spring App Environment Variables (in `docker-compose.yml`):

```yaml
SPRING_DATASOURCE_URL: jdbc:mysql://mysql-db:3306/mydb
SPRING_DATASOURCE_USERNAME: user
SPRING_DATASOURCE_PASSWORD: user
```

### MySQL Container Configuration:

```yaml
MYSQL_ROOT_PASSWORD: root
MYSQL_DATABASE: mydb
MYSQL_USER: user
MYSQL_PASSWORD: user
```

---

## 🧹 Stop and Clean Up

To stop containers:

```bash
docker compose down
```

To remove volumes as well:

```bash
docker compose down -v
```

---

## 📝 Notes

- Ensure your `application.properties` or `application.yml` file in the Spring Boot app uses the same DB credentials.
- Database hostname in your app should be `mysql-db` (as defined in Compose).