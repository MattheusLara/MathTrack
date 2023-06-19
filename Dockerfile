FROM openjdk:17-alpine3.14
LABEL maintainer="MatheusLara"

VOLUME /tmp
EXPOSE 8091

ARG JAR_FILE=target/mathtrack*.jar
ADD ${JAR_FILE} mathtrack.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /mathtrack.jar --logging.file=/tmp/mathtrack.log"]