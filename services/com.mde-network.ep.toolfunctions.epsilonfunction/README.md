# Additional MDENet Epsilon Tool Functions

Prerequisites:
- [Maven](https://maven.apache.org/)

To build the tool functions, at the root directory of the platformtools repository  run the following commands. 

```
mvn clean
mvn install
```

To Run the additional epsilon tool function backend service locally, navigate to the `com.mde-network.ep.toolfunctions.epsilonfunction` subdirectory and run the following commands.

```
mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.epsilonfunction.RunConversionFlexmiToXmi -Drun.port=9093'
```
