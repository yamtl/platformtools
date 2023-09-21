package com.mdenetnetwork.ep.toolfunctions.xtext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;


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
	
}