$(function(){	
	$("#sidebar-menu-check-cluster").click(function(){
		//alert("check cluster");	
		resetMainContentHeader("Cluster Management","Check Cluster");
		//empty old elements of main content
		$("#center-main-content").empty();
		syncAjaxInsertRow("#center-main-content","htmls/cluster-check-cluster-info-form.html");
		syncAjaxInsertRow("#center-main-content","htmls/cluster-check-cluster-host-info-form.html");
		syncAjaxInsertRow("#center-main-content","htmls/cluster-check-cluster-host-specific-detail-tabform.html");		
	
		ajaxCheckClusterGetClustersList();
		
	});	
	
	
	
	
	$("#sidebar-menu-create-cluster").click(function(){
		//alert("create cluster");
		resetMainContentHeader("Cluster Management","Create Cluster");
		//empty old elements of main content
		$("#center-main-content").empty();
		//insert origin form
		syncAjaxInsertRow("#center-main-content","htmls/cluster-create-cluster-set-cluster-info.html");


		//After load the html page,the button will be bind.
		$("#create-cluster-confirm-cluster-btn").click(function(){
			//alert("create-cluster-confirm-cluster-btn");
			//show the form of host info 
			syncAjaxInsertRow("#center-main-content","htmls/cluster-create-cluster-set-host-info-form.html");
			//bind some button for clicking
			$("#create-cluster-new-host-btn").click(function(){
				syncAjaxInsertRow("#create-cluster-host-info-form","htmls/cluster-create-cluster-set-host-info-row.html");
			});	
			$("#create-cluster-delete-host-btn").click(function(){
				$("#create-cluster-host-info-form").children().last().remove()
			});	
			$("#create-cluster-submit-btn").click(function(){	
				ajaxCreateCluster($(".input-cluster-info").serialize());
			});	
		});	

	});	
	
	$("#sidebar-menu-monitor-cluster").click(function(){
		//alert("monitor cluster");
		resetMainContentHeader("Cluster Management","Monitor Cluster");
		//empty old elements of main content
		$("#center-main-content").empty();
	});	
	

});

function ajaxCreateCluster(inputData){
	$.ajax({
		  data:{
		    "data" : inputData
		  },
		  type:'POST',
		  url:'cluters-management/create-cluster',
		  dataType:'json',
		  success: function(returnData){
			  //alert(returnData);
		  },
		  error:function(){
			  alert("error");
		  }
	});
};

function ajaxCheckClusterGetClustersList(){
	$.ajax({
		  data:{
		    "data" : "getClustersList"
		  },
		  type:'POST',
		  async:true, 
		  url:'cluters-management/check-clusters',
		  dataType:'json',
		  success: function(returnData){
			  if(returnData!=""){
					//alert("it is not null");
					checkClusterInsertClustersList("#cluster-list-select",returnData);
					//bind the event
					$("#cluster-list-select>option").click(function(){
						//alert($("#cluster-list-select>option:selected").text());
						ajaxCheckClusterGetClusterInfo($("#cluster-list-select>option:selected").text());
					});				
			  }
		  },
		  error:function(){
			  alert("error");
		  }
	});
};

function checkClusterInsertClustersList(tag,inputData){
	$(tag).empty();
	for(var i=0;i<inputData.length;i++){
		//alert(inputData[i]);
		$(tag).append("<option>"+inputData[i]+"</option>");
	}
};

function checkClusterInsertClustersInfo(inputData){
	//alert("insert info");
	$("#cluster-info-create-time").empty();
	$("#cluster-info-create-time").append("<td style=\"width:160px\"><strong>Create Time</strong></td><td>"+inputData.name+"</td>");
	$("#cluster-info-intro").empty();
	$("#cluster-info-intro").append("<td><strong>Cluster Intro</strong></td><td>"+inputData.intro+"</td>");
	$("#cluster-info-detail").empty();
	$("#cluster-info-detail").append("<td><strong>Cluster Detail</strong></td><td>"+inputData.detail+"</td>");
	
	//initial this table
	$("#cluster-host-show-info-form>tbody").empty();
	$("#cluster-host-show-info-form>tbody").append("<tr><th>Host name</th><th>Host IP</th><th>Status</th><th>Service installed</th><th>Introduction</th></tr>");               
	for(var i=0;i<inputData.hostsList.length;i++){	
		$("#cluster-host-show-info-form>tbody").append(
			"<tr><td>"+inputData.hostsList[i].name
			+"</td><td>"+inputData.hostsList[i].ip
			+"</td><td>"+inputData.hostsList[i].status
			+"</td><td>"+inputData.hostsList[i].services
			+"</td><td>"+inputData.hostsList[i].intro
			+"</td></tr>");
	}
};

function ajaxCheckClusterGetClusterInfo(clusterName){
	$.ajax({
		  data:{
		    "data" : clusterName
		  },
		  type:'POST',
		  async:true, 
		  url:'cluters-management/check-clusters',
		  dataType:'json',
		  success: function(returnData){
			  checkClusterInsertClustersInfo(returnData);
		  },
		  error:function(){
			  alert("error");
		  }
	});
};




