package org.bit.linc.web.controllers;

import java.io.IOException;
import java.util.ArrayList;

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

public class TestData extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{	
		//Set encoding type by unified static class EncodeType.java
    	EncodeType.setEncodingType(response);
    	

		try {
			//You could use a method to get the data which you want to test.
			String testData="This test data";
			
			Plugin p = DataTest.testPluginXml();
			p.create();
			//PluginsUtil.getPluginList();
			//return data
			ResponseUtil.returnData(response, new Gson().toJson(testData));
			
		} catch (SysimpleException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

}
