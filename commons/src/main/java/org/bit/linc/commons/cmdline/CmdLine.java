package org.bit.linc.commons.cmdline;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.CanStopThread;
import org.bit.linc.commons.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * each CmdLine can run only one script;
 * @author wuqi
 *
 */
public class CmdLine {
	private static Logger logger=LoggerFactory.getLogger(CmdLine.class);
	private CanStopThread executeThread=null;
	/**
	 * stop this command
	 */
	public  void stop(){
		if(executeThread!=null){
			this.executeThread.goStop();
		}
	}
	/**
	 * this method used to call shell file,you need to run it in a thread.
	 * you should better check whether commandOrFile is existing if commandOrFile is a shell file before using this method.
	 * if commandOrFile is not exist,error will be log into logfile
	 * @param field in CmdType
	 * @param commandOrFile 'command' or 'path shell path'
	 * @param interFile intermediate file
	 * @param callBack Class's name that implements CmdCallBack,You need to define your printLine() in this Class
	 * @throws SysimpleException :when IOException or InterruptedException happend , SysimpleException will be throw
	 */
	public void callCommand(String cmdType,String commandOrFile,String interFile,final CmdCallBack callBack) throws SysimpleException{
		final File file=new File(interFile);
		try {
			Runtime.getRuntime().exec(new String[]{cmdType,"/c","echo \"\" >"+interFile}).waitFor();
			String newCommand="";
			if(cmdType.equals(CmdType.Linux)){
				Runtime.getRuntime().exec("chmod  666 "+interFile).waitFor();
				newCommand=commandOrFile+" >> "+interFile;
			}else{
				newCommand=commandOrFile+" >> "+interFile;
			}	
			final Process ps = Runtime.getRuntime().exec(new String[]{cmdType,"/c",newCommand});
			executeThread=new CanStopThread(){
				LineNumberReader reader=null;
				@Override
				public void run() {
					try{
						int lineNum=0;
						FileReader in = new FileReader(file);
						reader = new LineNumberReader(in);  
						while(!isStop){
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								//e.printStackTrace();
							}
							while(lineNum<FileUtil.GetTotalLines(file)){
								callBack.printLine(reader.readLine());
								lineNum++;
							}
						}
						if(isStop){
							try{
								ps.destroy();
								if(reader!=null){
									reader.close();
								}
							}catch(IOException e){
							}
						}
					}catch(FileNotFoundException e){
						//log日志输出：file not found
						logger.error(e.getMessage());
					}catch (IOException e) {
						//log日志输出:open interFile failed
						logger.error(e.getMessage());
					}
				}
				
			};
			executeThread.start();
			ps.waitFor();
			Thread.sleep(500);
			ps.destroy();
			executeThread.goStop();
		}catch (IOException e) {
			throw new SysimpleException(e.getMessage());
		}catch (InterruptedException e) {
			if (executeThread!=null&&executeThread.isAlive()) {
				executeThread.goStop();
			}
			throw new SysimpleException("call shell "+ commandOrFile+" was interrupted");
		}
	}
	

	
	
}
