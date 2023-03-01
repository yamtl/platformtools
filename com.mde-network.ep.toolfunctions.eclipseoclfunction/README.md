# Eclipse OCL Tool Function

Prerequisites:
- [Maven](https://maven.apache.org/)

To build the tool functions, at the root directory of the platformtools repository  run the following commands. 

```
mvn clean
mvn install
```

To Run the eclipse ocl tool function backend service locally, navigate to the `com.mde-network.ep.toolfunctions.eclipseoclfunction` subdirectory and run the follwoing command.

```
mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.eclipseoclfunction.RunEclipseOclFunction -Drun.port=9090
```
