package com.mdenetnetwork.ep.toolfunctions.xtext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import com.mdenetnetwork.ep.toolfunctions.xtext.XtextTool;

public class XtextToolTests {
	
	@Test
	@Disabled
	void testOuputIsGreetingWithInputParameterAppended() throws Exception{
		
		//Prepare inputs
		final String languageName = "base.Language"; // last part must be capitalised
		final String baseName = "base";
		final String extension = "ext";
		final String projectFiles = "projectfiles";
		
		final String expectedOutput="TODO";
		
		JsonObject response = new JsonObject();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();

		XtextTool tool = new XtextTool(); 
		
		// Run the tool function
		tool.run(languageName, baseName, extension, projectFiles, outstream, response);
		
		// Check the result
		assertEquals( expectedOutput, outstream.toString() );
	}	
}