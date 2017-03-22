package org.bit.linc.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class FileUtil {

	/**
	 * get file's lineNum
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static int getTotalLines(File file) throws IOException {  
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
	
}
