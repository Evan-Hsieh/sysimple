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
			
			System.out.println(files[i].getName());
			
			if(files[i].getName().endsWith("clusters")||files[i].getName().endsWith("cluster")){
				JAXBContext context;
				try {
							
					context = JAXBContext.newInstance(Cluster.class);
					Unmarshaller unMarshaller = context.createUnmarshaller();
					System.out.println(files[i].getAbsolutePath()+"/info.xml");
					Cluster cluster=(Cluster)unMarshaller.unmarshal(new File(files[i].getAbsolutePath()+"/info.xml"));
					//If the cluster-name in the info.xml is different from the one in the file system, update the info.xml
					System.out.println(files[i].getAbsolutePath()+"/info.xml");
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
	
}
