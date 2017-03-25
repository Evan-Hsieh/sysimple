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
function resetTagAttribute(tag,attrItem,newValue){
	$(tag).attr(attrItem,newValue);
};