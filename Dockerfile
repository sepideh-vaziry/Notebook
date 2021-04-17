# our final base image
FROM openjdk:8-jre-alpine

# copy over the built artifact from the maven image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]