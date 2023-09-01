
#! /bin/bash

# start tomcat
catalina.sh run &

# start editorserver
node ./src/server.js &

# wait for them all
wait -n