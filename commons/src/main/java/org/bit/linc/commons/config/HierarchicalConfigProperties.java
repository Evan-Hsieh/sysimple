package org.bit.linc.commons.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.bit.linc.commons.exception.SysimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//This class implements the interface ConfigProperties
public class HierarchicalConfigProperties implements ConfigProperties{	
	
	private static final Logger logger = LoggerFactory.getLogger(HierarchicalConfigProperties.class);
	private static Map<String, String> propertiesMap=new HashMap<String,String>();
	
/*	Set properties which this project will use. 
	If some properties have no default value, set it as null.*/	
	public HierarchicalConfigProperties(){
		super();
		propertiesMap.put("sysimple.home", null);
		propertiesMap.put("sysimple.conf.dir", null);
		propertiesMap.put("sysimple.plugins.dir", null);
		propertiesMap.put("sysimple.webserver.port", "3000");
		propertiesMap.put("sysimple.webserver.minthreads", "10");
		propertiesMap.put("sysimple.webserver.maxthreads", "1000");
		propertiesMap.put("log4j.configuration", null);
			
	}	
	
	//init the variable SYSIMPLE_HOME to propertiesMap
	public boolean initSysimpleHome(){	
		String SYSIMPLE_HOME=System.getProperty("SYSIMPLE_HOME");
		
		if(null==SYSIMPLE_HOME){
			logger.error("The environment variable SYSIMPLE_HOME hava not been configured.");
			logger.error("Set the SYSIMPLE_HOME and try again!.");
			return false;
		}
		propertiesMap.put("sysimple.home", SYSIMPLE_HOME);
		return true;
	}
	
	/*	rule of setProperties: 
  	the temporary properties have highly priority,
	the properties that configured in the file have 2nd priority,
	the default properties have lowerd priority.*/
	public void setPropertiesByRule(){
		for (String key : propertiesMap.keySet()) {			
			if(null==System.getProperty(key)){
				Configuration config;
				try {
					config = ApplicationProperties.get();
					String propValue=config.getString(key, propertiesMap.get(key));	
					System.setProperty(key, propValue);
				} catch (SysimpleException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	//init all properties that will be used 
	public boolean initConfigProperties(){
		//if faild to init SYSIMPLE_HOME, return false
		if(false==initSysimpleHome()){
			return false;
		}		
		//set some properties according sysimple.home
		propertiesMap.put("sysimple.conf.dir", propertiesMap.get("sysimple.home")+"/conf");
		System.setProperty("sysimple.conf.dir", propertiesMap.get("sysimple.conf.dir"));
		propertiesMap.put("sysimple.plugins.dir", propertiesMap.get("sysimple.home")+"/plugins");
		propertiesMap.put("log4j.configuration", "file:"+propertiesMap.get("sysimple.home")+"/conf/sysimple-log4j.properties");		
						   		
		setPropertiesByRule();	
		return true;
	}
}
