package com.mdenetwork.ep.toolfunctions.epsilon;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.emfatic.core.EmfaticResource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import com.google.gson.JsonObject;
import com.mdenetnetwork.ep.toolfunctions.emf.EmfResourceLoader;




public class EpsilonTool  { 
	
	public EpsilonTool() {
		
		// Register resource factories
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emfatic", new EmfaticResourceFactory());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
	}

	
	public void convertFlexmiToXmi(String flexmi, String emfatic,  OutputStream outputStream, JsonObject response) throws Exception {
		
		final String emfaticFilename = "emfatic.emfatic";
		
		/*-------------------------------------
		 *  Load Metamodel 
		 *-------------------------------------*/
		ResourceSet resourceSet = new ResourceSetImpl();
		
		EPackage epkg = EmfResourceLoader.loadMetamodel( resourceSet, emfatic, emfaticFilename, EmfaticResource.class);
		
		resourceSet.getPackageRegistry().put(epkg.getNsURI(), epkg ); // Register the metamodel uri
		
		
		/*-------------------------------------
		 *  Load Flexmi 
		 *-------------------------------------*/	
		 
		EmfResourceLoader.loadModel(resourceSet, flexmi, "flexmi.flexmi", FlexmiResource.class );
		
		//Resource flexmiResource =  resourceSet.getResource(URI.createURI("flexmi.flexmi"), false); // TODO support  multiple resources
		Resource flexmiResource =  resourceSet.getResources().get(1); // TODO support  multiple resources

		/*-------------------------------------
		 *  Generate XMI
		 *-------------------------------------*/	
		 
		 
		 ResourceSet xmiResourceSet = new ResourceSetImpl();
		 
		 // Create resource in the target format
		 Resource xmiResource =  xmiResourceSet.createResource(URI.createURI("xmi.xmi"));
		
		 // Copy the source into the new target resource
		 xmiResource.getContents().addAll( EcoreUtil.copyAll(flexmiResource.getContents()) );
		 
		 // Serialise the target resource contents to a stream
		 OutputStream ecoreStream = new ByteArrayOutputStream();
		 xmiResource.save(ecoreStream, null);
		
		 response.addProperty("output", ecoreStream.toString() );
	}
	
}
	
