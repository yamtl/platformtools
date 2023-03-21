package com.mdenetnetwork.ep.toolfunctions.epsilonfunction;

import java.io.ByteArrayOutputStream;

import com.google.gson.JsonObject;
import com.mdenetnetwork.ep.toolfunctions.core.MdeNetToolFunction;

import com.mdenetwork.ep.toolfunctions.epsilon.EpsilonTool;


public class RunConversionFlexmiToXmi extends MdeNetToolFunction {

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		new EpsilonTool().convertFlexmiToXmi(request.get("input").getAsString(),      // flexmi
											 request.get("metamodel").getAsString(),  // emfatic
											 bos, response);
		
	}
// mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.epsilonfunction.RunConversionFlexmiToXmi -Drun.port=9090
 
}

