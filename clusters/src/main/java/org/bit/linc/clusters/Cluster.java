package org.bit.linc.clusters;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.bit.linc.commons.utils.ExResult;
import org.bit.linc.commons.utils.FileUtil;
import org.bit.linc.commons.utils.UniqueID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** CONTENT
 *  1.Constructors methods
 *  2. Fields and their getters/setters.
 *  3. Methods
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Cluster{
	/** 
	 *  1.Constructors methods. <<<<<<<<<<
	 */
	public Cluster (){}
	public Cluster(String name,String intro,String detail){
		this.id=UniqueID.getUUIDAsStr("");
		this.name=name;
		this.intro=intro;
		this.detail=detail;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		this.createTime=sdf.format(new Date());
	}
	/** End of 1.Constructors methods. >>>>>>>>>>
	 */
	
	
	/**
	 *  2. Fields and their getters/setters. <<<<<<<<<<
	 */
	private String id;
	private String name;
	private String intro;
	private String detail;
	private String createTime;
	private String createUser;
	private String master; 
	private String connectStatus;
	//ArrayList must be intial
	private ArrayList<Host> hostsList=new ArrayList<Host>();
	//Logger
	private static Logger logger=LoggerFactory.getLogger(Cluster.class);
	@XmlElement
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	@XmlElement
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@XmlElement
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@XmlElement
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	@XmlElement
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	@XmlElement
	public String getConnectStatus() {
		return connectStatus;
	}
	public void setConnectStatus(String connectStatus) {
		this.connectStatus = connectStatus;
	}
	@XmlElementWrapper(name="hosts")
	@XmlElement(name="host")
	public ArrayList<Host> getHostsList() {
		return hostsList;
	}
	public void setHosts(ArrayList<Host> hostsList) {
		this.hostsList = hostsList;
	}
	/**
	 *  End of 2. Fields and their getters/setters. >>>>>>>>>
	 */
	 
	
	
	/**
	 *  3. Methods. <<<<<<<<<<
	 */

	public String toString() {
		return super.toString();
	}
	
	public void addHost(Host h) {
		hostsList.add(h);
		updateInfoXml();
	}

	public void removeHost(Host h) {
		int i = hostsList.indexOf(h);
		if (i >= 0) {
			hostsList.remove(i);
		}
		updateInfoXml();
	}

	public void updateInfoXml() {
		String clusterDir = ClustersUtil.getClustersDir() + "/" + name;
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Cluster.class);
			Marshaller marshaller = context.createMarshaller();
			//format the info.xml file, then it will be legible.
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshaller.marshal(this, new File(clusterDir + "/info.xml"));
		} catch (JAXBException e) {
			logger.error("something error in serializing cluster to info.xml");
			e.printStackTrace();
		}
		
	}
	
	public static Cluster initCluster(String inputData){
    	int clusterParameterNum=3;
    	int hostParameterNum=5;
		String[] data=inputData.split("&");		
		Cluster newCluster = new Cluster(data[0].split("=")[1]+"-cluster",data[1].split("=")[1],data[2].split("=")[1]);
		//The first host is the default master of cluster.
		newCluster.setMaster(data[3].split("=")[1]);
		ArrayList<Host> hostsList=new ArrayList<Host>();
		for(int index=clusterParameterNum;index<data.length;index+=hostParameterNum){
			Host newHost = new Host(data[index].split("=")[1],data[index+1].split("=")[1],data[index+2].split("=")[1],data[index+3].split("=")[1],data[index+4].split("=")[1]);
			newHost.setClusterId(newCluster.getId());
			hostsList.add(newHost);
		}
		newCluster.setHosts(hostsList);
		return newCluster;
	}
	
	public ExResult create(){
		ExResult result=new ExResult();
		String clustersDir=ClustersUtil.getClustersDir()+"/"+name;
		result=FileUtil.CreateFile(false,clustersDir);		
		if(result.code==0){
			//create the dir named scripts
			result=FileUtil.CreateFile(false,clustersDir+"/hosts");
			//update 
			updateInfoXml();
			if(result.code!=0){
				FileUtil.DeleteFile(clustersDir);//if not success,clean all that have done
			}
		}else if(result.code!=1){//1：already exist,so can't remove this plugin
			FileUtil.DeleteFile(clustersDir);//if not success,clean all that have done
		}
		return result;
	}
	
	

	/**
	 *  End of 3. Methods. >>>>>>>>>
	 */
	
}
