package org.bit.linc.plugins.scripts;

public class Script {
	
	private String name;
	private String path;
	
	public Script(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * @param path  Scripts's absolute path
	 * @param name Scripts's name
	 */
	public Script(String name,String path) {
		super();
		this.name = name;
		this.path = path;
	}
	/**
	 * get script path
	 * @return
	 */
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * get script name
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * run script
	 * @return
	 */
	public String ScriptRun(){
		return "";
	}
	
}
