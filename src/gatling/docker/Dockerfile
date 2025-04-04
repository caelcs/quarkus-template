# Use an official JDK image
FROM registry.access.redhat.com/ubi9/openjdk-21:1.21

# Set working directory inside the container
WORKDIR /app

# Copy the Gradle build files first (to leverage caching)
COPY --chown=185 gradlew gradlew.bat ./
COPY --chown=185 gradle/ gradle/
COPY --chown=185 build.gradle settings.gradle gradle.properties ./

# Download dependencies first (so they are cached)
RUN ./gradlew dependencies --no-daemon

# Copy the entire project
COPY . .

COPY --chown=185 . .

# Build the Gatling tests

RUN ./gradlew clean gatlingClasses --no-daemon

# Set environment variables (these can be overridden at runtime)
ENV KEYCLOAK_URL="http://keycloak:8080"
ENV QUARKUS_APP_URL="http://quarkus-app:8080"
ENV CONCURRENT_USERS="30"
ENV DURATION="30"

# Set the entrypoint command
CMD ["./gradlew", "gatlingRun"]
