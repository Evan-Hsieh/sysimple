package org.bit.linc.commons.utils;

public class CanStopThread extends Thread{
	protected boolean isStop=false;
	public void goStop(){
		this.isStop=true;
	}
}