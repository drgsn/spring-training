FROM openjdk:latest

ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar

ENV JAVA_OPTS=""
ENV SERVER_PORT="8080"

EXPOSE ${SERVER_PORT}

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar