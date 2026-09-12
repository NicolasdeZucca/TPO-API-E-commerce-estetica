# Usa una imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define la carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado por Maven a la imagen de Docker
COPY target/estetica-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto por el que escucha Spring Boot
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]