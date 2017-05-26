package org.bit.linc.web.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bit.linc.clusters.Cluster;
import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.plugins.plugins.Plugin;
import org.bit.linc.web.commons.DataTest;
import org.bit.linc.web.commons.EncodeType;
import org.bit.linc.web.commons.ResponseUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CreateCluster extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{	
		//Set encoding type by unified static class EncodeType.java
    	EncodeType.setEncodingType(response);
    	
    	int clusterParameterNum=3;
    	int hostParameterNum=5;
		//String[] data=request.getParameter("data").split("&");		
		String data=request.getParameter("data");
		//The first host is the default master of cluster.
		Cluster newCluster = new Cluster();

/*		for(int index=clusterParameterNum;index<data.length;index+=hostParameterNum){
			
		}*/
		System.out.println(data);			
	
	}
}
