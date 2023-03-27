package com.mdenetnetwork.ep.toolfunctions.epsilon;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.mdenetwork.ep.toolfunctions.epsilon.EpsilonTool;


public class EpsilonToolTests {
	
	@Test
	void testFlexmiToXmi() throws Exception{
		JsonObject response = new JsonObject();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();

		EpsilonTool et = new EpsilonTool(); 

		et.convertFlexmiToXmi(flexmi, ecore_mm, outstream, response);
		
		String generatedXmi = response.get("output").getAsString();
		
		assertEquals(expectedXmi , generatedXmi);
		
	}	

	@Test
	void testFlexmiToXmi_mmhasfilerefs() throws Exception{
		// resolution of resource url when "ecore.ecore"
		JsonObject response = new JsonObject();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();

		EpsilonTool et = new EpsilonTool(); 

		et.convertFlexmiToXmi(flexmi, ecore_mm_filerefs, outstream, response);
		
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
	
	
	private  static final String ecore_mm = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<ecore:EPackage xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
			+ "    xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" name=\"tutorial\" nsURI=\"http://www.eclipse.org/mdt/ocl/oclinecore/tutorial\"\n"
			+ "    nsPrefix=\"tut\">\n"
			+ "  <eAnnotations source=\"http://www.eclipse.org/OCL/Import\">\n"
			+ "    <details key=\"ecore\" value=\"http://www.eclipse.org/emf/2002/Ecore\"/>\n"
			+ "  </eAnnotations>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Library\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" lowerBound=\"1\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"books\" ordered=\"false\"\n"
			+ "        upperBound=\"-1\" eType=\"#//Book\" containment=\"true\" eOpposite=\"#//Book/library\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"loans\" ordered=\"false\"\n"
			+ "        upperBound=\"-1\" eType=\"#//Loan\" containment=\"true\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"members\" ordered=\"false\"\n"
			+ "        upperBound=\"-1\" eType=\"#//Member\" containment=\"true\" eOpposite=\"#//Member/library\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "  </eClassifiers>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Book\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" lowerBound=\"1\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"copies\" lowerBound=\"1\"\n"
			+ "        eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EBigInteger\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"library\" eType=\"#//Library\"\n"
			+ "        eOpposite=\"#//Library/books\"/>\n"
			+ "  </eClassifiers>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Member\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" lowerBound=\"1\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"library\" eType=\"#//Library\"\n"
			+ "        eOpposite=\"#//Library/members\"/>\n"
			+ "  </eClassifiers>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Loan\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"book\" lowerBound=\"1\" eType=\"#//Book\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"member\" lowerBound=\"1\"\n"
			+ "        eType=\"#//Member\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"date\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EDate\"/>\n"
			+ "  </eClassifiers>\n"
			+ "</ecore:EPackage>"; 
	
	private  static final String flexmi = 
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
	
	private  static final String ecore_mm_filerefs =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<ecore:EPackage xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
			+ "    xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" name=\"tutorial\" nsURI=\"http://www.eclipse.org/mdt/ocl/oclinecore/tutorial\"\n"
			+ "    nsPrefix=\"tut\">\n"
			+ "  <eAnnotations source=\"http://www.eclipse.org/OCL/Import\">\n"
			+ "    <details key=\"ecore\" value=\"http://www.eclipse.org/emf/2002/Ecore\"/>\n"
			+ "  </eAnnotations>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Library\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" lowerBound=\"1\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"books\" ordered=\"false\"\n"
			+ "        upperBound=\"-1\" eType=\"ecore:EClass ecore.ecore#//Book\" containment=\"true\"\n"
			+ "        eOpposite=\"ecore.ecore#//Book/library\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"loans\" ordered=\"false\"\n"
			+ "        upperBound=\"-1\" eType=\"ecore:EClass ecore.ecore#//Loan\" containment=\"true\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"members\" ordered=\"false\"\n"
			+ "        upperBound=\"-1\" eType=\"ecore:EClass ecore.ecore#//Member\" containment=\"true\"\n"
			+ "        eOpposite=\"ecore.ecore#//Member/library\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "  </eClassifiers>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Book\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" lowerBound=\"1\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"copies\" lowerBound=\"1\"\n"
			+ "        eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EBigInteger\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"library\" eType=\"ecore:EClass ecore.ecore#//Library\"\n"
			+ "        eOpposite=\"ecore.ecore#//Library/books\"/>\n"
			+ "  </eClassifiers>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Member\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" lowerBound=\"1\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"library\" eType=\"ecore:EClass ecore.ecore#//Library\"\n"
			+ "        eOpposite=\"ecore.ecore#//Library/members\"/>\n"
			+ "  </eClassifiers>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Loan\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"book\" lowerBound=\"1\" eType=\"ecore:EClass ecore.ecore#//Book\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"member\" lowerBound=\"1\"\n"
			+ "        eType=\"ecore:EClass ecore.ecore#//Member\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"date\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EDate\"/>\n"
			+ "  </eClassifiers>\n"
			+ "</ecore:EPackage>";
}