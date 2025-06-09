# Use Eclipse Temurin Java 17 base image
FROM openjdk:17-jdk-slim as build

# Create app directory
WORKDIR /app

# 1. Create models directory
RUN mkdir -p /app/models

# 2. Copy ONLY the model file (not in JAR)
COPY src/main/resources/u2net.onnx /app/models/

# 4. Set environment variable
ENV MODEL_PATH=/app/models/u2net.onnx

# Copy the built Spring Boot JAR (assumes it's already built)
COPY target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
