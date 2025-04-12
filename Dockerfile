# Build stage
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Add this line to set execute permission
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]