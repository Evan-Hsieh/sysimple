package org.bit.linc.web.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bit.linc.clusters.Cluster;
import org.bit.linc.clusters.ClustersUtil;
import org.bit.linc.web.commons.EncodeType;
import org.bit.linc.web.commons.ResponseUtil;

import com.google.gson.Gson;

public class CheckClusters extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{	
		//Set encoding type by unified static class EncodeType.java
    	EncodeType.setEncodingType(response);

  	if("getClustersList".equals(request.getParameter("data"))){
    		try {
    			ArrayList<String> clustersNameList=new ArrayList<String>();
    			if(ClustersUtil.getClusterList()==null){
        			System.out.println("it is null");
    			}

    			for(Cluster x:ClustersUtil.getClusterList()){
    				System.out.println(x.getName());
    				clustersNameList.add(x.getName());
    			}
				ResponseUtil.returnData(response, new Gson().toJson(clustersNameList));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    
    	
    	
      	
    	
	
	}
}
