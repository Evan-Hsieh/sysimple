package org.bit.linc.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bit.linc.exception.SysimpleException;
import org.bit.linc.plugins.PluginsUtil;
import org.bit.linc.web.commons.EncodeType;

import com.google.gson.Gson;

public class ShowPlugins extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{	
		//Set encoding type by unified static class EncodeType.java
    	EncodeType.setContentType(response);
    	EncodeType.setCharacterEncoding(response);
		List<String> pluginsList=null;
		try {
			pluginsList=PluginsUtil.getPluginNameList();
			PrintWriter out = response.getWriter();     
			out.println(new Gson().toJson(pluginsList));    
			out.flush();
			out.close();
		} catch (SysimpleException e) {
			e.printStackTrace();
		}
		System.out.println(pluginsList);

	}
}
