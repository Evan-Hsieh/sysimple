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

/*
 * 	name
	intro
    createUser
	createTime
	master
	hostsList
	connectStatus
 * */
 
public class Cluster implements Clusters,DataPersistence{
	private String clusterName;
	private String introduce;
	private int numOfNode;
	private boolean connectStatus;
	private ArrayList host;
	
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
		
	}
	@Override
	public void removeHost(Host h) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void notifyHost() {
		// TODO Auto-generated method stub
		
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
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
}
