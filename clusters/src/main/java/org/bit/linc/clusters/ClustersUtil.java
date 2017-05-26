package org.bit.linc.clusters;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.bit.linc.commons.exception.SysimpleException;import org.omg.CORBA.Context;

public class ClustersUtil {
	public static String getClustersDir(){
		File file = new File(System.getProperty("sysimple.clusters.dir"));
		return file.getAbsolutePath();
	}
	
	public static String verifyClustersDir(){
		if(new File(getClustersDir()).listFiles()==null){
			return "non-existent";
		}
		if(new File(getClustersDir()).listFiles().length==0){
			return "empty";
		}else{
			return "ocupied";
		}
	}
	
	public ArrayList<Cluster> getClusterList() throws SysimpleException{
		ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
		String ClusterDir = getClustersDir();
		File[] files = new File(ClusterDir).listFiles();
		for(int i = 0;i < files.length; i++){
			if (files[i].getName().endsWith("cluster")||files[i].getName().endsWith("clusters")) {
				JAXBContext context;
				try {
					context = JAXBContext.newInstance(Cluster.class);
					Unmarshaller unmar = context.createUnmarshaller();
					Cluster cluster = (Cluster)unmar.unmarshal(new File(files[i].getAbsolutePath() + "/info.xml"));
					cluster.setName(files[i].getName());
					clusterList.add(cluster);
				} catch (JAXBException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}
		return clusterList;
	}
	
}
