package org.bit.linc.clusters;

import java.io.IOException;

import javax.security.auth.Subject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Host implements Hosts{
	@XmlAttribute  
	private int hostId;
	@XmlElement(name="host-name")  
	private String hostName;
	@XmlElement(name="host-ip")  
	private String ipAddress;
	@XmlElement(name="ismaster")  
	private boolean isMaster;
	
	private Clusters Cluster;
	
	public Host(Clusters Cluster){
		this.Cluster = Cluster;
		Cluster.registerHost(this);
	}
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public int getHostId() {
		return hostId;
	}
	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public boolean isMaster() {
		return isMaster;
	}
	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}
	
	public void update(int hostId, String hostName, String ipAddress, boolean isMaster) {
		// TODO Auto-generated method stub
		this.hostId = hostId;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.isMaster = isMaster;
		Cluster.notifyHost();
	}
	
}
