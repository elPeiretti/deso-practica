# Etapa 1: compila el .jar con el JDK completo
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copiar primero solo lo necesario para bajar dependencias: Docker reutiliza esta capa
# mientras no cambie el pom.xml, y no las vuelve a descargar en cada build
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -q

COPY src src
RUN ./mvnw package -DskipTests -q

# Etapa 2: la imagen final solo lleva el JRE y el .jar
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
