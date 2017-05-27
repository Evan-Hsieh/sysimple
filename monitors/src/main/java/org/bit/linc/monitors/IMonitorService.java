package org.bit.linc.monitors;


public interface IMonitorService {
	/** 
     * 获得当前的监控对象. 
     * @return 返回构造好的监控对象 
     * @throws Exception 
     */  
    public MonitorBean getMonitorBean() throws Exception;  
}
