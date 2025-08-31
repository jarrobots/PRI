# Use OpenJDK 21 runtime base image
FROM openjdk:21-jdk

# Set working directory inside the container
WORKDIR /app

# Copy your JAR file into the container; adjust the path and filename accordingly
COPY out/PRI.jar PRI.jar

# Expose your application's running port 8082
EXPOSE 8082

# Command to run your JAR file
ENTRYPOINT ["java", "-jar", "PRI.jar", "--spring.main.web-application-type=servlet"]