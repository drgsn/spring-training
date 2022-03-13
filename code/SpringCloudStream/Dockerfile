FROM openjdk:latest

ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar

ENV JAVA_OPTS=""
ENV RABBIT_HOST="127.0.0.1"
ENV RABBIT_PORT="5672"
ENV RABBIT_USER="guest"
ENV RABBIT_PASS="guest"

EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar