package org.bit.linc.commons.cmdline;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.FileUtil;

public class CmdLine {

	
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
		abstract class MyThead extends Thread{
			public boolean isStop;
			abstract public void canStop();
		}
		MyThead t1 = null;
		final File file=new File(interFile);
		try {
			Runtime.getRuntime().exec(new String[]{cmdType,"/c","echo \"\" >"+interFile}).waitFor();
			if(cmdType.equals(CmdType.Linux)){
				Runtime.getRuntime().exec("chmod  666 "+interFile).waitFor();
			}
			String newCommand=commandOrFile+" >> "+interFile;
			final Process ps = Runtime.getRuntime().exec(new String[]{cmdType,"/c",newCommand});
			t1=new MyThead(){
				LineNumberReader reader=null;
				@Override
				public void run() {
					isStop=false;
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
				public void canStop() {
					try{
						if(reader!=null){
							reader.close();
						}
					}catch(IOException e){
					}
					isStop=true;
					
				}
			};
			t1.start();
			ps.waitFor();
			Thread.sleep(500);
			t1.canStop();
		}catch (IOException e) {
			throw new SysimpleException(e.getMessage());
		}catch (InterruptedException e) {
			if (t1!=null&&t1.isAlive()) {
				t1.canStop();
			}
			throw new SysimpleException("call shell was interrupted");
		}
	}
	

	
	
}
