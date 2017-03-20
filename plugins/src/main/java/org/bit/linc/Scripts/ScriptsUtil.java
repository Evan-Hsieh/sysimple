package org.bit.linc.Scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bit.linc.exception.SysimpleException;
import org.bit.linc.plugins.PluginsUtil;

public class ScriptsUtil {

	/**
	 * get the list of shells in a plugin file 
	 * @param pluginName
	 * @return the list of shells in pluginName
	 * @throws SysimpleException
	 */
	public static List<String> getScriptsList(String pluginName) throws SysimpleException{
		List<String> scriptsList=new ArrayList<String>();
		File scripts=new File(PluginsUtil.getPluginsDir()+"/"+pluginName+"/scripts");
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
