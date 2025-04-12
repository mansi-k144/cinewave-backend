# Use exact Java version from your pom.xml
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests

# Create final lightweight image
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Run as non-root user for security
RUN useradd -m myuser && chown -R myuser:myuser /app
USER myuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]