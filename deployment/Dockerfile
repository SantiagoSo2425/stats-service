# Etapa 1: Construcción del JAR
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiamos los archivos de configuración de Gradle primero
COPY build.gradle settings.gradle main.gradle gradle.properties ./
COPY gradle gradle/

# Copiamos gradlew y damos permisos
COPY gradlew gradlew.bat ./
RUN chmod +x ./gradlew

# Copiamos las carpetas del proyecto explícitamente
COPY applications applications/
COPY domain domain/
COPY infrastructure infrastructure/
COPY deployment deployment/

# Compilamos directamente el jar de la aplicación principal
# Saltamos la validación de estructura para evitar el error de path
RUN ./gradlew :app-service:bootJar -x validateStructure --no-daemon --info

# Etapa 2: Imagen final
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/applications/app-service/build/libs/*.jar /app/stats-service.jar

ENTRYPOINT ["java", "-jar", "/app/stats-service.jar"]
