$(function(){
	$("#menu-plugins-management").click(function(){
		showPlugins();
	});
});

function showPlugins(){

	$.ajax({
		  type:'POST',
		  url:'plugins-management/show-plugins',
		  success: function(data){
			  alert(data);
		  }
	});

};