# Stage 1: build
FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
# Use Maven to build the executable jar (skip tests for faster builds)
RUN mvn -B -DskipTests package

# Stage 2: runtime
FROM eclipse-temurin:17-jre-jammy
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
ARG JAR_FILE=/workspace/target/*.jar
# Create non-root user
RUN addgroup --system app && adduser --system --ingroup app app
COPY --from=builder ${JAR_FILE} /app/app.jar
USER app
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]