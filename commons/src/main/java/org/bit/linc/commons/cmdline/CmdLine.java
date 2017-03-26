package org.bit.linc.commons.cmdline;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.FileUtil;

/**
 * each CmdLine can run only one script;
 * @author wuqi
 *
 */
public class CmdLine {
	abstract class CanStopThread extends Thread{
		public boolean isStop;
		abstract public void goStop();
	}
	private CanStopThread executeThread=null;
	/**
	 * stop this command
	 */
	public  void stop(){
		if(executeThread!=null){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			if(cmdType.equals(CmdType.Linux)){
				Runtime.getRuntime().exec("chmod  666 "+interFile).waitFor();
			}
			String newCommand=commandOrFile+" >> "+interFile;
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
							while(lineNum<FileUtil.GetTotalLines(file)){
								callBack.printLine(reader.readLine());
								lineNum++;
							}
						}
					}catch(FileNotFoundException e){
						//log日志输入：file not found
					}catch (IOException e) {
						//log日志输出:open interFile failed
					}
				}
				@Override
				public void goStop() {
					try{
						if(reader!=null){
							reader.close();
						}
					}catch(IOException e){
					}
					isStop=true;
					
				}
			};
			executeThread.start();
			ps.waitFor();
			Thread.sleep(500);
			executeThread.goStop();
		}catch (IOException e) {
			throw new SysimpleException(e.getMessage());
		}catch (InterruptedException e) {
			if (executeThread!=null&&executeThread.isAlive()) {
				executeThread.goStop();
			}
			throw new SysimpleException("call shell was interrupted");
		}
	}
	

	
	
}
