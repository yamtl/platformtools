# syntax=docker/dockerfile:1

FROM tomcat:9.0.76-jdk17-temurin

RUN apt-get update && apt-get install -y --no-install-recommends unzip maven nodejs npm

COPY editorserver /editorserver

WORKDIR /editorserver

RUN npm ci --omit=dev

EXPOSE 10001
EXPOSE 8080

COPY start.sh /editorserver/start.sh

ENTRYPOINT [ "/bin/bash", "start.sh" ]