package com.mdenetnetwork.ep.toolfunctions.emfatic;

import java.io.OutputStream;
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

import com.google.gson.JsonObject;

public class EmfaticTool  { 
	
	public EmfaticTool() {
		
		// Register emf resource factories
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
	
	public void convertEmfaticToDiagram(String emfatic,  OutputStream outputStream, JsonObject response)  throws Exception {
		
		/*-------------------------------------
		 *  Load Emfatic 
		 *-------------------------------------*/
		ResourceSet resourceSet = new ResourceSetImpl();
		
		EPackage epkg = EmfResourceLoader.loadMetamodel( resourceSet , emfatic, "emfatic.emfatic", EmfaticResource.class);
		
		resourceSet.getPackageRegistry().put(epkg.getNsURI(), epkg ); // Register the metamodel uri
		
		
		/*-------------------------------------
		 *  Create Diagram
		 *-------------------------------------*/
		//TODO
	}
	
	
	
}
	