package org.bit.linc.plugins;

import java.util.List;

import org.bit.linc.Scripts.Script;
import org.bit.linc.Scripts.ScriptsUtil;
import org.bit.linc.exception.SysimpleException;

public class Plugin {
	private String path;//路径
	private String name;//插件名
	/**
	 * @param path plugin's absolutePath
	 * @param name plugin's name
	 */
	public Plugin(String path, String name) {
		super();
		this.path = path;
		this.name = name;
	}
	/**
	 * get plugin's absolute path
	 * @return
	 */
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * get plugin's name
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get Scripts in this plugin
	 * @return
	 * @throws SysimpleException
	 */
	public List<Script> getScripts() throws SysimpleException {
		return ScriptsUtil.getScriptList(name);
	}
	/**
	 * get Scripts' names in this plugin
	 * @return
	 * @throws SysimpleException
	 */
	public List<String> getScriptNames() throws SysimpleException {
		return ScriptsUtil.getScriptNameList(name);
	}
}
