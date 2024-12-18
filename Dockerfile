# Base image
FROM openjdk:21

# Application JAR file
ARG JAR_FILE=build/libs/*.jar

# Copy JAR file to container
COPY ${JAR_FILE} app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
