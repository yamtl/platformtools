package com.mdenetnetwork.ep.toolfunctions.emf;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import main.java.com.mdenetnetwork.ep.toolfunctions.emf.EmfTool;


public class EmfToolTests {
	
	@Test
	void convertXmiToDiagram() throws Exception{
		JsonObject response = new JsonObject();
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		 
		 
		//new EmfTool().ConvertXmiToDiagram( xmi, outstream, response );
		//TODO Need to load the metamodel
	}
	
	@Test
	void convertEcoreToDiagram() throws Exception{
		JsonObject response = new JsonObject();
		 ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			
		 new EmfTool().ConvertEcoreToDiagram(ecore_mm, outstream, response);
		 
		 // TODO Check for image
	}
	
	private  static final String ecore_mm =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<ecore:EPackage xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
			+ "    xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" name=\"tutorial\" nsURI=\"http://www.eclipse.org/mdt/ocl/oclinecore/tutorial\"\n"
			+ "    nsPrefix=\"tut\">\n"
			+ "  <eAnnotations source=\"http://www.eclipse.org/OCL/Import\">\n"
			+ "    <details key=\"ecore\" value=\"http://www.eclipse.org/emf/2002/Ecore\"/>\n"
			+ "  </eAnnotations>\n"
			+ "  <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore\">\n"
			+ "    <details key=\"invocationDelegates\" value=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"/>\n"
			+ "    <details key=\"settingDelegates\" value=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"/>\n"
			+ "    <details key=\"validationDelegates\" value=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"/>\n"
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
			+ "    <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore\">\n"
			+ "      <details key=\"constraints\" value=\"SufficientCopies\"/>\n"
			+ "    </eAnnotations>\n"
			+ "    <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\">\n"
			+ "      <details key=\"SufficientCopies\" value=\"&#xA; &#x9;&#x9;library.loans->select(book=self)->size() &lt;= copies\"/>\n"
			+ "    </eAnnotations>\n"
			+ "    <eOperations name=\"isAvailable\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EBooleanObject\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\">\n"
			+ "        <details key=\"body\" value=\"loans->size() &lt; copies\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eOperations>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" lowerBound=\"1\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"copies\" lowerBound=\"1\"\n"
			+ "        eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EBigInteger\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"library\" eType=\"#//Library\"\n"
			+ "        eOpposite=\"#//Library/books\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"loans\" ordered=\"false\"\n"
			+ "        upperBound=\"-1\" eType=\"#//Loan\" volatile=\"true\" derived=\"true\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\">\n"
			+ "        <details key=\"derivation\" value=\"library.loans->select(book=self)\"/>\n"
			+ "      </eAnnotations>\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "  </eClassifiers>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Member\">\n"
			+ "    <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore\">\n"
			+ "      <details key=\"constraints\" value=\"AtMostTwoLoans UniqueLoans\"/>\n"
			+ "    </eAnnotations>\n"
			+ "    <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\">\n"
			+ "      <details key=\"AtMostTwoLoans\" value=\"&#xA;  &#x9;&#x9;loans->size() &lt;= 2\"/>\n"
			+ "      <details key=\"UniqueLoans\" value=\"&#xA;  &#x9;&#x9;loans->isUnique(book)\"/>\n"
			+ "    </eAnnotations>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" lowerBound=\"1\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"library\" eType=\"#//Library\"\n"
			+ "        eOpposite=\"#//Library/members\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"loans\" ordered=\"false\"\n"
			+ "        upperBound=\"-1\" eType=\"#//Loan\" volatile=\"true\" derived=\"true\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\">\n"
			+ "        <details key=\"derivation\" value=\"library.loans->select(member=self)\"/>\n"
			+ "      </eAnnotations>\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"books\" ordered=\"false\"\n"
			+ "        unique=\"false\" upperBound=\"-1\" eType=\"#//Book\" volatile=\"true\" derived=\"true\">\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\">\n"
			+ "        <details key=\"derivation\" value=\"loans->collect(book)\"/>\n"
			+ "      </eAnnotations>\n"
			+ "      <eAnnotations source=\"http://www.eclipse.org/OCL/Collection\">\n"
			+ "        <details key=\"nullFree\" value=\"false\"/>\n"
			+ "      </eAnnotations>\n"
			+ "    </eStructuralFeatures>\n"
			+ "  </eClassifiers>\n"
			+ "  <eClassifiers xsi:type=\"ecore:EClass\" name=\"Loan\">\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"book\" lowerBound=\"1\" eType=\"#//Book\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EReference\" name=\"member\" lowerBound=\"1\"\n"
			+ "        eType=\"#//Member\"/>\n"
			+ "    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"date\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EDate\"/>\n"
			+ "  </eClassifiers>\n"
			+ "</ecore:EPackage>";
 	
	public static final String xmi = "<?xml version=\"1.0\" encoding=\"ASCII\"?>\n"
			+ "<tut:Library xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:tut=\"http://www.eclipse.org/mdt/ocl/oclinecore/tutorial\" xmi:id=\"_u-FPsLN-Ee2Voc5l4vy_ww\" name=\"lib\">\n"
			+ "  <books xmi:id=\"_u-FPsbN-Ee2Voc5l4vy_ww\" name=\"b1\" copies=\"1\"/>\n"
			+ "  <books xmi:id=\"_u-FPsrN-Ee2Voc5l4vy_ww\" name=\"b2\" copies=\"2\"/>\n"
			+ "  <books xmi:id=\"_u-FPs7N-Ee2Voc5l4vy_ww\" name=\"b3\" copies=\"3\"/>\n"
			+ "  <loans xmi:id=\"_u-FPtLN-Ee2Voc5l4vy_ww\" book=\"_u-FPsbN-Ee2Voc5l4vy_ww\" member=\"_u-FPurN-Ee2Voc5l4vy_ww\"/>\n"
			+ "  <loans xmi:id=\"_u-FPtbN-Ee2Voc5l4vy_ww\" book=\"_u-FPsbN-Ee2Voc5l4vy_ww\" member=\"_u-FPurN-Ee2Voc5l4vy_ww\"/>\n"
			+ "  <loans xmi:id=\"_u-FPtrN-Ee2Voc5l4vy_ww\" book=\"_u-FPsrN-Ee2Voc5l4vy_ww\" member=\"_u-FPubN-Ee2Voc5l4vy_ww\"/>\n"
			+ "  <loans xmi:id=\"_u-FPt7N-Ee2Voc5l4vy_ww\" book=\"_u-FPs7N-Ee2Voc5l4vy_ww\" member=\"_u-FPuLN-Ee2Voc5l4vy_ww\"/>\n"
			+ "  <members xmi:id=\"_u-FPuLN-Ee2Voc5l4vy_ww\" name=\"m1\"/>\n"
			+ "  <members xmi:id=\"_u-FPubN-Ee2Voc5l4vy_ww\" name=\"m2\"/>\n"
			+ "  <members xmi:id=\"_u-FPurN-Ee2Voc5l4vy_ww\" name=\"m3\"/>\n"
			+ "</tut:Library>";
	
}