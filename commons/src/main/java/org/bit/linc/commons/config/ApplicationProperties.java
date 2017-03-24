package org.bit.linc.commons.config;

import java.io.File;
import java.net.URL;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bit.linc.commons.exception.SysimpleException;

/**
 * 
 * @ClassName: ApplicationProperties 
 * @author Evan dreamcoding@outlook.com 
 * @date Mar 13, 2017 5:32:38 PM 
 *
 */
public class ApplicationProperties extends PropertiesConfiguration{
	public static String PROPERTIES_FILE_NAME="sysimple.properties";
	private static volatile Configuration instance = null;
	
	/**
	 * Construction method
	 * @param:
	 * @throws
	 */
	public ApplicationProperties(URL url) throws ConfigurationException{
		super(url);
	}
	
	/**
	 * Get the instance of Configuration according to the PROPERTIES_FILE_NAME.
	 * This method will call the overloading method: get(String fileName)
	 * @Name: get 
	 * @param: none
	 * @return: Configuration: a instance of Class Configuration  
	 * @throws:
	 */
	public static Configuration get() throws SysimpleException{
        if (instance == null) {
            synchronized (ApplicationProperties.class) {
                if (instance == null) {
                    instance = get(PROPERTIES_FILE_NAME);
                }
            }
        }
        return instance;
    }

	/**
	 * Get the instance of Configuration according to the fileName
	 * Overloading method: get()
	 * @Name: get 
	 * @param: fileName
	 * @return: Configuration   
	 * @throws:
	 */
    public static Configuration get(String fileName) throws SysimpleException{
    	String confDir = System.getProperty("sysimple.conf.dir");
        try {
            URL url = null;
            //get URL     
             url = new File(confDir, fileName).toURI().toURL();
            //After get URL, call the method of super class
            return new ApplicationProperties(url).interpolatedConfiguration();
            
        } catch (Exception e) {
            throw new SysimpleException("Failed to load the file sysimple.properties. Please check if the SYSIMPLE_CONF_DIR is correct:"+confDir, e);
        }
    }
        
    /**
     * Remove prefix of properties of configuration
     * eg: prefix.number = 1
     *     prefix.string = Apache
     * After removing prefix, it can get
     *     number = 1
     *     string = Apache
     * @Name: getSubsetConfiguration 
     * @param: Configuration inConf, String prefix
     * @return: Configuration   
     * @throws:
     */
    public static Configuration getSubsetConfiguration(Configuration inConf, String prefix) {
        return inConf.subset(prefix);
    }
    
    
}
