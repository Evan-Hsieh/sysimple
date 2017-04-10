package org.bit.linc.web.commons;

import org.bit.linc.plugins.plugins.PluginsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.plugins.plugins.Plugin;

public class PluginInfo {
	private static PluginInfo pluginInfo = null;
	private static Object lock = new Object();
	private static ArrayList<Plugin> arrayListPlugin;
	private static Logger logger = LoggerFactory.getLogger(PluginInfo.class);
	Thread mainThread = Thread.currentThread();

	private PluginInfo() {
		init();
	}

	public static PluginInfo getInstance() {
		if (pluginInfo == null) {
			pluginInfo = new PluginInfo();
		}
		return pluginInfo;
	}
	
	private void init() {
		logger.info("init Plugin attributes");
		Thread pluginThread = new Thread() {
			@Override

			public void run() {
				synchronized (mainThread) {
					try {
						arrayListPlugin = PluginsUtil.getPluginList();
						for (Plugin plugin : arrayListPlugin) {
							System.out.println(plugin.getName());
						}
						logger.info("初始化完成");
						mainThread.notify();
					} catch (SysimpleException sysimpleException) {
						sysimpleException.printStackTrace();
						logger.error("something error in getting pluginList from PluginInfo.class");
					}
				}
			}

			public ArrayList<Plugin> getArrayListPlugin() {
				return arrayListPlugin;
			}
		};
		pluginThread.setName("Daemon Thread");
		pluginThread.setDaemon(true);
		pluginThread.start();
		try {
			pluginThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Plugin plugin : arrayListPlugin) {
			System.out.println(plugin.getName());
		}
		logger.info("init completed");
	}
	
	
	
	public void print() {
		for (Plugin plugin : arrayListPlugin) {
			System.out.println(plugin.getName());
		}
	}

}