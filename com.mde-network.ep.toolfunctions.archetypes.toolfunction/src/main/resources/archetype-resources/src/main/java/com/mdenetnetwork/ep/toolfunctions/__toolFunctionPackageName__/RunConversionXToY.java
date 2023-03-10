package ${groupId.replaceAll("-", "")}.${toolFunctionPackageName};

import java.io.ByteArrayOutputStream;

import com.google.gson.JsonObject;
import com.mdenetnetwork.ep.toolfunctions.core.MdeNetToolFunction;

import ${groupId.replaceAll("-", "")}.${toolPackageName}.${toolName}Tool;


public class RunConversionXToY extends MdeNetToolFunction {

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		${toolName}Tool().convertXToY(request.get("input").getAsString(), bos, response);
		
	}
// mvn function:run -Drun.functionTarget=${groupId.replaceAll("-", "")}.${toolFunctionPackageName}.RunConversionXToY -Drun.port=9090
 
}

