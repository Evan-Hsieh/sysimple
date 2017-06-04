package org.bit.linc.web.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.bit.linc.commons.utils.CanStopThread;
import org.bit.linc.monitors.MonitorBean;
import org.bit.linc.monitors.MonitorServiceImpl;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@ServerEndpoint(value="/socket/monitors/")
public class MonitorSocket {
	private static final Logger logger = LoggerFactory.getLogger(MonitorSocket.class);
	
	@OnOpen
    public void onWebSocketConnect(Session sess)
    {
		logger.info("Socket Connected: " + sess);
		new sendThread(sess).start();
		
    }
	@OnMessage
    public void onWebSocketText(String message)
    {
        System.out.println("Received TEXT message: " + message);
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
            new Timer().schedule(new TimerTask(){ 
                @Override 
                public void run() 
                { 
                    if(!isStop){
                    	try {
                            session.getBasicRemote().sendText(new Gson().toJson(MonitorServiceImpl.getMonitorBean())); 
    					} catch (IOException e) {
    						logger.info("an monitor client has closed");
    						goStop();
    					} catch (SigarException e) {
    						logger.error("monitor has an error");
						} 
                    }
                }}, 3000, 3000); 
        } 
    } 
}
