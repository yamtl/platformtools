package com.mdenetnetwork.ep.toolfunctions.emfatic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.emfatic.core.EmfaticResource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;

import com.mdenetnetwork.ep.toolfunctions.emf.EmfResourceLoader;
import org.eclipse.emf.emfatic.core.generator.ecore.EcoreGenerator;

import org.eclipse.emf.emfatic.core.generator.emfatic.Writer;

import com.google.gson.JsonObject;

public class EmfaticTool  { 
	
	public EmfaticTool() {
		
		// Register emf resource factories
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emfatic", new EmfaticResourceFactory());
	}

	
	public void convertEmfaticToEcore(String emfatic,  OutputStream outputStream, JsonObject response) throws Exception {
		
		/*-------------------------------------
		 *  Load Emfatic 
		 *-------------------------------------*/	
		ResourceSet resourceSet = new ResourceSetImpl();
			
		EPackage epkg = EmfResourceLoader.loadMetamodel( resourceSet , emfatic, "emfatic.emfatic", EmfaticResource.class);
		
		resourceSet.getPackageRegistry().put(epkg.getNsURI(), epkg ); // Register the metamodel uri
	
		
		/*-------------------------------------
		 *  Generate Ecore
		 *-------------------------------------*/
		//TODO
	}
	
	public void convertEcoreToEmfatic(String emfatic,  OutputStream outputStream, JsonObject response) throws Exception {
		
		/*-------------------------------------
		 *  Load Ecore 
		 *-------------------------------------*/	
		ResourceSet resourceSet = new ResourceSetImpl();
			
		EPackage epkg = EmfResourceLoader.loadMetamodel( resourceSet , emfatic, "ecore.ecore", XMIResource.class);
		
		resourceSet.getPackageRegistry().put(epkg.getNsURI(), epkg ); // Register the metamodel uri
	
		
		/*-------------------------------------
		 *  Generate Emfatic
		 *-------------------------------------*/
		Writer ecoreWriter = new Writer();
		String ecoreFile = ecoreWriter.write(resourceSet.getResources().get(0), null, null);
		
		response.addProperty("output", ecoreFile);
	}
	
	
	public void convertEmfaticToDiagram(String emfatic,  OutputStream outputStream, JsonObject response)  throws Exception {
		
	
		/*-------------------------------------
		 *  Create Diagram
		 *-------------------------------------*/
//		EglTemplateFactoryModuleAdapter module = new EglTemplateFactoryModuleAdapter();
//		module.parse(new File("src/main/resources/emfatic2plantuml.egl"));
//		module.getContext().getModelRepository().addModel(getInMemoryEmfaticModel(emfatic));
//		String plantUml = module.execute() + "";
//		
//		SourceStringReader reader = new SourceStringReader(plantUml);
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		reader.outputImage(os, new FileFormatOption(FileFormat.SVG));
//		os.close();
//
//		String(os.toByteArray(), Charset.forName("UTF-8"));
//		
//		response.addProperty("metamodelDiagram", plantuml);
	}
	
	
}
	