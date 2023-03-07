package com.mdenetnetwork.ep.toolfunctions.emffunction;

import java.io.ByteArrayOutputStream;

import com.google.gson.JsonObject;
import com.mdenetnetwork.ep.toolfunctions.core.MdeNetToolFunction;

import main.java.com.mdenetnetwork.ep.toolfunctions.emf.EmfTool;


public class RunConversionXmiToDiagram extends MdeNetToolFunction {

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		

		new EmfTool().ConvertXmiToDiagram(
				request.get("input").getAsString(), bos, response);
		
	}
// mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.emfunction.RunConversionEmfToDiagram -Drun.port=9090
 
}

