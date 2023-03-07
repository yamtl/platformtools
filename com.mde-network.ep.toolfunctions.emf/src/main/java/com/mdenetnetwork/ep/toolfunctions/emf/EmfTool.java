package main.java.com.mdenetnetwork.ep.toolfunctions.emf;




import java.io.OutputStream;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.google.gson.JsonObject;


public class EmfTool  { 

	
	public void ConvertEcoreToDiagram(String ecore,  OutputStream outputStream, JsonObject response) throws Exception {
		
		/*-------------------------------------
		 *  Load Ecore 
		 *-------------------------------------*/
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		
	
		
		ResourceSet rs = new ResourceSetImpl();
		
		ResourceSet resourceSet = new ResourceSetImpl();
		
	
		
		EPackage epkg = EmfResourceLoader.loadMetamodel( resourceSet , ecore, "ecore.ecore", XMIResource.class);
		
		resourceSet.getPackageRegistry().put(epkg.getNsURI(), epkg ); // Register the metamodel uri
		
		/*-------------------------------------
		 *  Create Diagram
		 *-------------------------------------*/
		//TODO
	}
	
	public void ConvertXmiToDiagram(String xmi,  OutputStream outputStream, JsonObject response)  throws Exception {
		
		/*-------------------------------------
		 *  Load Xmi 
		 *-------------------------------------*/
		
		ResourceSet resourceSet = new ResourceSetImpl();
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		
		EmfResourceLoader.loadModel(resourceSet, xmi, "xmi.xmi", XMLResource.class );
		
		
		/*-------------------------------------
		 *  Create Diagram
		 *-------------------------------------*/
		//TODO
	}
	
	
	
}
	