package org.bit.linc.clusters;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.bit.linc.commons.exception.SysimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClustersUtil {
	private static Logger logger=LoggerFactory.getLogger(ClustersUtil.class);
	
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
	
	public static ArrayList<Cluster> getClusterList() throws SysimpleException{		
		if(verifyClustersDir()!="ocupied"){
			logger.info("The dir of clusters is empty or not exist. The null will be return when try to get cluster-list, ");
			return null;
		}		
		ArrayList<Cluster> clustersList=new ArrayList<Cluster>();
		//get the array of files or dirs
		File [] files=new File(getClustersDir()).listFiles();
		//get the dirs with plugin(s) as suffix among array
		for(int i=0;i<files.length;i++){		
			if(files[i].getName().endsWith("clusters")||files[i].getName().endsWith("cluster")){
				JAXBContext context;
				try {						
					context = JAXBContext.newInstance(Cluster.class);
					Unmarshaller unMarshaller = context.createUnmarshaller();
					Cluster cluster=(Cluster)unMarshaller.unmarshal(new File(files[i].getAbsolutePath()+"/info.xml"));
					//If the cluster-name in the info.xml is different from the one in the file system, update the info.xml
					if(!cluster.getName().equals(files[i].getName())){
						cluster.setName(files[i].getName());
						cluster.updateInfoXml();
					}					
					clustersList.add(cluster);
				} catch (JAXBException e) {
					e.printStackTrace();
					logger.error("something error in getting cluster from {}/info.xml",files[i].getAbsolutePath());
				}
			}
		}
		return clustersList;
	}
	
	public static Cluster getCluster(String clusterName){
		File [] files=new File(getClustersDir()).listFiles();
		Cluster cluster=new Cluster();
		for(int i=0;i<files.length;i++){		
			if(files[i].getName().startsWith(clusterName)&&files[i].getName().endsWith("cluster")){			
				try {						
					JAXBContext context = JAXBContext.newInstance(Cluster.class);
					Unmarshaller unMarshaller = context.createUnmarshaller();
					cluster=(Cluster)unMarshaller.unmarshal(new File(files[i].getAbsolutePath()+"/info.xml"));
				} catch (JAXBException e) {
					e.printStackTrace();
					logger.error("something error in getting cluster "+clusterName+" from {}/info.xml",files[i].getAbsolutePath());
				}
			}
		}
		return cluster;
	}
	
	//The xml file can't store the blank or space, so the info of cluster should be restore.
	public static Cluster resetClusterInfo(Cluster cluster){
		cluster.setName(cluster.getName().replace("+", " "));
		cluster.setIntro(cluster.getIntro().replace("+", " "));
		cluster.setDetail(cluster.getDetail().replace("+", " "));
		for(Host h:cluster.getHostsList()){
			h.setIntro(h.getIntro().replace("+", " "));
			//set the clusterID of host as id of cluster
			h.setClusterId(cluster.getId());
		}
		return cluster;
	}
	
}
