package org.bit.linc.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.callback.Callback;
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
	
	@OnOpen
    public void onWebSocketConnect(Session sess)
    {
		logger.info("Socket Connected: " + sess);
		new sendThread(sess).start();
    }
	@OnMessage
    public void onWebSocketText(String message)
    {
		Pattern pattern=Pattern.compile("([^,]*),([^,]*)");
		Matcher matcher=pattern.matcher(message);
		String pluginName="";
		String clusterName="";
		while(matcher.find()){
			clusterName=matcher.group(1);
			pluginName=matcher.group(2);
		}
		if(runPlugin==null||!runPlugin.isRun()){
			try {
				runPlugin=PluginsUtil.getPlugin(pluginName);
				if(runPlugin==null){
					logger.info("no {}",message);
				}
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
				}
			} catch (SysimpleException e) {
				logger.error("there are somethings wrong when reading the info.xml of {}",pluginName);
			}
		}
    }
    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
    	logger.info("monitor Socket Closed: " + reason);
    }
    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }
    class sendThread extends CanStopThread
    {
        Session session;
        sendThread(Session session)
        { 
            this.session = session; 
        } 
        public void run() 
        { 
        	while(!isStop){
            	try {
            		String mess=message.take();
                    session.getBasicRemote().sendText(mess);
				} catch (IOException e) {
					logger.info("an monitor client has closed");
					goStop();
				} catch (InterruptedException e) {
					goStop();
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
