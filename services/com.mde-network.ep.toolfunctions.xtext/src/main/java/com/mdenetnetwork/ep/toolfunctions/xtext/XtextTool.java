package com.mdenetnetwork.ep.toolfunctions.xtext;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.gson.JsonObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.xtext.xtext.wizard.cli.CliProjectsCreatorMain;

public class XtextTool  { 

	static final String PROJECT_PATH = "./xtext-project/" ;
	static final String PROJECT_FILE_PATH = "./xtext-project.zip" ;
	static final int FILE_BUFFER_SIZE = 1024;
	
	public XtextTool() {
		
		// Register resource factories
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
	}


	public void run(String input, OutputStream outputStream, JsonObject response) throws Exception {
	
		String result = "";
		
		File projectFolder = new File(PROJECT_PATH);
		
		String[] args = new String[] {
				"-targetDir=" + projectFolder.getAbsolutePath(),
				"-languageName=org.xtext.example.mydsl.MyDsl",
				"-baseName=org.xtext.example.mydsl",
				"-extension=mydsl",
				"-enabledProjects=UIProject,GenericIDE,RuntimeTest,UITest,Web",
				"-buildSystem=MAVEN",
				"-sourceLayout=PLAIN",
				"-languageServer=FATJAR",
				"-xtextVersion=undefined",
				"-projectLayout=FLAT",
				"-javaVersion=JAVA10"
			};

		CliProjectsCreatorMain.main(args);
		
		 ZipOutputStream projectFile = new ZipOutputStream( new FileOutputStream(PROJECT_FILE_PATH) );
		
		 compressDirectory(projectFolder, "", projectFile);
		 
		 projectFile.flush();
		 projectFile.close();
		 
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
	

	private void compressDirectory(File dir, String parentDir, ZipOutputStream output )  
			throws FileNotFoundException, IOException {
		
		for (File file : dir.listFiles() ){
		
			if (file.isDirectory()) {
				
				if (file.listFiles().length == 0) {
					// Add empty folder directories
					String dirPath = file.getName() + "/";
					
					if (!parentDir.isEmpty()) {
						dirPath = parentDir + "/" + dirPath;
					}
					
					output.putNextEntry(new ZipEntry( dirPath ) );
					output.closeEntry();
				}
					
				compressDirectory(file, parentDir + "/" + file.getName(), output );
				
			} else {
				// Prepare to add file to the compressed stream
				output.putNextEntry(new ZipEntry(parentDir + "/" + file.getName()) );
				BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
				
				// Copy the file contents to the compressed stream
				long bytesRead = 0;
				byte[] bytesIn = new byte[FILE_BUFFER_SIZE];
				int read = 0;
				while((read = input.read(bytesIn)) != -1) {
					output.write(bytesIn, 0 , read);
					bytesRead += read;
				}
				output.closeEntry();
			}
		}
	}
	
		
}
	
