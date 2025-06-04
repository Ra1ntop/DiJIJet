# Use official Gradle image for build stage
FROM gradle:8.13-jdk17 AS builder
WORKDIR /workspace

# Copy all files and build application using Gradle
COPY . .
RUN gradle clean bootJar --no-daemon -x test

# Use minimal JRE for runtime
FROM openjdk:17-slim
WORKDIR /app

# Copy executable JAR from builder stage
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]