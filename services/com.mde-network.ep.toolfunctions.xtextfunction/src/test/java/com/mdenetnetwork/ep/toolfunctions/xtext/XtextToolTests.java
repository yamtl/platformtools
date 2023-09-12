package com.mdenetnetwork.ep.toolfunctions.xtext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.mdenetnetwork.ep.toolfunctions.xtext.XtextTool;



public class XtextToolTests {

	@Test
	void testProjectFileParsedToObject() {
		Gson json = new Gson(); 
		
		final String pathValue = "/a/b/c";
		final String contentsValue = "<x><y><z>";
		final String jsonProjectFile = "{ \"path\": \"" + pathValue + "\", \"contents\":\"" + contentsValue + "\"   }";
		
		ProjectFile parsedPfile = json.fromJson(jsonProjectFile, ProjectFile.class);
		
		assertEquals(pathValue, parsedPfile.getPath());
		assertEquals(contentsValue, parsedPfile.getContents());
	}
	
	@Test
	void testMultipleProjectFilesParsedToObjectsArray() {
		Gson json = new Gson(); 
		
		final String pathValueA = "a";
		final String contentsValueA = "x";
		final String jsonProjectFileA = "{ \"path\": \"" + pathValueA + "\", \"contents\":\"" + contentsValueA + "\"   }";

		final String pathValueB = "b";
		final String contentsValueB = "y";
		final String jsonProjectFileB = "{ \"path\": \"" + pathValueB + "\", \"contents\":\"" + contentsValueB + "\"   }";
		
		final String jsonProjectFileArray = " [  "  + jsonProjectFileA  +    ","   + jsonProjectFileB + "   ]  ";
		
		ProjectFile[] parsedPfiles = json.fromJson(jsonProjectFileArray, ProjectFile[].class);
		
		assertEquals(2, parsedPfiles.length);
		assertEquals(pathValueA, parsedPfiles[0].getPath());
		assertEquals(contentsValueA, parsedPfiles[0].getContents());
		assertEquals(pathValueB, parsedPfiles[1].getPath());
		assertEquals(contentsValueB, parsedPfiles[1].getContents());
	}
	
	
	@Test
	@Disabled
	void testOuputIsGreetingWithInputParameterAppended() throws Exception{
		
		//Prepare inputs
		final String languageName = "base.Language"; // last part must be capitalised
		final String baseName = "base";
		final String extension = "ext";
		final String grammar= "grammar";
		final List<ProjectFile>  projectFiles = null;
		
		final String expectedOutput="TODO";
		
		JsonObject response = new JsonObject();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();

		XtextTool tool = new XtextTool(); 
		
		// Run the tool function
		tool.run(languageName, baseName, extension, grammar, projectFiles, outstream, response);
		
		// Check the result
		assertEquals( expectedOutput, outstream.toString() );
	}	
}