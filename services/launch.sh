#! /bin/bash

gnome-terminal -- /bin/sh -c 'cd com.mde-network.ep.toolfunctions.eclipseoclfunction &&  mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.eclipseoclfunction.RunEclipseOclFunction -Drun.port=9090'

gnome-terminal -- /bin/sh -c 'cd com.mde-network.ep.toolfunctions.emfaticfunction && mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.emfaticfunction.RunConversionEcoreToEmfatic -Drun.port=9091'


gnome-terminal -- /bin/sh -c 'cd com.mde-network.ep.toolfunctions.emfaticfunction && mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.emfaticfunction.RunConversionEmfaticToDiagram -Drun.port=9092'

gnome-terminal -- /bin/sh -c 'cd com.mde-network.ep.toolfunctions.epsilonfunction && mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.epsilonfunction.RunConversionFlexmiToXmi -Drun.port=9093'


gnome-terminal -- /bin/sh -c 'cd $HOME/git/epsilon/playground-docker && docker compose up'

