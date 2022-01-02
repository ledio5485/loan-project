FROM openjdk:8-jre-alpine

LABEL maintainer="Ledion Spaho"

RUN mkdir /application
WORKDIR /application

COPY target/*.jar ./application.jar

EXPOSE 9280

ARG JAVA_ADDITIONAL_OPTS
ENV JAVA_ADDITIONAL_OPTS=$JAVA_ADDITIONAL_OPTS
ENTRYPOINT java $JAVA_ADDITIONAL_OPTS \
                -Djava.security.egd=file:/dev/./urandom \
                -jar application.jar
