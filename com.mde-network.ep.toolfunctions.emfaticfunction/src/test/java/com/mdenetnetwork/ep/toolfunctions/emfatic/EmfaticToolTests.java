package com.mdenetnetwork.ep.toolfunctions.emfatic;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
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
 	
	@Test
	void convertEcoreToEMfatic() throws Exception{
		JsonObject response = new JsonObject();
		 ByteArrayOutputStream outstream = new ByteArrayOutputStream();
				
		 new EmfaticTool().convertEcoreToEmfatic(ecore_mm, outstream, response);
		
		 String generatedEmfatic = response.get("output").getAsString();
		 
		 assertEquals(expectedEmfatic, generatedEmfatic);
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
	
	private  static final String expectedEmfatic =
	"@\"http://www.eclipse.org/OCL/Import\"(ecore=\"http://www.eclipse.org/emf/2002/Ecore\")\n"
	+ "@Ecore(invocationDelegates=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\", settingDelegates=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\", validationDelegates=\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\")\n"
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
	+ "@Ecore(constraints=\"SufficientCopies\")\n"
	+ "@\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"(SufficientCopies=\"\n"
	+ " 		library.loans->select(book=self)->size() <= copies\")\n"
	+ "class Book {\n"
	+ "\n"
	+ "	@\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"(body=\"loans->size() < copies\")\n"
	+ "	op Boolean isAvailable();\n"
	+ "	attr String[1] name;\n"
	+ "	attr EBigInteger[1] copies;\n"
	+ "	ref Library#books library;\n"
	+ "\n"
	+ "	@\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"(derivation=\"library.loans->select(book=self)\")\n"
	+ "	@\"http://www.eclipse.org/OCL/Collection\"(nullFree=\"false\")\n"
	+ "	!ordered volatile derived ref Loan[*] loans;\n"
	+ "}\n"
	+ "\n"
	+ "@Ecore(constraints=\"AtMostTwoLoans UniqueLoans\")\n"
	+ "@\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"(AtMostTwoLoans=\"\n"
	+ "  		loans->size() <= 2\", UniqueLoans=\"\n"
	+ "  		loans->isUnique(book)\")\n"
	+ "class Member {\n"
	+ "	attr String[1] name;\n"
	+ "	ref Library#members library;\n"
	+ "\n"
	+ "	@\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"(derivation=\"library.loans->select(member=self)\")\n"
	+ "	@\"http://www.eclipse.org/OCL/Collection\"(nullFree=\"false\")\n"
	+ "	!ordered volatile derived ref Loan[*] loans;\n"
	+ "\n"
	+ "	@\"http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot\"(derivation=\"loans->collect(book)\")\n"
	+ "	@\"http://www.eclipse.org/OCL/Collection\"(nullFree=\"false\")\n"
	+ "	!unique !ordered volatile derived ref Book[*] books;\n"
	+ "}\n"
	+ "\n"
	+ "class Loan {\n"
	+ "	ref Book[1] book;\n"
	+ "	ref Member[1] member;\n"
	+ "	attr EDate date;\n"
	+ "}\n"
	+ "\n"
	+ "";
	
}