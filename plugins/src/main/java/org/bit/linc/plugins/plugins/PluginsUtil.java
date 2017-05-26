package org.bit.linc.plugins.plugins;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.bit.linc.commons.exception.SysimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginsUtil {
	private static Logger logger=LoggerFactory.getLogger(PluginsUtil.class);
	public static String getPluginsDir(){
		File file=new File(System.getProperty("sysimple.plugins.dir"));
		return file.getAbsolutePath();
	}
	
	public static String verifyPluginsDir(){
		if(new File(getPluginsDir()).listFiles()==null){
			return "non-existent";
		}
		if(new File(getPluginsDir()).listFiles().length==0){
			return "empty";
		}else{
			return "ocupied";
		}
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
		if(files == null){
			logger.info("The dir of plugins is empty. The null will be return when try to get plugin-list, ");
			return null;
		}
		//get the dirs with plugin(s) as suffix among array
		for(int i=0;i<files.length;i++){
			if(files[i].getName().endsWith("plugins")||files[i].getName().endsWith("plugin")){
				JAXBContext context;
				try {
					context = JAXBContext.newInstance(Plugin.class);
					Unmarshaller unMarshaller = context.createUnmarshaller();
					Plugin plugin=(Plugin)unMarshaller.unmarshal(new File(files[i].getAbsolutePath()+"/info.xml"));
					//If the plugin-name in the info.xml is different from the one in the file system, update the info.xml
					if(!plugin.getName().equals(files[i].getName())){
						plugin.setName(files[i].getName());
						plugin.updateInfoXml();
					}					
					pluginsList.add(plugin);
				} catch (JAXBException e) {
					e.printStackTrace();
					logger.error("something error in getting plugin from {}/info.xml",files[i].getAbsolutePath());
				}
			}
		}
		return pluginsList;
	}

}
