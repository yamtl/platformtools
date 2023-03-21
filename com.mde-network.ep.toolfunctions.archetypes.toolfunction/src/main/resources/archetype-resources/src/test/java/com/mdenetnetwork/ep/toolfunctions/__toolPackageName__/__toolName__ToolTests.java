package ${groupId.replaceAll("-", "")}.${toolPackageName};



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ${groupId.replaceAll("-", "")}.${toolPackageName}.${toolName}Tool;


public class ${toolName}ToolTests {
	
	@Test
	void test() throws Exception{
		JsonObject response = new JsonObject();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();

		${toolName}Tool et = new ${toolName}Tool(); 

		//TODO test toolfunction
	}	
}