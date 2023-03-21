package com.mdenetnetwork.ep.toolfunctions.epsilon;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.mdenetwork.ep.toolfunctions.epsilon.EpsilonTool;


public class EpsilonToolTests {
	
	@Test
	void test() throws Exception{
		JsonObject response = new JsonObject();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();

		EpsilonTool et = new EpsilonTool(); 

		et.convertFlexmiToXmi(flexmi_mm, emfatic_mm, outstream, response);
		
		String generatedXmi = response.get("output").getAsString();
		
		assertEquals(expectedXmi , generatedXmi);
		
	}	
	
	
	private  static final String emfatic_mm = 
			"@\"http://www.eclipse.org/OCL/Import\"(ecore=\"http://www.eclipse.org/emf/2002/Ecore\")\n"
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
			+ "\n"
			+ "";
	
	
	
	
	private  static final String flexmi_mm = 
			"<?nsuri http://www.eclipse.org/mdt/ocl/oclinecore/tutorial?>\n"
			+ "\n"
			+ "<library name=\"lib\">\n"
			+ "\n"
			+ "	<book name=\"b1\" copies=\"1\" />\n"
			+ "	<book name=\"b2\" copies=\"2\" />\n"
			+ "	<book name=\"b3\" copies=\"3\" />\n"
			+ "	\n"
			+ "	<member name=\"m1\" />\n"
			+ "	<member name=\"m2\" />\n"
			+ "	<member name=\"m3\" />\n"
			+ "	\n"
			+ "	<loan book=\"b1\" member=\"m3\" />\n"
			+ "	<loan book=\"b1\" member=\"m3\" />\n"
			+ "	<loan book=\"b2\" member=\"m2\" />\n"
			+ "	<loan book=\"b3\" member=\"m1\" />\n"
			+ "	\n"
			+ "</library>\n"
			+ "";
	
	private  static final String expectedXmi = 
			"<?xml version=\"1.0\" encoding=\"ASCII\"?>\n"
			+ "<tut:Library xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:tut=\"http://www.eclipse.org/mdt/ocl/oclinecore/tutorial\" name=\"lib\">\n"
			+ "  <books name=\"b1\" copies=\"1\"/>\n"
			+ "  <books name=\"b2\" copies=\"2\"/>\n"
			+ "  <books name=\"b3\" copies=\"3\"/>\n"
			+ "  <loans book=\"//@books.0\" member=\"//@members.2\"/>\n"
			+ "  <loans book=\"//@books.0\" member=\"//@members.2\"/>\n"
			+ "  <loans book=\"//@books.1\" member=\"//@members.1\"/>\n"
			+ "  <loans book=\"//@books.2\" member=\"//@members.0\"/>\n"
			+ "  <members name=\"m1\"/>\n"
			+ "  <members name=\"m2\"/>\n"
			+ "  <members name=\"m3\"/>\n"
			+ "</tut:Library>\n"
			+ "";
	
	
}