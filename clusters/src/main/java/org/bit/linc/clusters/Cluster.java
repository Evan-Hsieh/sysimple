package org.bit.linc.clusters;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


import java.util.List;

import javax.management.ObjectInstance;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * 	name
	intro
    createUser
	createTime
	master
	hostsList
	connectStatus
 * */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Cluster implements ClusterInterface,DataPersistence{
	@XmlElement(name="cluster-name")  
	private String clusterName;
	@XmlElement(name="cluster-info")  
	private String introduce;
	@XmlElement(name="node-num")  
	private int numOfNode;
	@XmlElement(name="connectstatus")  
	private boolean connectStatus;
	@XmlElementWrapper(name="hosts")  
	@XmlElement(name="host")   
	private ArrayList<Host> hosts;
	 
	
	public Cluster (){
		
	}
	
	public Cluster(String clusterName,String introduce,int numOfNode,boolean connectStatus){
		
	}
	public ArrayList<Host> getHosts() {
		return hosts;
	}
	public void setHosts(ArrayList<Host> hosts) {
		this.hosts = hosts;
	}
	/**
	 * get cluster's name
	 * @return
	 */
	public int getNumOfNode() {
		return numOfNode;
	}
	public void setNumOfNode(int numOfNode) {
		this.numOfNode = numOfNode;
	}
	
	/**
	 * get cluster's name
	 * @return
	 */
	public String getName() {
		return clusterName;
	}
	/**
	 * set cluster's name
	 * @return
	 */
	public void setName(String name) {
		this.clusterName = name;
	}
	/**
	 * get cluster's introduce
	 * @return
	 */
	public String getIntro() {
		return introduce;
	}
	/**
	 * set cluster's introduce
	 * @return
	 */
	public void setIntro(String intro) {
		this.introduce = intro;
	}
	/**
	 * get ConnectStatus
	 * @return
	 */
	public boolean isConnectStatus() {
		return connectStatus;
	}
	/**
	 * set ConnectStatus
	 * @return
	 */
	public void setConnectStatus(boolean connectStatus) {
		this.connectStatus = connectStatus;
	}
	@Override
	public String toString() {
		return super.toString();
	}
	

	@Override
	public void registerHost(Host h) {
		// TODO Auto-generated method stub
		hosts.add(h);
		freshXmlInfo();
	}
	@Override
	public void removeHost(Host h) {
		// TODO Auto-generated method stub
		int i = hosts.indexOf(h);
		if (i >= 0) {
			hosts.remove(i);
		}
	}
	@Override
	public void notifyHost() {
		// TODO Auto-generated method stub
		freshXmlInfo();
	}
	@Override
	public void freshXmlInfo() {
		// TODO Auto-generated method stub
		String clusterDir = ClustersUtil.getClusterDir() + "/" + clusterName;
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Cluster.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(this, new File(clusterDir + "/info.xml"));
		} catch (Exception e)  {
			// TODO: handle exception
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
	
}
