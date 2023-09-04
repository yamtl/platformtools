# syntax=docker/dockerfile:1


FROM maven:3.6.3-openjdk-17 AS toolfunctions

#Copy ocl toolfunction source and its dependencies
COPY services/com.mde-network.ep.toolfunctions.core /usr/src/toolfunctions/com.mde-network.ep.toolfunctions.core
COPY services/com.mde-network.ep.toolfunctions.emf /usr/src/toolfunctions/com.mde-network.ep.toolfunctions.emf
COPY services/com.mde-network.ep.toolfunctions.xtext /usr/src/toolfunctions/com.mde-network.ep.toolfunctions.xtext
COPY services/com.mde-network.ep.toolfunctions.xtextfunction /usr/src/toolfunctions/com.mde-network.ep.toolfunctions.xtextfunction
COPY services/pom.xml /usr/src/toolfunctions/

WORKDIR /usr/src/toolfunctions

RUN mvn clean install -Pxtext

# Get runtime dependencies 
RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.6.0:get -Dartifact=com.google.cloud.functions:function-maven-plugin:0.9.5 && mvn org.apache.maven.plugins:maven-dependency-plugin:3.6.0:get -Dartifact=org.apache.maven.plugins:maven-deploy-plugin:2.7 

WORKDIR /usr/src/toolfunctions





FROM tomcat:9.0.76-jdk17-temurin

# toolserice main endpoint port
ENV TS_PORT=9000
# editor server internal port
ENV ES_PORT=10001 

RUN apt-get update && apt-get install -y --no-install-recommends unzip maven nodejs npm

# Copy built tool and sources
COPY --from=toolfunctions /root/.m2 /root/.m2
COPY --from=toolfunctions /usr/src/toolfunctions /toolservice


COPY xtext/editorserver /editorserver

WORKDIR /editorserver

RUN npm ci --omit=dev

EXPOSE ${ES_PORT}
EXPOSE ${TS_PORT}
#8080 is the default tomcat public port
EXPOSE 8080

COPY xtext/start.sh /editorserver/start.sh
RUN chmod +x /editorserver/start.sh

ENTRYPOINT [ "/bin/bash", "start.sh" ]