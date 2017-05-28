$(function(){	
	$("#sidebar-menu-create-plugin").click(function(){		
		//empty and reset main content header
		resetMainContentHeader("Plugins Management","Create Plugin");
		//empty old elements of main content
		$("#center-main-content").empty();

	});	
	
	
	$("#sidebar-menu-check-plugins").click(function(){		
		
		//empty and reset main content header
		resetMainContentHeader("Plugins Management","Check Plugins");
		//empty old elements of main content
		$("#center-main-content").empty();
		//load the html of pulugins list table
		syncAjaxInsertRow("#center-main-content","htmls/plugin-check-plugin-list-table.html");		
		//get plugins list and insert in the web
		ajaxCheckPluginsCheckPlugins();
	});	
	

	$("#sidebar-menu-execute-plugin").click(function(){		
		//empty and reset main content header
		resetMainContentHeader("Plugins Management","Execute Plugin");
		//empty old elements of main content
		$("#center-main-content").empty();
		syncAjaxInsertRow("#center-main-content","htmls/plugin-execute-plugin-set-cluster-plugin-form.html");		

		ajaxExecutePluginGetClustersList();
		ajaxExecutePluginGetPluginsList();

	});	
	
	
	
	
	$("#sidebar-menu-download-plugin").click(function(){		
		//empty and reset main content header
		resetMainContentHeader("Plugins Management","Download Plugin");
		//empty old elements of main content
		$("#center-main-content").empty();

	});	
	
	
	//remove after develop
	$("#sidebar-menu-test").click(function(){		
		//empty and reset main content header
		resetMainContentHeader("Plugins Management","Test");
		//empty old elements of main content
		$("#center-main-content").empty();
		ajaxTestData();

	});	
	

});


function ajaxTestData(){
	$.ajax({
		  type:'POST',
		  url:'test-data',
		  dataType:'json',
		  success: function(data){
			  alert(data);
		  },
		  error:function(){
			  alert("error");
		  }
	});

};


function ajaxCheckPluginsCheckPlugins(){
	$.ajax({
		  type:'POST',
		  url:'plugins-management/check-plugins',
		  dataType:'json',
		  success: function(data){
			  scanJsonData(data);
		  },
		  error:function(){
			  alert("error");
		  }
	});
};

function scanJsonData(data){
	  for(var i=0;i<data.length;i++){
		  //alert(data[i].name);
		  insertRow(i,data[i]);
	  }
};

function insertRow(i,obj){
	syncAjaxInsertRow("#plugins-list-table","htmls/plugin-check-plugin-set-list-table-row.html");
	$(".panel.box:last .col-intro div:eq(0)").append(obj.name);
	$(".panel.box:last .col-intro div:eq(1)").append(obj.intro);
	$(".panel.box:last #plugin-detail-row div:first").append(obj.detail);
	resetTagAttribute(".panel.box:last .col-intro a","data-target","#plugin-detail-row"+i);
	resetTagAttribute(".panel.box:last .panel-collapse.collapse:eq(0)","id","plugin-detail-row"+i);
	resetTagAttribute(".panel.box:last .col-option .btn-group button:eq(1)","data-target","#plugin-option-row"+i);
	resetTagAttribute(".panel.box:last .panel-collapse.collapse:eq(1)","id","plugin-option-row"+i);
};

function ajaxExecutePluginGetClustersList(){
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
				  executePluginInsertClusterList("#execute-plugin-cluster-list-select",returnData);			
				  $("#execute-plugin-cluster-list-select>option").click(function(){
					  //alert($("#execute-plugin-cluster-list-select>option:selected").text());
					  ajaxExecutePluginGetClusterInfo($("#execute-plugin-cluster-list-select>option:selected").text());
				  });	
			  }
		  },
		  error:function(){
			  alert("error");
		  }
	});
};

function executePluginInsertClusterList(tag,inputData){
	$(tag).empty();
	for(var i=0;i<inputData.length;i++){
		$(tag).append("<option>"+inputData[i]+"</option>");
	}
};

function ajaxExecutePluginGetClusterInfo(clusterName){
	$.ajax({
		  data:{
		    "data" : clusterName
		  },
		  type:'POST',
		  async:true, 
		  url:'cluters-management/check-clusters',
		  dataType:'json',
		  success: function(returnData){
			  executePluginInsertClusterInfo(returnData);
		  },
		  error:function(){
			  alert("error");
		  }
	});
};

function executePluginInsertClusterInfo(inputData){
	$("#execute-plugin-host-info-form>tbody").empty();
	$("#execute-plugin-host-info-form>tbody").append("<tr><th>Plugin Target</th><th>Host Name</th><th>Host Introduction</th></tr>");
	for(var i=0;i<inputData.hostsList.length;i++){	
		$("#execute-plugin-host-info-form>tbody").append(
				"<tr><td><span><input type=\"checkbox\">Execute plugin on this host</span>"		
				+"</td><td>"+inputData.hostsList[i].name
				+"</td><td>"+inputData.hostsList[i].intro
				+"</td></tr>");
	}
};

function ajaxExecutePluginGetPluginsList(){
	$.ajax({
		  type:'POST',
		  url:'plugins-management/check-plugins',
		  dataType:'json',
		  success: function(data){
			  executePluginInsertPluginList("#execute-plugin-plugin-list-select",data);
		  },
		  error:function(){
			  alert("error");
		  }
	});
};

function executePluginInsertPluginList(tag,inputData){
	$(tag).empty();
	for(var i=0;i<inputData.length;i++){
		$(tag).append("<option>"+inputData[i].name+"</option>");
	}
};





