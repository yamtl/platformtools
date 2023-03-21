# Emfatic Tool Function

Prerequisites:
- [Maven](https://maven.apache.org/)

To build the tool functions, at the root directory of the platformtools repository  run the following commands. 

```
mvn clean
mvn install
```

To Run the emfatic tool function backend services locally, navigate to the `com.mde-network.ep.toolfunctions.emfaticfunction` subdirectory and run the following commands.

```
mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.emfaticfunction.RunConversionEcoreToEmfatic -Drun.port=9091'

mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.emfaticfunction.RunConversionEmfaticToDiagram -Drun.port=9092'
```
