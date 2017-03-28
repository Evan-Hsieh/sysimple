package org.bit.linc.web.commons;

import org.bit.linc.commons.cmdline.CmdCallBack;
import org.bit.linc.commons.config.*;
import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.plugins.plugins.Plugin;
import org.bit.linc.plugins.plugins.PluginsUtil;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartWeb{
	private static final Logger logger = LoggerFactory.getLogger(StartWeb.class);
	public static void main(String[] args) throws SysimpleException {
		//If failed to init config properties, web server will not start.
		if(false==new HierarchicalConfigProperties().initConfigProperties()){
			return;
		}
		//Start web server
		Server server = new Server();
		server.setStopAtShutdown(true);
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setReuseAddress(false);
		int port=Integer.parseInt(System.getProperty("sysimple.webserver.port"));
        connector.setPort(port);
        logger.info("starting Sysimple in port:{}",port);
        server.setConnectors(new Connector[]{connector}); 
        WebAppContext webAppContext;
        if(args.length==0){
        	webAppContext = new WebAppContext("src/main/webapp","/");
            webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");
            webAppContext.setResourceBase("src/main/webapp");
        }else{
        	webAppContext = new WebAppContext();
        	webAppContext.setWar(args[0]);
        }
        webAppContext.setDisplayName("jetty");
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        webAppContext.setConfigurationDiscovered(true);
        webAppContext.setParentLoaderPriority(true);
        server.setHandler(webAppContext);
        server.setStopAtShutdown(true);
        try{
            server.start();
            logger.info("********************************************************");
            logger.info("The SySimple Has Started !!!");
        }catch(Exception e){
        	logger.error(e.getMessage());
        	System.exit(0);
        }
	}
	
}