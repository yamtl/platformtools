package com.mdenetnetwork.ep.toolfunctions.${toolPackageName};

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import com.mdenetnetwork.ep.toolfunctions.emf.EmfResourceLoader;

import com.google.gson.JsonObject;

public class ${toolName}Tool  { 
	
	public ${toolName}Tool() {
		
		// Register resource factories
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
	}


	public void run(String ecore, String oclcomplete, String xmi, OutputStream outputStream, JsonObject response) throws Exception {
	
		String result = "";

		/*-------------------------------------
		 *  Metamodel 
		 *-------------------------------------*/
		
		// TODO load metamodel


		/*-------------------------------------
		 *  Model 
		 *-------------------------------------*/

		// TODO load model

	
		/*-------------------------------------
		 *  OCL 
		 *-------------------------------------*/	
		
		 // TODO run tool function


		 outputStream.write(result.getBytes());
	}

	
	public void convertXToY(String X,  OutputStream outputStream, JsonObject response) throws Exception {
		
		final String emfaticFilename = "emfatic.emfatic";

		String generatedY = "";
		
		/*-------------------------------------
		 *  Load X 
		 *-------------------------------------*/	
		
	

		/*-------------------------------------
		 *  Generate Y
		 *-------------------------------------*/		

		
		 response.addProperty("output",  generatedY );
	}
	
}
	
