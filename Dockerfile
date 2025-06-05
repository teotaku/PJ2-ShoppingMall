FROM openjdk:17-jdk-slim
COPY ./build/libs/*SNAPSHOT.jar project.jar
ENTRYPOINT ["java", " -jar", "project.jar"]