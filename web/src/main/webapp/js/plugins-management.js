$(function(){	
	$("#sidebar-menu-check-plugins").click(function(){		
		//load the html of pulugins list table
		$("#center-main-content").empty();
		syncAjaxInsertRow("#center-main-content","htmls/component-plugin-list-table.html");		
		ajaxCheckPlugins();
	});	
	
	$("#sidebar-menu-check-cluster").click(function(){
		alert($("#center-main-content").html());
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
		  insertRow(data[i]);
	  }
};

function insertRow(obj){
	syncAjaxInsertRow("#plugins-list-table","htmls/component-plugin-list-table-row.html");
	$(".box-primary:last .col-intro div:eq(0)").append(obj.name);
	$(".box-primary:last .col-intro div:eq(1)").append(obj.intro);
	$(".box-primary:last #plugin-detail-row div:first").append(obj.detail);
};

//correct function!!!
function syncAjaxInsertRow(domObj,url){
	$.ajax({
		  type:'POST',
		  url:url,
		  async:false, 
		  dataType:'html',
		  success: function(data){
			  $(domObj).append(data);
		  },
		  error:function(){
			  alert("error");
		  }
	});
}

