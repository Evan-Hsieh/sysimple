package org.bit.linc.clusters;

import java.io.IOException;

public class Node {
	private String nodeName;
	private String createUser;
	private String createTime;
	private String ipAddress;
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public Object addNode(Object obj,String path) throws IOException, ClassNotFoundException{
		Cluster.serializeObject(obj, path);
		return Cluster.reserializeObject(path);
	}
	
}
