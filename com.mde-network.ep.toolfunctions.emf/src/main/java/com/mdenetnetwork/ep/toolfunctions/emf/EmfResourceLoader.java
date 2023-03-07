package com.mdenetnetwork.ep.toolfunctions.emf;

import java.io.ByteArrayInputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class EmfResourceLoader {

	
	public static <T extends Resource> EPackage loadMetamodel(ResourceSet rs, String resourceFileContents, String resourceFileName, Class<T> metamodelresource) throws Exception {
		
		if (resourceFileContents == null || resourceFileContents.trim().isEmpty()) { 
			return null;
		}
		
		Resource emfResource = (Resource) metamodelresource.cast( rs.createResource(URI.createURI(resourceFileName)) );

		emfResource.load(new ByteArrayInputStream(resourceFileContents.getBytes()), null); 
		
		if (!emfResource.getErrors().isEmpty()) {
			throw new RuntimeException(emfResource.getErrors().toString());
		}
		
		EPackage epkg = (EPackage) emfResource.getContents().get(0);
		return epkg;
	}
	
	
	public static <T extends Resource> void loadModel(ResourceSet rs, String resourceFileContents, String resourceFileName, Class<T> metamodelresource) throws Exception {
		
		if (resourceFileContents == null || resourceFileContents.trim().isEmpty()) { 
			return;
		}
			
		Resource emfResource = (Resource) metamodelresource.cast( rs.createResource(URI.createURI(resourceFileName)) );

		emfResource.load(new ByteArrayInputStream(resourceFileContents.getBytes()), null); 
		
		if (!emfResource.getErrors().isEmpty()) {
			throw new RuntimeException(emfResource.getErrors().toString());
		}
		
		return;
	}
}


