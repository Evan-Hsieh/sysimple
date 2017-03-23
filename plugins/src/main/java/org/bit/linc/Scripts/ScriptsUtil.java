package org.bit.linc.scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bit.linc.exception.SysimpleException;
import org.bit.linc.plugins.PluginsUtil;

public class ScriptsUtil {

	/**
	 * get the list of shellName in a plugin file 
	 * @param pluginName
	 * @return the list of shells in pluginName
	 * @throws SysimpleException
	 */
	public static List<String> getScriptNameList(String pluginName) throws SysimpleException{
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
	
	public static List<Script> getScriptList(String pluginName) throws SysimpleException{
		List<Script> scriptsList=new ArrayList<Script>();
		String scriptPath=PluginsUtil.getPluginsDir()+"/"+pluginName+"/scripts";
		File scripts=new File(scriptPath);
		if(!scripts.exists()){
			throw new SysimpleException("no script file in "+pluginName);
		}
		String [] files=scripts.list();
		if(files.length==0){
			throw new SysimpleException("have no script in "+pluginName+"/scripts");
		}
		for(int i=0;i<files.length;i++){
			if(files[i].endsWith("sh")){
				Script script=new Script(scriptPath+"/"+files[i], files[i]);
				scriptsList.add(script);
			}
		}
		return scriptsList;
	}
}
