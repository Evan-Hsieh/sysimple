$(function(){	
	$("#sidebar-menu-check-plugins").click(function(){		
		//empty old elements
		$("#center-main-content").empty();
		//load the html of pulugins list table
		syncAjaxInsertRow("#center-main-content","htmls/component-plugin-list-table.html");		
		//get plugins list and insert in the web
		ajaxCheckPlugins();
	});	
	
	$("#sidebar-menu-check-cluster").click(function(){
		alert("cluster");
		//resetTagAttribute(".panel.box:last .col-intro div:first","class","col-md-6");
	});	
});



function ajaxCheckPlugins(){
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
	syncAjaxInsertRow("#plugins-list-table","htmls/component-plugin-list-table-row.html");
	$(".panel.box:last .col-intro div:eq(0)").append(obj.name);
	$(".panel.box:last .col-intro div:eq(1)").append(obj.intro);
	$(".panel.box:last #plugin-detail-row div:first").append(obj.detail);
	resetTagAttribute(".panel.box:last .col-intro a","href","#plugin-detail-row"+i);
	resetTagAttribute(".panel.box:last .panel-collapse.collapse:eq(0)","id","plugin-detail-row"+i);
	resetTagAttribute(".panel.box:last .col-option a","href","#plugin-option-row"+i);
	resetTagAttribute(".panel.box:last .panel-collapse.collapse:eq(1)","id","plugin-option-row"+i);
};



