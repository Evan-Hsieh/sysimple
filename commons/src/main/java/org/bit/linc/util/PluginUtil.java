package org.bit.linc.util;

import java.io.File;

import java.util.ArrayList;
import java.util.List;


import org.bit.linc.SysimpleException;

public class PluginUtil {

	/**
	 * get PluginDir's absolute path
	 * @return
	 */
	public static String pluginDir(){
		 String pluginLocation = System.getProperty("sysimple.plugins");
		 if(pluginLocation==null||pluginLocation.equals("")){
			 //log the pluginLocation is null or spaces
		 }
	     return pluginLocation;
	}
	
	/**
	 * get plugins' list
	 * @return
	 */
	public static List<String> pluginList(){
		List<String> fileList=new ArrayList<String>();
		File plugins=new File(pluginDir());
		String [] files=plugins.list();
		for(int i=0;i<files.length;i++){
			if(files[i].endsWith("plugins")||files[i].endsWith("plugin")){
				fileList.add(files[i]);
			}
		}
		return fileList;
	}
	
	/**
	 * get the list of shells in a plugin file 
	 * @param pluginName
	 * @return the list of shells in pluginName
	 * @throws SysimpleException
	 */
	public static List<String> shellList(String pluginName) throws SysimpleException{
		List<String> shellList=new ArrayList<String>();
		File shells=new File(pluginDir()+"/"+pluginName+"/scripts");
		if(!shells.exists()){
			throw new SysimpleException("no shell in "+pluginName);
		}
		String [] files=shells.list();
		for(int i=0;i<files.length;i++){
			if(files[i].endsWith("sh")){
				shellList.add(files[i]);
			}
		}
		return shellList;
	}

	
}
