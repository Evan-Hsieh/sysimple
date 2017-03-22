package org.bit.linc.web.commons;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
	public static void returnData(HttpServletResponse response,String jsonData) throws Exception{
		PrintWriter out = response.getWriter();     
		out.println(jsonData);    
		out.flush();
		out.close();
	}
}
