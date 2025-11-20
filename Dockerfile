FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/PRI-0.0.1-SNAPSHOT.jar /app/PRI-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/PRI-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=dev"]

