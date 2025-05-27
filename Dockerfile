# Etapa de construcción con Semeru JDK 23 y Maven
FROM icr.io/appcafe/semeru-runtimes:open-23-jdk as build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar solo archivos necesarios para cacheo de dependencias
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Descargar dependencias para optimizar cache
RUN ./mvnw dependency:go-offline -B

# Copiar el resto del código fuente
COPY src ./src

# Compilar la app sin ejecutar los tests
RUN ./mvnw package -DskipTests

# Etapa final: imagen liviana con Semeru JDK 23
FROM icr.io/appcafe/semeru-runtimes:open-23-jdk

WORKDIR /app
EXPOSE 8080

# Copiar el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
