package org.bit.linc.clusters;

import java.util.List;

public interface clusterOperate {
	public void createCluster(String clusterName);
	
	public List<String> showCluster();
	
	public boolean removeCluster(String clusterName);
}
