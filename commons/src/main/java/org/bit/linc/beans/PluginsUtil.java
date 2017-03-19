package org.bit.linc.beans;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.bit.linc.config.ApplicationProperties;
import org.bit.linc.config.DefaultConfiguration;
import org.bit.linc.exception.SysimpleException;

public class PluginsUtil {
	
	/**
	 * get PluginDir's absolute path
	 * @return
	 * @throws SysimpleException 
	 */
	public static String getPluginsDir() throws SysimpleException{
		Configuration config=ApplicationProperties.get();
		String pluginsDir=config.getString("sysimple.plugins.dir", DefaultConfiguration.SYSIMPLE_PLUGINS_DIR.getString());	
			if(pluginsDir==null||pluginsDir.equals("")){
			//log the pluginLocation is null or spaces
			}
		return pluginsDir;
	}
	
	/**
	 * get plugins' list
	 * @return
	 * @throws SysimpleException 
	 */
	public static List<String> getPluginsList() throws SysimpleException{		
		List<String> pluginsList=new ArrayList<String>();
		//get the array of files or dirs
		String [] files=new File(getPluginsDir()).list();
		//get the dirs with plugin(s) as suffix among array
		for(int i=0;i<files.length;i++){
			if(files[i].endsWith("plugins")||files[i].endsWith("plugin")){
				pluginsList.add(files[i]);
			}
		}
		return pluginsList;
	}
	
	/**
	 * get the list of shells in a plugin file 
	 * @param pluginName
	 * @return the list of shells in pluginName
	 * @throws SysimpleException
	 */
	public static List<String> getScriptsList(String pluginName) throws SysimpleException{
		List<String> scriptsList=new ArrayList<String>();
		File scripts=new File(getPluginsDir()+"/"+pluginName+"/scripts");
		if(!scripts.exists()){
			throw new SysimpleException("no script in "+pluginName);
		}
		String [] files=scripts.list();
		for(int i=0;i<files.length;i++){
			if(files[i].endsWith("sh")){
				scriptsList.add(files[i]);
			}
		}
		return scriptsList;
	}

	
}
