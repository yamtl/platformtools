package com.mdenetnetwork.ep.toolfunctions.eclipseocl;



import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.emfatic.core.EmfaticResource;
import org.eclipse.ocl.xtext.basecs.PackageCS;
import org.eclipse.ocl.xtext.completeocl.utilities.CompleteOCLCSResource;
import org.eclipse.ocl.xtext.oclinecore.utilities.OCLinEcoreCSResource;
import org.eclipse.ocl.xtext.oclinecorecs.TopLevelCS;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class MdeNetToolFunction implements HttpFunction { 
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {
		response.appendHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		
		if ("OPTIONS".equals(request.getMethod())) {
			response.appendHeader("Access-Control-Allow-Methods", "GET");
			response.appendHeader("Access-Control-Allow-Headers", "Content-Type");
			response.appendHeader("Access-Control-Max-Age", "3600");
			response.setStatusCode(HttpURLConnection.HTTP_NO_CONTENT);
			return;
		}
		else {
			JsonObject responseJson = new JsonObject();
			
			try {
				serviceImpl(getJsonObject(request), responseJson);
			}
			catch (Throwable t){
				responseJson.addProperty("output", t.getMessage());
				responseJson.addProperty("error", t.getMessage());
			}
			
			response.getWriter().write(responseJson.toString());
			
		}
	}
	
	public abstract void serviceImpl(JsonObject request, JsonObject response) throws Exception;
	
//	protected InMemoryEmfModel getInMemoryOclInEcore(String xmi, String oclinecore) throws Exception {
//		ResourceSet resourceSet = new ResourceSetImpl();
//		OCLinEcoreCSResource ePackage = getOclInEcoreResource(oclinecore);
//		
//		resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
//		Resource resource = resourceSet.createResource(URI.createURI("xmi.xmi"));
//		resource.load(new ByteArrayInputStream(xmi.getBytes()), null);
//
//		InMemoryEmfModel model = new InMemoryEmfModel(resource);
//		model.setName("M");
//		return model;
//	}
	
	//protected InMemoryEmfModel getBlankInMemoryModel(String emfatic) throws Exception {
	
	//protected InMemoryEmfModel getInMemoryEmfaticModel(String emfatic) throws Exception {
	
	protected PackageCS getOclInEcoreResource(ResourceSet rs, String oclinecore) throws Exception {
	
		if (oclinecore == null || oclinecore.trim().isEmpty()) { 
			return null;
		}
		
		
		OCLinEcoreCSResource oclinecoreResource = (OCLinEcoreCSResource) rs.createResource(URI.createURI("oclinecore.oclinecore")); 

		oclinecoreResource.load(new ByteArrayInputStream(oclinecore.getBytes()), null); 

		TopLevelCS csimp = (TopLevelCS) oclinecoreResource.getContents().get(0);
		PackageCS packcs = csimp.getOwnedPackages().get(0);
	

		if (oclinecoreResource.getParseResult().hasSyntaxErrors()) {
			throw new RuntimeException(oclinecoreResource.getParseResult().getSyntaxErrors().toString());
		}
			
		return packcs;
	}

	protected void getXmiResource(ResourceSet rs, String xmi) throws Exception {
		
		if (xmi == null || xmi.trim().isEmpty()) { 
			return;
		}
			
		XMIResource xmiResource = (XMIResource) rs.createResource(URI.createURI("xmi.xmi"));

		xmiResource.load(new ByteArrayInputStream(xmi.getBytes()), null); 
		
		if (!xmiResource.getErrors().isEmpty()) {
			throw new RuntimeException(xmiResource.getErrors().toString());
		}
		
		return;
	}
	
	protected void getOclCompleteResource(ResourceSet rs, String oclcomplete) throws Exception {
		
		if (oclcomplete == null || oclcomplete.trim().isEmpty()) { 
			return;
		}
			
		CompleteOCLCSResource oclcompleteResource = (CompleteOCLCSResource) rs.createResource(URI.createURI("ocl.ocl"));

		oclcompleteResource.load(new ByteArrayInputStream(oclcomplete.getBytes()), null); 
		
		if (!oclcompleteResource.getErrors().isEmpty()) {
			throw new RuntimeException(oclcompleteResource.getErrors().toString());
		}
		
		return;
	}
	
// TODO Move into wrapper function	
//	
//	protected void getFlexmiResource(ResourceSet rs, String flexmi) throws Exception  {
//		if (flexmi == null || flexmi.trim().isEmpty()) { 
//			return;
//		}
//			
//		/FlexmiResource flexmiResource = (FlexmiResource) rs.createResource(URI.createURI("flexmi.flexmi"));
//
//		/** Exception on trying to parse, internal casting in flexmi to epackage.
//		 *         org.eclipse.ocl.pivot.internal.resource.StandaloneProjectMap$EPackageDescriptor cannot be cast to class org.eclipse.emf.ecore.EPackage */ 
//		flexmiResource.load(new ByteArrayInputStream(flexmi.getBytes()), null); 
//		
//		if (!flexmiResource.getErrors().isEmpty()) {
//			throw new RuntimeException(flexmiResource.getErrors().toString());
//		}
//		
//		return;
//	}
	
	
	
	protected EPackage getEmfaticResource(ResourceSet rs, String emfatic) throws Exception {
		
		if (emfatic == null || emfatic.trim().isEmpty()) { 
			return null;
		}
		
		EmfaticResource emfaticResource = (EmfaticResource) rs.createResource(URI.createURI("emfatic.emf"));
		
		emfaticResource.load(new ByteArrayInputStream(emfatic.getBytes()), null); 
		
		if (emfaticResource.getParseContext().hasErrors()) {
			throw new RuntimeException(emfaticResource.getParseContext().getMessages()[0].getMessage());
		}
		
		EPackage epkg = (EPackage) emfaticResource.getContents().get(0);
		return epkg;
	}	
	
	
	
	protected EPackage getXmlResource(ResourceSet rs, String xml) throws Exception {
		
		if (xml == null || xml.trim().isEmpty()) { 
			return null;
		}
		
		
		XMLResource xmlResource = (XMLResource) rs.createResource(URI.createURI("ecore.ecore"));

		xmlResource.load(new ByteArrayInputStream(xml.getBytes()), null); 
		
		if (!xmlResource.getErrors().isEmpty()) {
			throw new RuntimeException(xmlResource.getErrors().toString());
		}
		
		EPackage epkg = (EPackage) xmlResource.getContents().get(0);
		return epkg;
	}	
	
	
	protected JsonObject getJsonObject(HttpRequest request) throws Exception {
		String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		return getJsonObject(json);
	}
	
	protected JsonObject getJsonObject(String json) {
		return JsonParser.parseString(json).getAsJsonObject();
	}
	
}