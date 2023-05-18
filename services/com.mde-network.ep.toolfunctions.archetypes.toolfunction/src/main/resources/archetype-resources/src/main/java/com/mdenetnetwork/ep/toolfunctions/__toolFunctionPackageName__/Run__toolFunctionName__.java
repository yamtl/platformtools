package ${groupId.replaceAll("-", "")}.${toolFunctionPackageName};

import java.io.ByteArrayOutputStream;

import com.google.gson.JsonObject;
import com.mdenetnetwork.ep.toolfunctions.core.MdeNetToolFunction;

import ${groupId.replaceAll("-", "")}.${toolPackageName}.${toolName}Tool;


public class Run${toolFunctionName} extends MdeNetToolFunction {

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		new ${toolName}Tool().
			run(	request.get("parmameter1").getAsString() , //TODO add parameters
					request.get("parmameter2").getAsString() ,
					request.get("parmameter3").getAsString() ,
					bos, response);
			
		response.addProperty("output", bos.toString());
	}
	
// mvn function:run -Drun.functionTarget=${groupId.replaceAll("-", "")}.${toolFunctionPackageName}.Run${toolFunctionName} -Drun.port=9090
 
}
