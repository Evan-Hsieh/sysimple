package org.bit.linc.plugins.scripts;

import java.io.File;

import javax.security.auth.callback.Callback;

import org.bit.linc.commons.cmdline.CmdCallBack;
import org.bit.linc.commons.cmdline.CmdLine;
import org.bit.linc.commons.cmdline.CmdType;
import org.bit.linc.commons.exception.SysimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Script {
	private static Logger logger=LoggerFactory.getLogger(Script.class);
	private String name;
	private String path;
	
	public Script(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * @param path  Scripts's absolute path
	 * @param name Scripts's name
	 */
	public Script(String name,String path) {
		super();
		this.name = name;
		this.path = path;
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
		final CmdLine cmdline=new CmdLine();
		if(name.endsWith("sh")&&CmdType.Linux.equals(CmdType.getCurrentType())){
			new Thread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					try {
						cmdline.callCommand(CmdType.Linux,path, interFile, callBack);
					} catch (SysimpleException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}
				}
			}).start();
		}else if(name.endsWith("bat")&&CmdType.DOS.equals(CmdType.getCurrentType())){
			new Thread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					try {
						cmdline.callCommand(CmdType.DOS,path, interFile, callBack);
					} catch (SysimpleException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}
				}
			}).start();
			
		}else{
			throw new SysimpleException("can not run this script in this environment");
		}
		return ;
	}
	
}
