# Use official JDK for build stage
FROM openjdk:17-jdk-slim AS builder
# Set working directory for build stage
WORKDIR /workspace

# Copy all files and build application
COPY . .
RUN chmod +x gradlew && \
    ./gradlew clean bootJar --no-daemon -x test

# Use minimal JRE for runtime
FROM openjdk:17-slim
WORKDIR /app

# Copy executable JAR from builder stage
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]