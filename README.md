# Spring Boot + MySQL + Kubernetes Deployment Guide

This guide walks you through deploying a Spring Boot application with a MySQL database using Docker and Kubernetes (Minikube).

---

## 🔧 Prerequisites

- Docker installed
- Minikube running (e.g., `minikube start`)
- Java + Maven for building the Spring Boot app
- DockerHub account (for pushing images)

---

## 🧱 Step 1: Create Docker Network (For Local Docker Containers)

```bash
docker network create spring-mysql-net
```
**Why?** Creates a virtual bridge network so that MySQL and Spring Boot containers can communicate by name (like `mysql-db`).

---

## 🐬 Step 2: Run MySQL Container

```bash
docker pull mysql:8.3
```
**Why?** Pulls the MySQL Docker image version 8.3.

```bash
docker run --name mysql-db   --network spring-mysql-net   -e MYSQL_ROOT_PASSWORD=user   -e MYSQL_DATABASE=mydb   -e MYSQL_USER=user   -e MYSQL_PASSWORD=user   -p 3306:3306   -d mysql:8.3
```
**Why?** Starts the MySQL container with custom user/database/password and attaches it to the Docker network.

Alternative command (if network is not used):
```bash
docker run --name mysql-db   -e MYSQL_ROOT_PASSWORD=root   -e MYSQL_DATABASE=mydb   -e MYSQL_USER=user   -e MYSQL_PASSWORD=user   -p 3306:3306   -d mysql:8.3
```

---

## 🔍 Step 3: Check MySQL Logs (Optional)

```bash
docker logs mysql-db
```
**Why?** Verifies MySQL started successfully without errors.

---

## ⚙️ Step 4: Build Spring Boot App with Maven

```bash
mvn clean package
```
**Why?** Compiles and packages the app into a `.jar` file inside `target/`.

---

## 🐳 Step 5: Build Docker Image for Spring Boot App

```bash
docker build -t docker-exp-app .
```
**Why?** Builds your Spring Boot app image using your `Dockerfile`.

---

## ▶️ Step 6: Run Spring Boot App with Docker (connected to MySQL)

```bash
docker run --network spring-mysql-net -p 8080:8080 docker-exp-app
```
**Why?** Runs the app container and connects it to the MySQL container over the Docker network.

---

## ☁️ Step 7: Push Image to DockerHub

```bash
docker login
docker tag <imageid> amankumarsharma10/kube-test
docker push amankumarsharma10/kube-test
```

**Why?** Tags and uploads your image to DockerHub so Kubernetes can pull it later.

---

## 🧪 Step 8: Deploy MySQL in Kubernetes

```bash
kubectl apply -f db-deployments.yml
```

**Why?** Applies the MySQL Deployment and Service YAML files to your cluster.

To remove MySQL from Kubernetes:
```bash
kubectl delete deployment mysql
```

---

## 🚀 Step 9: Deploy Spring Boot App in Kubernetes

```bash
kubectl create deployment my-spring-demo --image=amankumarsharma10/kube-test:latest
```

**Why?** Creates a deployment in Kubernetes using the pushed Docker image and without deployment.yml file.

---

## 📦 Step 10: Verify Kubernetes Deployment

```bash
kubectl get deployments
kubectl get pods
kubectl logs <pod-name>
kubectl rollout status deployment my-spring-demo
```

**Why?** Useful for checking deployment, pod status, and logs.

---

## 🔁 Step 11: Rebuild and Redeploy App (if changes are made)

```bash
kubectl rollout restart deployment my-spring-demo
```

**Why?** Restarts the deployment to pick up the latest image.

---

## 🐳 Step 12: Tag and Push New Docker Image (After Code Changes)

```bash
docker tag springboot-exp-app-k8s amankumarsharma10/springboot-exp-app-k8s:latest
docker push amankumarsharma10/springboot-exp-app-k8s:latest
```

**Why?** Pushes the new version of your Spring Boot app image to DockerHub.

---

## Deploy MYSQL and SpringBootApp by Applying Deployment YAML

```bash
kubectl apply -f db-deployments.yml

kubectl apply -f app-deployments.yml
```

**Why?** Deploys or updates the Spring Boot app in Kubernetes using your YAML file 
before applying you need to *mvn clean package* and build docker image then run above cmd

---

## 🌐 Port Forward the Kubernetes Service

```bash
kubectl port-forward service/springboot-service-svc 8080:80
```

**Why?** For local testing, forwards port 8080 on your machine to the service port (80) inside the cluster.

---

## ✅ Access the Application

```
http://127.0.0.1:8080/api/users/1234
```

**Why?** Confirms the application is deployed and accessible.

---

## 🛳 (Optional) Use Local Docker Images in Minikube Without Pushing

```bash
@FOR /f "tokens=*" %i IN ('minikube -p minikube docker-env --shell cmd') DO @%i
```

**Why?** Configures your shell to use Minikube’s Docker daemon so images are immediately available inside the cluster.

---

## 📁 Folder Structure

```
docker-exp/
├── Dockerfile
├── app-deployments.yml
├── db-deployments.yml
├── src/
│   └── main/java/... (your app code)
```

---

## 📌 Notes

- Ensure `application.properties` is configured for `MYSQL_HOST`, `PORT`, `USERNAME`, `PASSWORD`:
  ```properties
  spring.datasource.url=jdbc:mysql://mysql-db:3306/mydb
  spring.datasource.username=user
  spring.datasource.password=user
  ```
- When using Kubernetes, use the **service name** of MySQL as the DB host.
