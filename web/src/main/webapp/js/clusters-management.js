$(function(){	
	$("#sidebar-menu-check-cluster").click(function(){
		//alert("check cluster");	
		resetMainContentHeader("Cluster Management","Check Cluster");
		//empty old elements of main content
		$("#center-main-content").empty();
		syncAjaxInsertRow("#center-main-content","htmls/cluster-check-cluster-info-form.html");
		syncAjaxInsertRow("#center-main-content","htmls/cluster-check-cluster-host-info-form.html");
		syncAjaxInsertRow("#center-main-content","htmls/cluster-check-cluster-host-specific-detail-tabform.html");		
	
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
			//show the form of host info 
			syncAjaxInsertRow("#center-main-content","htmls/cluster-create-cluster-set-host-info-form.html");
			//bind some button for clicking
			$("#create-cluster-submit-btn").click(function(){
				alert($(".input-cluster-info").serialize());
			});	
			$("#add-new-host-info-row-btn").click(function(){
				syncAjaxInsertRow("#create-cluster-host-info-form","htmls/cluster-create-cluster-set-host-info-row.html");
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


