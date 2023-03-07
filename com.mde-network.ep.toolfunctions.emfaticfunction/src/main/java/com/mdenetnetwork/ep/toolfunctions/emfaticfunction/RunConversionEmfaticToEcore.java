package com.mdenetnetwork.ep.toolfunctions.emfaticfunction;

import java.io.ByteArrayOutputStream;

import com.google.gson.JsonObject;
import com.mdenetnetwork.ep.toolfunctions.core.MdeNetToolFunction;

import com.mdenetnetwork.ep.toolfunctions.emfatic.EmfaticTool;


public class RunConversionEmfaticToEcore extends MdeNetToolFunction {

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		

		new EmfaticTool().convertEmfaticToDiagram(
				request.get("input").getAsString(), bos, response);
		
	}
// mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.emfaticunction.RunConversionEmfaticToEcore -Drun.port=9090
 
}

