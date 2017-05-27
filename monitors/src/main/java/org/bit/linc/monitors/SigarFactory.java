package org.bit.linc.monitors;

import org.bit.linc.commons.utils.OsCheck;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;  

public class SigarFactory {
	private static final Logger logger = LoggerFactory.getLogger(SigarFactory.class);
	  private final static Sigar sigar=null;
	  
	  public static Sigar getSigar(){
		  if(sigar==null){
			  synchronized (SigarFactory.class) {
					if(sigar==null){
						return initSigar();
					}else{
						return sigar;
					}
			  }
		  }
		  return sigar;
	  }

      private static Sigar initSigar() { 
	        try { 
	            File classPath = new File(System.getProperty("sysimple.conf.dir")+"/sigar/"); 
	            String path = System.getProperty("java.library.path"); 
	            String classPath2=classPath.getCanonicalPath();
	            if (OsCheck.getOperatingSystemType() == OsCheck.OSType.Windows) {
	                path += ";" + classPath2; 
	            } else { 
	                path += ":" + classPath2; 
	            } 
	            System.setProperty("java.library.path", path); 
	            return new Sigar(); 
	        } catch (Exception e) { 
	        	logger.error("add sigar to java path failed,the monitor will be failed");
	            return null; 
	        } 
	    } 
}
