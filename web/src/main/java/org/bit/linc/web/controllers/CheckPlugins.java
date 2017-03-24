package org.bit.linc.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.plugins.plugins.Plugin;
import org.bit.linc.plugins.plugins.PluginsUtil;
import org.bit.linc.web.commons.DataTest;
import org.bit.linc.web.commons.EncodeType;
import org.bit.linc.web.commons.ResponseUtil;

import com.google.gson.Gson;

public class CheckPlugins extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{	
		//Set encoding type by unified static class EncodeType.java
    	EncodeType.setEncodingType(response);
    	
		ArrayList<Plugin> pluginsList=null;	
		try {
			//get data 
			//pluginsList=PluginsUtil.getPluginNameList();
			pluginsList=DataTest.buildTestData();
			//return data
			ResponseUtil.returnData(response, new Gson().toJson(pluginsList));
			
		} catch (SysimpleException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
