package org.bit.linc.monitors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MonitorBean {
	private final int cpuRatio;
	
	private final  double memoryTotal;
	
	private final double memoryUsed;
	
	private final double memoryFree;
	
	private final int fsTotal;
	
	private final int fsUsed;
	
	private final int fsFree;

	private final ArrayList<NetStatus> netStatus;
	
	private final String time;
	
	public MonitorBean(int cpuRatio,double memoryTotal,double memoryUsed,int fsTotal,int fsUsed,ArrayList<NetStatus> netStatus){
		this.cpuRatio=cpuRatio;
		this.memoryTotal=memoryTotal;
		this.memoryUsed=memoryUsed;
		this.memoryFree=memoryTotal-memoryUsed;
		this.fsTotal=fsTotal;
		this.fsUsed=fsUsed;
		this.fsFree=fsTotal-fsUsed;
		this.netStatus=netStatus;
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.time= formatter.format(currentTime);
	}

	public int getCpuRatio() {
		return cpuRatio;
	}

	public double getMemoryTotal() {
		return memoryTotal;
	}

	public double getMemoryUsed() {
		return memoryUsed;
	}

	public double getMemoryFree() {
		return memoryFree;
	}

	public int getFsTotal() {
		return fsTotal;
	}

	public int getFsUsed() {
		return fsUsed;
	}

	public int getFsFree() {
		return fsFree;
	}

	public ArrayList<NetStatus> getNetStatus() {
		return netStatus;
	}

	
	
}
