package com.mdenetnetwork.ep.toolfunctions.xtext;
 
/**
 * Data structure for 
 *
 */
public class ProjectFile {
	
	private String path;
	private String contents;
	
	public ProjectFile(String path, String contents) {
		this.path = path;
		this.contents = contents;
	}

	public String getPath() {
		return path;
	}
	
	public String getContents() {
		return contents;
	}
	
	@Override
	public String toString() {
		return "ProjectFile [path=" + path + ", contents=" + contents  + "]";
	}
	
}
