# ---- build stage ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# copy maven wrapper + pom first (layer caching: deps only re-download if pom changes)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

# copy source and build
COPY src/ src/
RUN ./mvnw clean package -DskipTests -B

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# copy only the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]