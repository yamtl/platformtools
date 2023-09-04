package com.mdenetnetwork.ep.toolfunctions.xtextfunction;

import java.io.ByteArrayOutputStream;

import com.google.gson.JsonObject;
import com.mdenetnetwork.ep.toolfunctions.core.MdeNetToolFunction;

import com.mdenetnetwork.ep.toolfunctions.xtext.XtextTool;


public class RunXtextFunction extends MdeNetToolFunction {

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		new XtextTool().
			run( request.get("languageName").getAsString() , 
				 request.get("baseName").getAsString(), 
				 request.get("extension").getAsString(),
				 request.get("projectFiles").getAsString(),
				 bos, response);
			
		response.addProperty("output", bos.toString());
	}
	
// mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.xtextfunction.RunXtextFunction -Drun.port=9090
 
}
