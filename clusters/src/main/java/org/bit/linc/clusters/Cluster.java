package org.bit.linc.clusters;

import java.util.List;

/*
 * 	name
	intro
    createUser
	createTime
	master
	hostsList
	connectStatus
 * */
 
public class Cluster {
	private String name;
	private String intro;
	private String createUser;
	private String createTime;
	private String master;
	private List<String> hostsList; 
	private String connectStatus;
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
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public List<String> getHostsList() {
		return hostsList;
	}
	public void setHostsList(List<String> hostsList) {
		this.hostsList = hostsList;
	}
	public String getConnectStatus() {
		return connectStatus;
	}
	public void setConnectStatus(String connectStatus) {
		this.connectStatus = connectStatus;
	}
	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
