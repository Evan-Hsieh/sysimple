package org.bit.linc.plugins.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.ExResult;
import org.bit.linc.commons.utils.FileUtil;
import org.bit.linc.plugins.scripts.ScriptsUtil;

import com.google.gson.Gson;

public class PluginsUtil {
	
	public static String getPluginsDir(){
		return System.getProperty("sysimple.plugins.dir");
	}
	
	
	/**
	 * get plugins' list
	 * @return
	 * @throws SysimpleException 
	 */
	public static ArrayList<Plugin> getPluginList() throws SysimpleException{		
		ArrayList<Plugin> pluginsList=new ArrayList<Plugin>();
		//get the array of files or dirs
		String pluginPath=getPluginsDir();
		File [] files=new File(pluginPath).listFiles();
		//get the dirs with plugin(s) as suffix among array
		for(int i=0;i<files.length;i++){
			if(files[i].getName().endsWith("plugins")||files[i].getName().endsWith("plugin")){
				ExResult result=FileUtil.ReadFileByLine(files[i].getAbsolutePath()+"/info.json");
				Plugin plugin=new Plugin(files[i].getName());
				if(result.code==0){
					String content=result.message;
					Gson gson=new Gson();
					Plugin pluginTemp=gson.fromJson(content.trim(), Plugin.class);
					plugin.setInfo(pluginTemp.getInfo());
					plugin.setDetail(pluginTemp.getDetail());
				}
				plugin.setScriptsList(ScriptsUtil.getScriptList(files[i].getName()));
				pluginsList.add(plugin);
			}
		}
		return pluginsList;
	}
	
	/**
	 * get pluginNames' list
	 * @return
	 * @throws SysimpleException
	 */
	public static List<String> getPluginNameList() throws SysimpleException{
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
	

}
