package org.bit.linc.shell;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.bit.linc.Utils.FileUtil;
import org.bit.linc.exception.SysimpleException;

public class CmdLine {

	
	/**
	 * this method used to call shell file,you need to run it in a thread.
	 * you should better check whether commandOrFile is existing if commandOrFile is a shell file before using this method.
	 * if commandOrFile is not exist,error will be log into logfile
	 * @param field in CmdType
	 * @param commandOrFile 'command' or 'path shell path'
	 * @param interFile intermediate file
	 * @param callBack Class's name that implements CmdCallBack,You need to define your printLine() in this Class
	 * @throws SysimpleException
	 */
	public void callCommand(String cmdType,String commandOrFile,String interFile,final CmdCallBack callBack) throws SysimpleException{
		Thread t1 = null;
		final File file=new File(interFile);
		try {
			Runtime.getRuntime().exec(new String[]{cmdType,"/c","echo \"\" >"+interFile}).waitFor();
			if(cmdType.equals(CmdType.Linux)){
				Runtime.getRuntime().exec("chmod  666 "+interFile).waitFor();
			}
			String newCommand=commandOrFile+" >> "+interFile;
			final Process ps = Runtime.getRuntime().exec(new String[]{cmdType,"/c",newCommand});
			t1=new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						int lineNum=0;
						FileReader in = new FileReader(file);
						LineNumberReader reader = new LineNumberReader(in);  
						while(true){
							while(lineNum<FileUtil.getTotalLines(file)){
								callBack.printLine(reader.readLine());
								lineNum++;
							}
						}
					}catch(FileNotFoundException e){
						//log日志输入：file not found
					}catch (IOException e) {
						// TODO Auto-generated catch block
						//log日志输出:open interFile failed
					}
				}
			};
			t1.start();
			ps.waitFor();
			Thread.sleep(500);
			t1.stop();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SysimpleException(e.getMessage());
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			if (t1!=null&&t1.isAlive()) {
				t1.stop();
			}
			throw new SysimpleException("call shell was interrupted");
		}
	}
	

	
	
}
