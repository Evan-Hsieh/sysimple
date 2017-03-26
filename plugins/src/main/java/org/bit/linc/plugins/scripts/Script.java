package org.bit.linc.plugins.scripts;

import java.io.File;

import org.bit.linc.commons.cmdline.CmdCallBack;
import org.bit.linc.commons.cmdline.CmdLine;
import org.bit.linc.commons.cmdline.CmdType;
import org.bit.linc.commons.exception.SysimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Script {
	private transient Thread thread;
	private static Logger logger=LoggerFactory.getLogger(Script.class);
	private String name;
	private String path;
	private transient CmdLine cmdline;
	public Script(String name) {
		super();
		this.name = name;
		cmdline=new CmdLine();
	}
	
	/**
	 * @param path  Scripts's absolute path
	 * @param name Scripts's name
	 */
	public Script(String name,String path) {
		this.name = name;
		this.path=path;
		cmdline=new CmdLine();
	}

	/**
	 * get script path
	 * @return
	 */
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * get script name
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * run script
	 * @return
	 * @throws SysimpleException :can not run this script in this environment or Script is not exist
	 */
	public void run(final String interFile,final CmdCallBack callBack) throws SysimpleException{
		if(null==path||path.equals("")||!(new File(path).exists())){
			throw new  SysimpleException(path+" is not exist");
		}
		if(name.endsWith("sh")&&CmdType.Linux.equals(CmdType.getCurrentType())){
			thread=new Thread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					try {
						cmdline.callCommand(CmdType.Linux,path, interFile, callBack);
					} catch (SysimpleException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}
				}
			});
			thread.start();
		}else if(name.endsWith("bat")&&CmdType.DOS.equals(CmdType.getCurrentType())){
			thread=new Thread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					try {
						cmdline.callCommand(CmdType.DOS,path, interFile, callBack);
					} catch (SysimpleException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}
				}
			});
			thread.start();
		}else{
			throw new SysimpleException("can not run this script in this environment");
		}
	}
	
	/**
	 * to know whether this script is running;
	 * @return
	 */
	public boolean isRun(){
		System.out.println("检测线程是否存活："+this.thread.isAlive());
		if(thread!=null&&thread.isAlive()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * stop this script
	 */
	public void stop(){
		this.cmdline.stop();
	}
	
	
}
