package org.bit.linc.commons.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static Logger logger=LoggerFactory.getLogger(FileUtil.class);
	/**
	 * get file's lineNum
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static int GetTotalLines(File file) throws IOException {  
        FileReader in = new FileReader(file);  
        LineNumberReader reader = new LineNumberReader(in);  
        String s = reader.readLine();  
        int lines = 0;  
        while (s != null) {  
            lines++;  
            s = reader.readLine();  
        }  
        reader.close();
        in.close();  
        return lines;  
    }
	/**
	 * create file
	 * @param isFile true：file ,false: fileSet
	 * @param path file path
	 * @return
	 */
	public static ExResult CreateFile(boolean isFile,String path){
		ExResult result=new ExResult();
		File file =new File(path);
		if(isFile&&file.exists()){
			result.code=1;
			result.message="file already exist";
			logger.error("create file {} error ,already exist",path);
			return result;
		}else if(isFile==false&&file.isDirectory()&&file.exists()){
			result.code=1;
			result.message="fileSet already exist";
			logger.error("create fileSet {} error ,already exist",path);
			return result;
		}
		
		try {
			if(isFile==false){
				file.mkdirs();
			}else{
				file.createNewFile();
			}
			logger.info("create {} successfully",file.getAbsolutePath());
		} catch (IOException e) {
			result.code=2;
			result.message="create file error:IOException";
			logger.error("create file {} error:IOException",path);
			return result;
		}
		return result;
	}
	/**
	 * delete file or fileSet
	 * @param path
	 */
	public static void DeleteFile(String path){
		File file =new File(path);
		DeleteFile(file);
	}
	/**
	 * delete file or fileSet
	 * @param file
	 */
	public static void DeleteFile(File file){
		if(!file.exists()){
			return;
		}
		if(file.isDirectory()){
			File[] childs=file.listFiles();
			for(int i=0;i<childs.length;i++){
				DeleteFile(childs[i]);
			}
			file.delete();
			logger.info("delete {} successfully",file.getAbsolutePath());
		}else{
			file.delete();
			logger.info("delete {} successfully",file.getAbsolutePath());
		}
	}
	
	/**
	 * read file by line
	 * @param file
	 * @return
	 */
	public static ExResult ReadFileByLine(File file){
		ExResult result=new ExResult();
		if(!file.exists()){
			result.code=3;
			result.message="file not exist";
		}
		StringBuilder strBuilder=new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
            	strBuilder.append(tempString);
            }
            result.code=0;
            result.message=strBuilder.toString();
            reader.close();
        } catch (IOException e) {
        	result.code=2;
        	result.message="read file by line error:IOException";
            logger.error(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
	}
	/**
	 * read file by line
	 * @param path
	 * @return
	 */
	public static ExResult ReadFileByLine(String path){
		File file =new File(path);
		return ReadFileByLine(file);
	}
	
	/**
	 * write content to file
	 * @param file
	 * @param content
	 * @return
	 */
	public static ExResult WriteFile(File file,String content){
		ExResult result=new ExResult();
		if(!file.exists()){
			CreateFile(true, file.getAbsolutePath());
		}
		FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			result.code=0;
			logger.info("write file successful to {}",file.getAbsolutePath());
		} catch (IOException e) {
			result.code=2;
			result.message="write file error to "+file.getAbsolutePath();
			logger.error("write file error to {}",file.getAbsolutePath());
			return result;
		}
		return result;
	}
	
	/**
	 * write content to file
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static ExResult WriteFile(String filePath,String content){
		File file=new File(filePath);
		return WriteFile(file, content);
	}
	
}
