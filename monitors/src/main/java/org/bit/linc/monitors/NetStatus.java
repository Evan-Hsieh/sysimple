package org.bit.linc.monitors;

public class NetStatus {
	private final String ip;
	private final long sendKb;
	private final long getKb;
	public NetStatus(String ip, long sendKb, long getKb) {
		super();
		this.ip = ip;
		this.sendKb = sendKb;
		this.getKb = getKb;
	}
	public String getIp() {
		return ip;
	}
	public long getSendKb() {
		return sendKb;
	}
	public long getGetKb() {
		return getKb;
	}
	

}
