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
import org.bit.linc.commons.utils.UniqueID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** CONTENT
 *  1.Constructors methods
 *  2. Fields and their getters/setters.
 *  3. Methods
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Cluster implements ClusterInterface{
	/** 
	 *  1.Constructors methods. <<<<<<<<<<
	 */
	public Cluster (){	}
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
	private ArrayList<Host> hostsList;
	//Logger
	private static Logger logger=LoggerFactory.getLogger(Cluster.class);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
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
	@Override
	public String toString() {
		return super.toString();
	}
	

	@Override
	public void registerHost(Host h) {
		// TODO Auto-generated method stub
		hostsList.add(h);
		updateInfoXml();
	}
	@Override
	public void removeHost(Host h) {
		// TODO Auto-generated method stub
		int i = hostsList.indexOf(h);
		if (i >= 0) {
			hostsList.remove(i);
		}
	}
	@Override
	public void notifyHost() {
		// TODO Auto-generated method stub
		updateInfoXml();
	}

	public void updateInfoXml() {
		String clusterDir = ClustersUtil.getClusterDir() + "/" + name;
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
	
	/**
	 * create a new host in this cluster
	 * @param hostId 
	 * @param hostName 
	 * @param ipAddress 
	 * @param isMaster 
	 * @return
	 */
	public Host createHost(int hostId, String hostName, String ipAddress, boolean isMaster){
		Host host=new Host(hostId, hostName, ipAddress, isMaster);
		registerHost(host);
		return host;
	}
	/**
	 *  End of 3. Methods. >>>>>>>>>
	 */
	
}
