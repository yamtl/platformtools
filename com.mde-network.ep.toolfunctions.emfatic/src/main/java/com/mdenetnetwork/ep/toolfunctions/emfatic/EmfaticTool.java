package com.mdenetnetwork.ep.toolfunctions.emfatic;

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
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;

import com.mdenetnetwork.ep.toolfunctions.emf.EmfResourceLoader;

import org.eclipse.emf.emfatic.core.generator.ecore.Builder;
import org.eclipse.emf.emfatic.core.generator.ecore.Connector;

import org.eclipse.emf.emfatic.core.generator.emfatic.Writer;
import org.eclipse.emf.emfatic.core.lang.gen.parser.EmfaticParserDriver;
import org.eclipse.gymnast.runtime.core.parser.ParseContext;

import com.google.gson.JsonObject;

public class EmfaticTool  { 
	
	public EmfaticTool() {
		
		// Register emf resource factories
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emfatic", new EmfaticResourceFactory());
	}

	
	public void convertEmfaticToEcore(String emfatic,  OutputStream outputStream, JsonObject response) throws Exception {
		
		final String emfaticFilename = "emfatic.emfatic";
		
		/*-------------------------------------
		 *  Load Emfatic 
		 *-------------------------------------*/	
		
		/* Loaded as per the emfatic generator implementation rather than the mde emf loader
		 * so that the ecore can be generated.
		 * 
		 * emfatic doesn't provide a generator that is not File based. 
		 */
		ResourceSet resourceSet = new ResourceSetImpl();

		Reader emfaticInput = new StringReader(emfatic);
		BufferedReader reader = new BufferedReader(emfaticInput);
		EmfaticParserDriver parser = new EmfaticParserDriver(URI.createURI(emfaticFilename));
		ParseContext parseContext = parser.parse(reader);
		String filePath = emfaticFilename.replaceAll("\\.emfatic$", ".ecore");
		
		//Resource resource = createResource(filePath, false);
		Resource resource = resourceSet.createResource( URI.createURI(filePath)  );

		
		Builder builder = new Builder();
		builder.build(parseContext, resource, null); // Monitor not actually used by called method


		/*-------------------------------------
		 *  Generate Ecore
		 *-------------------------------------*/		
		if (!parseContext.hasErrors()) {
			Connector connector = new Connector(builder);
			connector.connect(parseContext, resource, null); // Monitor not actually used by called method
			OutputStream ecoreStream = new ByteArrayOutputStream();
			resource.save(ecoreStream, null);
			
			response.addProperty("output", ecoreStream.toString() );
		}
		else {
			String message = parseContext.getMessages()[0].getMessage();
			message = message.replaceAll("\\r|\\n", " ");
			message = message.trim();
			message = "Syntax error: " + message;
			
			outputStream.write(message.getBytes());
			response.addProperty("output",  "" );
		}		
		
		
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
	