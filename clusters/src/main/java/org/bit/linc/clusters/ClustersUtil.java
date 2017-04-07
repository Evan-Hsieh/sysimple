package org.bit.linc.clusters;

import java.io.File;

public class ClustersUtil {
	public static String getClusterDir(){
		File file = new File(System.getProperty("sysimple.clusters.dir"));
		return file.getAbsolutePath();
	}
	
	
}
