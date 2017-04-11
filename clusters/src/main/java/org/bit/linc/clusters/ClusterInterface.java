package org.bit.linc.clusters;

public interface ClusterInterface {
	public void registerHost(Host h);
	public void removeHost(Host h);
	public void notifyHost();
}
