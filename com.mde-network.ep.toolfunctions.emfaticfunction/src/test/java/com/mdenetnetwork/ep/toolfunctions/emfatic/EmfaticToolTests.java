package com.mdenetnetwork.ep.toolfunctions.emfatic;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import com.mdenetnetwork.ep.toolfunctions.emfatic.EmfaticTool;


public class EmfaticToolTests {
	
	@Test
	void convertEmfaticToDiagram() throws Exception{
		JsonObject response = new JsonObject();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		 
		 
		new EmfaticTool().convertEmfaticToDiagram( emfatic_mm, outstream, response );
		
	}
	
	@Test
	void convertEmfaticToEcore() throws Exception{
		JsonObject response = new JsonObject();
		 ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			
		 new EmfaticTool().convertEmfaticToEcore(emfatic_mm, outstream, response);
		 
		 // TODO Check for image
	}
 	
	public static final String emfatic_mm = "@\"http://www.eclipse.org/OCL/Import\"(ecore=\"http://www.eclipse.org/emf/2002/Ecore\")\n"
			+ "@namespace(uri=\"http://www.eclipse.org/mdt/ocl/oclinecore/tutorial\", prefix=\"tut\")\n"
			+ "package tutorial;\n"
			+ "\n"
			+ "class Library {\n"
			+ "	attr String[1] name;\n"
			+ "\n"
			+ "	@\"http://www.eclipse.org/OCL/Collection\"(nullFree=\"false\")\n"
			+ "	!ordered val Book[*]#library books;\n"
			+ "\n"
			+ "	@\"http://www.eclipse.org/OCL/Collection\"(nullFree=\"false\")\n"
			+ "	!ordered val Loan[*] loans;\n"
			+ "\n"
			+ "	@\"http://www.eclipse.org/OCL/Collection\"(nullFree=\"false\")\n"
			+ "	!ordered val Member[*]#library members;\n"
			+ "}\n"
			+ "\n"
			+ "class Book {\n"
			+ "	attr String[1] name;\n"
			+ "	attr EBigInteger[1] copies;\n"
			+ "	ref Library#books library;\n"
			+ "}\n"
			+ "\n"
			+ "class Member {\n"
			+ "	attr String[1] name;\n"
			+ "	ref Library#members library;\n"
			+ "}\n"
			+ "\n"
			+ "class Loan {\n"
			+ "	ref Book[1] book;\n"
			+ "	ref Member[1] member;\n"
			+ "	attr EDate date;\n"
			+ "}\n"
			+ "";
	
}