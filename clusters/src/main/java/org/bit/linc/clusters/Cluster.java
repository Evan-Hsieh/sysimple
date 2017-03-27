package org.bit.linc.clusters;

<<<<<<< HEAD
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
=======
>>>>>>> master
import java.util.List;

import javax.management.ObjectInstance;

/*
 * 	name
	intro
    createUser
	createTime
	master
	hostsList
	connectStatus
 * */
 
public class Cluster implements Serializable{
	private String clusterName;
	private String introduce;
	private String createUser;
	private String createTime;
	private String masterName;
	private int numOfNode;
	private boolean connectStatus;
	
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
	 * get CreateUser's name
	 * @return
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * set CreateUser's name
	 * @return
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * get CreateTime
	 * @return
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * set CreateTime
	 * @return
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * get master's name
	 * @return
	 */
	public String getMaster() {
		return masterName;
	}
	/**
	 * set master's name
	 * @return
	 */
	public void setMaster(String master) {
		this.masterName = master;
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
	
	/**
	 * serializing the data requested by the front-end
	 * @return
	 */
	public static void serializeObject(Object obj,String path)throws IOException{
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}
	/**
	 * reserializing the data requested to the front-end
	 * @return
	 */
	public static Object reserializeObject(String path)throws ClassNotFoundException,IOException{
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		return ois.readObject();
		
	}
	
	public void createNode(String clusterName) {
		// TODO Auto-generated method stub
		
	}
	public List<String> showNode() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean removeNode(String clusterName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
