package org.bit.linc.plugins.scripts;

public class Script {
	private String path;
	private String name;
	/**
	 * @param path  Scripts's absolute path
	 * @param name Scripts's name
	 */
	public Script(String path, String name) {
		super();
		this.path = path;
		this.name = name;
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
