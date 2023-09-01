#! /bin/bash

archiveFile=$1
buildDir=$PWD/build/$archiveFile
deployDir=/usr/local/tomcat/webapps

# Prepare
mkdir -p $buildDir
mkdir -p $deployDir

cp ./uploads/$archiveFile $buildDir

cd $buildDir

# Build
unzip $archiveFile

cd ./*.parent

mvn clean install

cd ..

# Deploy
mv $buildDir/*.web/target/*.war $deployDir/$archiveFile.war


