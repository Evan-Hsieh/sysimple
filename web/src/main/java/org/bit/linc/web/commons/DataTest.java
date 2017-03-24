package org.bit.linc.web.commons;

import java.util.ArrayList;

import org.bit.linc.plugins.plugins.Plugin;
import org.bit.linc.plugins.scripts.Script;

import com.google.gson.Gson;

public class DataTest {
	public static ArrayList<Plugin> buildTestData(){
		Plugin p1=new Plugin("hadoop-plugin","This plugin will help user to install and configure hadoop easily.");
		p1.setDetail("The Apache Hadoop project develops open-source software for reliable, scalable, distributed computing.The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models. It is designed to scale up from single servers to thousands of machines, each offering local computation and storage. Rather than rely on hardware to deliver high-availability, the library itself is designed to detect and handle failures at the application layer, so delivering a highly-available service on top of a cluster of computers, each of which may be prone to failures");
		ArrayList<Script> sArray=new ArrayList<Script>();
		sArray.add(new Script("p1s1"));
		sArray.add(new Script("p1s2"));
		sArray.add(new Script("p1s3"));
		p1.setScriptsList(sArray);
		
		Plugin p2=new Plugin("Atlas-plugin","This plugin will help user to install and configure atlas easily.");
		p2.setDetail("The Apache Atlas project develops open-source software for reliable, scalable, distributed computing.The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models. It is designed to scale up from single servers to thousands of machines, each offering local computation and storage. Rather than rely on hardware to deliver high-availability, the library itself is designed to detect and handle failures at the application layer, so delivering a highly-available service on top of a cluster of computers, each of which may be prone to failures");
		
		Plugin p3=new Plugin("Sqoop-plugin","This plugin will help user to install and configure sqoop easily.");
		p3.setDetail("The Apache Atlas project develops open-source software for reliable, scalable, distributed computing.The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models. It is designed to scale up from single servers to thousands of machines, each offering local computation and storage. Rather than rely on hardware to deliver high-availability, the library itself is designed to detect and handle failures at the application layer, so delivering a highly-available service on top of a cluster of computers, each of which may be prone to failures");
		
		Plugin p4=new Plugin("Kafka-plugin","This plugin will help user to install and configure kafka easily.");
		p4.setDetail("The Apache Atlas project develops open-source software for reliable, scalable, distributed computing.The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models. It is designed to scale up from single servers to thousands of machines, each offering local computation and storage. Rather than rely on hardware to deliver high-availability, the library itself is designed to detect and handle failures at the application layer, so delivering a highly-available service on top of a cluster of computers, each of which may be prone to failures");
				
		ArrayList<Plugin> pluginList=new ArrayList<Plugin>();
		pluginList.add(p1);
		pluginList.add(p2);
		pluginList.add(p3);
		pluginList.add(p4);
		
		return pluginList;
		
	}
	
	public static void main(String[] args){
		ArrayList<Plugin> pluginsList=null;	

		pluginsList=DataTest.buildTestData();
		System.out.println(new Gson().toJson(pluginsList));
	}

}
