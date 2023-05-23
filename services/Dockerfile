# syntax=docker/dockerfile:1

FROM maven:3.8.5-openjdk-17 

COPY services /usr/src/toolfunctions

WORKDIR /usr/src/toolfunctions

RUN mvn clean install

# Get runtime dependencies 
RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.6.0:get -Dartifact=com.google.cloud.functions:function-maven-plugin:0.9.5 && mvn org.apache.maven.plugins:maven-dependency-plugin:3.6.0:get -Dartifact=org.apache.maven.plugins:maven-deploy-plugin:2.7 

WORKDIR /usr/src/toolfunctions

