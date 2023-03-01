package com.mdenetnetwork.ep.toolfunctions.eclipseoclfunction;

import java.io.ByteArrayOutputStream;

import com.google.gson.JsonObject;
import com.mdenetnetwork.ep.toolfunctions.core.MdeNetToolFunction;
import com.mdenetnetwork.ep.toolfunctions.eclipseocl.EclipseOclTool;

public class RunEclipseOclFunction extends MdeNetToolFunction {

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		new EclipseOclTool().
			run(	request.get("emfatic").getAsString() ,
					request.get("oclcomplete").getAsString(), 
					request.get("xmi").getAsString(), 
					bos, response);
			
		response.addProperty("output", bos.toString());
	}
	
// mvn function:run -Drun.functionTarget=com.mdenetnetwork.ep.toolfunctions.eclipseoclfunction.RunEclipseOclFunction -Drun.port=9090
 
}

