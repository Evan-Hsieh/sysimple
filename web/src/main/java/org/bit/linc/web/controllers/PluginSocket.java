package org.bit.linc.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.bit.linc.clusters.Cluster;
import org.bit.linc.clusters.ClustersUtil;
import org.bit.linc.clusters.Host;
import org.bit.linc.commons.cmdline.CmdCallBack;
import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.CanStopThread;
import org.bit.linc.commons.utils.ExResult;
import org.bit.linc.commons.utils.FileUtil;
import org.bit.linc.plugins.plugins.Plugin;
import org.bit.linc.plugins.plugins.PluginsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value="/socket/plugin/")
public class PluginSocket{
	private static final Logger logger = LoggerFactory.getLogger(PluginSocket.class);
	private static BlockingQueue<String> message=new LinkedBlockingQueue<>();
	private static Plugin runPlugin=null;
	private static ArrayList<Session> sessions=new ArrayList<>();
	@OnOpen
    public void onWebSocketConnect(Session sess)
    {
		sessions.add(sess);
		logger.info("Socket Connected: " + sess);
    }
	@OnMessage
    public void onWebSocketText(String message)
    {
		System.out.println(message);
		int firstC=message.indexOf(",");
		String clusterName=message.substring(0,firstC);
		int secondC=message.indexOf(",", firstC+1);
		String pluginName=message.substring(firstC+1,secondC);
		if(runPlugin==null||!runPlugin.isRun()){
			try {
				
				runPlugin=PluginsUtil.getPlugin(pluginName);
				if(runPlugin!=null){
					Cluster cluster=ClustersUtil.getCluster(clusterName);
					ArrayList<Host> hosts=cluster.getHostsList();
					String hostsMessage="";
					for(int i=0;i<hosts.size();i++){
						hostsMessage+=hosts.get(i).getName()+",";
						hostsMessage+=hosts.get(i).getIp()+",";
						hostsMessage+=hosts.get(i).getUserName()+",";
						hostsMessage+=hosts.get(i).getUserPwd();
						if(i!=hosts.size()-1){
							hostsMessage+="|";
						}
					}
					ExResult result=FileUtil.WriteFile(ClustersUtil.getClustersDir()+"/"+clusterName+"/target.run", hostsMessage, false);
					if(result.code==0){
						PluginCallBack call=new PluginCallBack();
						runPlugin.run(call);
						logger.info("{} run",runPlugin.getName());
						new sendThread().start();
					}
				}else{
					logger.info("no {}",message);
				}
				
			} catch (SysimpleException e) {
				logger.error("there are somethings wrong when reading the info.xml of {}",pluginName);
			}
		}
    }
    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
    	for(int i=0;i<sessions.size();i++){
        	if(sessions.get(i)==null||!sessions.get(i).isOpen()){
        		sessions.remove(i);
        		i--;
        	}
        }
    	logger.info("monitor Socket Closed: " + reason);
    }
    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }
    
    class sendThread extends CanStopThread
    {
        public void run() 
        { 
        	while(!isStop){
            	if(runPlugin.isRun()||message.size()>0){
            		try {
            			if(runPlugin.isRun()&&message.size()==0){
            				Thread.sleep(300);
            			}
                		String mess=message.poll();
                        if(mess!=null){
                        	for(int i=0;i<sessions.size();i++){
                            	if(sessions.get(i)!=null&&sessions.get(i).isOpen()){
                            		sessions.get(i).getBasicRemote().sendText(mess);
                            	}
                            }
                        }
    				} catch (IOException e) {
    					logger.info("an monitor client has closed");
    				} catch (InterruptedException e) {
						
					}
            	}else{
            		try {
						Thread.sleep(2000);
						if(!runPlugin.isRun()&&message.size()==0){
							goStop();
							logger.info("{} stop",runPlugin.getName());
						}
					} catch (InterruptedException e) {
					}
            	}
            }
        } 
    }
	
	static class PluginCallBack implements CmdCallBack{
		@Override
		public void printLine(String content) {
			try {
				message.put(content);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}		
		}
		
	}
}
