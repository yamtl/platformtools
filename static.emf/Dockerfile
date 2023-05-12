# syntax=docker/dockerfile:1

FROM nginx:1.24.0-bullseye AS toolservice

# Needed to avoid prompts blocking the build process
ENV DEBIAN_FRONTEND=noninteractive

# Needed for Cloud Build
ENV PORT=80

# Install Python
RUN apt-get update \
    && apt-get install -y python3-minimal maven tini netcat \
    && rm -rf /var/lib/apt/lists/*

# Copy tool sources
#COPY services /toolservice
#COPY services/pom.xml services/.project /toolservice/

# Copy files for webserver
COPY static.emf/nginx.conf.template /etc/nginx.conf.template

RUN rm -r /usr/share/nginx/html/*
COPY static.emf/dist /usr/share/nginx/html

WORKDIR /toolservice


# Due to https://issues.apache.org/jira/browse/MDEP-568, m-dependency-p
# is not a practical solution for ensuring all dependencies are available.
#
# We use https://github.com/qaware/go-offline-maven-plugin instead.
#RUN mvn -B de.qaware.maven:go-offline-maven-plugin:1.2.8:resolve-dependencies

# Copy start script and make it executable
ADD static.emf/start.sh /
RUN chmod +x /start.sh

ENTRYPOINT ["/usr/bin/tini", "--", "/start.sh"]