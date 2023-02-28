package com.mdenetnetwork.ep.toolfunctions.eclipseocl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;

import org.eclipse.ocl.pivot.internal.labels.LabelSubstitutionLabelProvider;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.utilities.PivotUtil;
import org.eclipse.ocl.pivot.validation.ComposedEValidator;
import org.eclipse.ocl.xtext.completeocl.CompleteOCLStandaloneSetup;
import org.eclipse.ocl.xtext.completeocl.validation.CompleteOCLEObjectValidator;
import org.eclipse.ocl.xtext.oclinecore.OCLinEcoreStandaloneSetup;
import org.eclipse.ocl.xtext.oclinecore.validation.OCLinEcoreEObjectValidator;
import org.eclipse.ocl.xtext.oclstdlib.OCLstdlibStandaloneSetup;


import com.google.gson.JsonObject;

public class RunEclipseOclFunction extends  MdeNetToolFunction{
	private OCL ocl = null;
	
	
	public RunEclipseOclFunction () {
		ocl = OCL.newInstance(OCL.CLASS_PATH);
		
		CompleteOCLStandaloneSetup.doSetup(); 
		//EssentialOCLStandaloneSetup.doSetup();
		OCLinEcoreStandaloneSetup.doSetup();
		OCLstdlibStandaloneSetup.doSetup();
		EValidator.Registry.INSTANCE.put(null, new OCLinEcoreEObjectValidator());
		
		
	}
	
	
	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		run(	request.get("emfatic").getAsString() ,
				request.get("oclcomplete").getAsString(), 
				request.get("xmi").getAsString(), 
				bos, response);
		response.addProperty("output", bos.toString());
	}

	public void run(String ecore, String oclcomplete, String xmi, OutputStream outputStream, JsonObject response) throws Exception {
				
		//ResourceSet resourceSet = new XtextResourceSet();
		ResourceSet resourceSet = ocl.getResourceSet();
		
		// Register factories
		//resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());
		
		
		/*-------------------------------------
		 *  Metamodel 
		 *-------------------------------------*/
		EPackage epkg =  getEmfaticResource(resourceSet, ecore);
		
		resourceSet.getPackageRegistry().put(epkg.getNsURI(), epkg ); // Register the metamodel uri
		
		
		// Parse the oclinecore and add to resources
		//PackageCS pkg = getOclInEcoreResource(resourceSet, oclcomplete);
		
		//resourceSet.getPackageRegistry().put(pkg.getNsURI(), pkg.eClass().getEPackage() ); // Register the metamodel uri
		// Registering packages only works with EPackage
		
		//EPackage.Registry reg  =  resourceSet.getPackageRegistry();
		
	
		/*-------------------------------------
		 *  Model 
		 *-------------------------------------*/
		
		// Parse the model and add to resources
		getXmiResource(resourceSet, xmi);
		
		//getFlexmiResource(resourceSet, xmi); // Exception with flexmi
		
		
		/*-------------------------------------
		 *  OCL 
		 *-------------------------------------*/
		
		getOclCompleteResource(resourceSet, oclcomplete);
		
		
		
		// Check properties 
		
		ComposedEValidator newEValidator = ComposedEValidator.install(epkg);
		newEValidator.addChild(new CompleteOCLEObjectValidator(
											epkg, URI.createURI("result"), ocl.getEnvironmentFactory() )
		);
		
		
		// Get the model resource
		XMIResource modelResource = (XMIResource) resourceSet.getResources().stream().filter(res -> XMIResource.class.isInstance(res)).findFirst().get();
		
		MyDiagnostician diagnostician = new MyDiagnostician();
		Diagnostic diagnostics = diagnostician.validate(modelResource);
		
		String formattedDiagnostics;
				
		// Print the diagnostics
		if (diagnostics.getSeverity() != Diagnostic.OK) {
			formattedDiagnostics = PivotUtil.formatDiagnostics(diagnostics, "\n");
	    } else {
	    	formattedDiagnostics = "Validation completed.";
	    }

		
		
		PrintWriter pwriter = new PrintWriter(outputStream) ;
		pwriter.print(formattedDiagnostics);
		response.addProperty("validationResult", formattedDiagnostics);
		
	}
	
	
	public class MyDiagnostician extends Diagnostician
	{
		@Override
		public Map<Object, Object> createDefaultContext() {
			Map<Object, Object> context = super.createDefaultContext();
			context.put(EValidator.SubstitutionLabelProvider.class,
				new LabelSubstitutionLabelProvider());
			return context;
		}

		public BasicDiagnostic createDefaultDiagnostic(Resource resource) {
			return new BasicDiagnostic(EObjectValidator.DIAGNOSTIC_SOURCE, 0,
				EMFEditUIPlugin.INSTANCE.getString(
					"_UI_DiagnosisOfNObjects_message", new String[]{"1"}),
				new Object[]{resource});
		}

		public Diagnostic validate(Resource resource) {
			BasicDiagnostic diagnostics = createDefaultDiagnostic(resource);
			Map<Object, Object> context = createDefaultContext();
			for (EObject eObject : resource.getContents()) {
				validate(eObject, diagnostics, context);
			}
			return diagnostics;
		}
	}
	
	
//	protected void runOclInEcoreValidate(EolModule module, String oclinecore, String xmi) throws Exception {
//		InMemoryEmfModel model = getInMemoryFlexmiModel(flexmi, emfatic);
//		model.setName("M");
//		module.getContext().getModelRepository().addModel(model);
//		module.execute();
//	}
//	
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().addAppender(new ConsoleAppender(new SimpleLayout(), ConsoleAppender.SYSTEM_OUT));
		
		JsonObject response = new JsonObject();
		
		new RunEclipseOclFunction().run(
				emfatic_mm, 
				oclfile,
				xmi, 
				System.out, response);
		
		System.out.println(response.get("validationResult"));
		
		//new RunEpsilonFunction().getEPackage("package foo");
		
		/*
		new RunEpsilonFunction().run("", "",
				"<?nsuri http://www.eclipse.org/emf/2002/Ecore?>\n<package/>", "package tree; class Tree{}",
				"<?nsuri http://www.eclipse.org/emf/2002/Ecore?>\n<package/>", "package tree; class Tree{}",
				System.out, new JsonObject());*/
	}
	
	
	private  static String ecore_mm =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
	
	private static String emfatic_mm = "@\"http://www.eclipse.org/OCL/Import\"(ecore=\"http://www.eclipse.org/emf/2002/Ecore\")\n"
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
	
	public static String oclinecore = "import ecore : 'http://www.eclipse.org/emf/2002/Ecore';\n"
					+ "\n"
					+ "package tutorial : tut = 'http://www.eclipse.org/mdt/ocl/oclinecore/tutorial'\n"
					+ "{\n"
					+ "	class Library\n"
					+ "	{\n"
					+ "		attribute name : String[1];\n"
					+ "		property books#library : Book[*] { composes };\n"
					+ "		property loans : Loan[*] { composes };\n"
					+ "		property members#library : Member[*] { composes };\n"
					+ "	}\n"
					+ "	class Book\n"
					+ "	{\n"
					+ "		operation isAvailable() : Boolean[?]\n"
					+ "		{\n"
					+ "			body: loans->size() < copies;\n"
					+ "		}\n"
					+ "		attribute name : String[1];\n"
					+ "		attribute copies : Integer[1];\n"
					+ "		property library#books : Library[?];\n"
					+ "		property loans : Loan[*] { derived volatile }\n"
					+ "		{\n"
					+ "			initial: library.loans->select(book=self);\n"
					+ "		}\n"
					+ "		invariant SufficientCopies: \n"
					+ " 		library.loans->select(book=self)->size() <= copies;\n"
					+ "	}\n"
					+ "	class Member\n"
					+ "	{\n"
					+ "		attribute name : String[1];\n"
					+ "		property library#members : Library[?];\n"
					+ "		property loans : Loan[*] { derived volatile }\n"
					+ "		{\n"
					+ "			initial: library.loans->select(member=self);\n"
					+ "		}\n"
					+ "		property books : Book[*] { !unique derived volatile }\n"
					+ "		{\n"
					+ "			initial: loans->collect(book);\n"
					+ "		}\n"
					+ "		invariant AtMostTwoLoans: \n"
					+ "  		loans->size() <= 2;\n"
					+ "		invariant UniqueLoans: \n"
					+ "  		loans->isUnique(book);\n"
					+ "	}\n"
					+ "	class Loan\n"
					+ "	{\n"
					+ "		property book : Book[1];\n"
					+ "		property member : Member[1];\n"
					+ "		attribute date : ecore::EDate[?];\n"
					+ "	}\n"
					+ "}"; 
	
	public static String xmi = "<?xml version=\"1.0\" encoding=\"ASCII\"?>\n"
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
	
	public static String flexmi = "<?nsuri http://www.eclipse.org/mdt/ocl/oclinecore/tutorial?>\n"
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
	
					
	public static String oclfile = " import 'http://www.eclipse.org/mdt/ocl/oclinecore/tutorial'\n"
			+ " \n"
			+ " package tutorial\n"
			+ " \n"
			+ " context Book\n"
			+ " \n"
			+ " inv SufficientCopies : library.loans->select(book=self)->size() <= copies\n"
			+ " \n"
			+ " \n"
			+ " \n"
			+ " endpackage \n"
			+ "";
}
