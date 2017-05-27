package org.bit.linc.plugins.scripts;

import java.io.File;

import org.bit.linc.commons.cmdline.CmdCallBack;
import org.bit.linc.commons.cmdline.CmdLine;
import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.OsCheck;
import org.bit.linc.commons.utils.OsCheck.OSType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Script {
	private static Logger logger=LoggerFactory.getLogger(Script.class);
	private transient Thread thread;
	private String name;
	private String intro;
	private String path;
	private transient CmdLine cmdline=new CmdLine();

	/**
	 * Constructors of class Script
	 */
	//This no-argument constructor is necessary for JAXB
	public Script(){}
	public Script(String name) {
		this.name=name;
	}
	public Script(String name,String intro) {
		this(name);
		this.intro=intro;
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
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
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
	 * run script
	 * @return
	 * @throws SysimpleException :can not run this script in this environment or Script is not exist
	 */
	public void run(final CmdCallBack callBack) throws SysimpleException{
		if(null==path||path.equals("")||!(new File(path).exists())){
			throw new  SysimpleException(path+" is not exist");
		}
		if((name.endsWith("sh")&&OsCheck.getOperatingSystemType()==OSType.Linux)
				||(name.endsWith("bat")&&OsCheck.getOperatingSystemType()==OSType.Windows)){
			thread=new Thread(new Runnable() {
				public void run() {
					try {
						cmdline.callCommand(path, callBack);
					} catch (SysimpleException e) {
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
