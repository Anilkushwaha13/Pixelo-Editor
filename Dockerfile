# Use Eclipse Temurin Java 17 base image
FROM eclipse-temurin:17-jdk-jammy as build

# Create app directory
WORKDIR /app

# Copy the built Spring Boot JAR (assumes it's already built)
COPY target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
