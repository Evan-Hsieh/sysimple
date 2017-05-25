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
};

//correct function!!!
function asyncAjaxInsertRow(domObj,url){
	$.ajax({
		  type:'POST',
		  url:url,
		  async:true, 
		  dataType:'html',
		  success: function(data){
			  $(domObj).append(data);
		  },
		  error:function(){
			  alert("error");
		  }
	});
};

//correct function!!!
function resetTagAttribute(tag,attrItem,newValue){
	$(tag).attr(attrItem,newValue);
};

//correct function!!!
function resetTagContent(tag,newContent){
	$(tag).empty();
	$(tag).append(newContent);
};

//correct function!!!
function resetMainContentHeader(title,subtitle){
	resetTagContent("#main-content-header h1",title+"<small></small>");
	resetTagContent("#main-content-header small",subtitle);	
}

