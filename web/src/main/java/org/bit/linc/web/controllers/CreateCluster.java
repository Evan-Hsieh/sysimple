package org.bit.linc.web.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bit.linc.clusters.Cluster;
import org.bit.linc.clusters.ClustersUtil;
import org.bit.linc.clusters.Host;
import org.bit.linc.commons.exception.SysimpleException;
import org.bit.linc.commons.utils.ExResult;
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
    	//System.out.println(request.getParameter("data"));
    	ExResult res=Cluster.initCluster(request.getParameter("data")).create();
    	if(res.code==0){
    		try {
				ResponseUtil.returnData(response, new Gson().toJson("success"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
}
