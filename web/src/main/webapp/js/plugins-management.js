$(function(){
	alert("bangding");
	$("#center-main-content").load("htmls/component-plugin-list-table.html");
});

function showPlugins(){

	$.ajax({
		  type:'POST',
		  url:'plugins-management/show-plugins',
		  success: function(data){
			  alert(data);
		  }
	});
	

	
	$("#center-main-content").click(function(){
		alert("123");
	});
	


};

