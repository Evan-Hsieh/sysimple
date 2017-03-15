package org.bit.linc;

import org.apache.commons.configuration.Configuration;

public enum DefaultConfiguration {
   
	//Add the default value for properties
	SYSIMPLE_PLUGINS_DIR("sysimple.plugins.dir","../integration/src/plugins"),
	SYSIMPLE_WEBSERVER_MIN_THREADS("sysimple.webserver.minthreads", 10),
	SYSIMPLE_WEBSERVER_MAX_THREADS("sysimple.webserver.maxthreads", 1000),
	SYSIMPLE_WEBSERVER_PORT("sysimple.webserver.port",3000);
	
    private final String propertyName;
    private final Object defaultValue;
    /**
     * Construction method
     * @param:
     * @throws
     */
    DefaultConfiguration(String propertyName, Object defaultValue) {
        this.propertyName = propertyName;
        this.defaultValue = defaultValue;
    }
    
    public int getInt(){
    	return Integer.parseInt(defaultValue.toString());
    }
    public String getString(){
    	return defaultValue.toString();
    }
    
    
    
    //下面对于配置文件中某项参数是否有设置，进行了重复判断，因为在StartWeb.java中的getInt()中已经进行了判断。
/*    private static final Configuration SYSIMPLE_PROPERTIES;
    
    static {
        try {
        	SYSIMPLE_PROPERTIES = ApplicationProperties.get();
        } catch (SysimpleException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Object get() {
        Object value = SYSIMPLE_PROPERTIES.getProperty(propertyName);
        return value == null ? defaultValue : value;
    }*/

	
    
/*    public int getInt() {
        return SYSIMPLE_PROPERTIES.getInt(propertyName, Integer.valueOf(defaultValue.toString()).intValue());
    }

    public long getLong() {
        return SYSIMPLE_PROPERTIES.getLong(propertyName, Long.valueOf(defaultValue.toString()).longValue());
    }

    public String getString() {
        return SYSIMPLE_PROPERTIES.getString(propertyName, defaultValue.toString());
    }*/

    

}
