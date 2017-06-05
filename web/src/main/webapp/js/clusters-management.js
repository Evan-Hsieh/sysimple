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
		
		syncAjaxInsertRow("#center-main-content","htmls/cluster-monitor-cluster-chart.html");
		
		
		checkClusterMonitorCluster("ws://localhost:3000/socket/monitors/");
		$("#monitor-cluster-connect-websocket").trigger("click");
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
					$("#cluster-list-select").change(function(){
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


function checkClusterMonitorCluster(url){
	var cpuChart = echarts.init($("#monitor-cluster-cpu-chart").get(0));
	var memoryChart = echarts.init($("#monitor-cluster-memory-chart").get(0));
	var diskChart = echarts.init($("#monitor-cluster-disk-chart").get(0));
	var netChart = echarts.init($("#monitor-cluster-net-chart").get(0));	
	monitorClusterInitDrawCpuChart(cpuChart);
	monitorClusterInitDrawMemoryChart(memoryChart);
	monitorClusterInitDrawDiskChart(diskChart);
	monitorClusterInitDrawNetChart(netChart);
	var cpuData=[];
	var memoryData=[];
	var diskData=[];
	var receiveNetData=[];
	var sendNetData=[];
	var socket;
	
	if(typeof(WebSocket) == "undefined") {
		alert("Your browser doesn't support WebSocket");
		return;
	}

	$("#monitor-cluster-connect-websocket").click(function() {
		//alert("url:"+url);
		socket = new WebSocket(url);
		//Open websocket
		socket.onopen = function() {
			//alert("Socket has opened");
		};
		//get msg
		socket.onmessage = function(msg) {
			//alert(msg.data);
			var curtDate = updateNewDate();
			//alert(curtDate);
			updateCpuChart(cpuChart,msg,cpuData,curtDate);
			updateMemoryChart(memoryChart,msg,memoryData,curtDate);
			updateDiskChart(diskChart,msg,diskData,curtDate);
			updateNetChart(netChart,msg,receiveNetData,sendNetData,curtDate);
		};
	
		//Close
		socket.onclose = function() {
			//alert("Socket has closed");
		};
	  
		//Error
		socket.onerror = function() {
			alert("websocket error");
		}
	});

	//Close
	$("#monitor-cluster-close-websocket").click(function() {
		socket.close();
	});
};

//cpu
function monitorClusterInitDrawCpuChart(cpuChart){
	 var option = {
	            tooltip: {},
	            xAxis: {
	                type: 'time',
	                splitLine: {
	                    show: false
	                }
	            },
	            yAxis: {
	                type: 'value',
	                boundaryGap: [0, '10%'],
	                splitLine: {
	                    show: false
	                }
	            },
	            series: [{
	                name: 'cpuSeries',
	                type: 'line',
	                itemStyle: {normal: {color:'rgb(243,156,18)'}},
	                lineStyle: {normal: {color:'rgb(243,156,18)'}},
	                areaStyle: {normal: {color:'rgb(243,156,18)'}},
	                data: []
	            }]
	        };
	 cpuChart.setOption(option);
}



//memory
function monitorClusterInitDrawMemoryChart(memoryChart){
	 var option = {
	            tooltip: {},
	            xAxis: {
	                type: 'time',
	                splitLine: {
	                    show: false
	                }
	            },
	            yAxis: {
	                type: 'value',
	                boundaryGap: [0, '20%'],
	                splitLine: {
	                    show: false
	                }
	            },
	            series: [{
	                name: 'memorySeries',
	                type: 'line',
	                itemStyle: {normal: {color:'rgb(0,128,128)'}},
	                lineStyle: {normal: {color:'rgb(0,128,128)'}},
	                areaStyle: {normal: {color:'rgb(0,128,128)'}},
	                data: []
	            }]
	        };
	 memoryChart.setOption(option);
}

//disk
function monitorClusterInitDrawDiskChart(diskChart){
	 var option = {
	            tooltip: {},
	            xAxis: {
	                type: 'time',
	                splitLine: {
	                    show: false
	                }
	            },
	            yAxis: {
	                type: 'value',
	                boundaryGap: [0, '20%'],
	                splitLine: {
	                    show: false
	                }
	            },
	            series: [{
	                name: 'diskSeries',
	                type: 'line',
	                itemStyle: {normal: {color:'rgb(128,128,255)'}},
	                lineStyle: {normal: {color:'rgb(128,128,255)'}},
	                areaStyle: {normal: {color:'rgb(128,128,255)'}},
	                data: []
	            }]
	        };
	 diskChart.setOption(option);
}
//net
function monitorClusterInitDrawNetChart(netChart){
	 var option = {
	            tooltip: {},
	            xAxis: {
	                type: 'time',
	                splitLine: {
	                    show: false
	                }
	            },
	            yAxis: {
	                type: 'value',
	                boundaryGap: [0, '20%'],
	                splitLine: {
	                    show: false
	                }
	            },
	            series: [{
		                name: 'receiveSeries',
		                type: 'line',
		                itemStyle: {normal: {color:'rgb(212,130,101)'}},
		                lineStyle: {normal: {color:'rgb(212,130,101)'}},
		                areaStyle: {normal: {color:'rgb(212,130,101)'}},
		                data: []
	            	},
	            	{
		                name: 'sendSeries',
		                type: 'line',
		                itemStyle: {normal: {color:'rgb(145,199,174)'}},
		                lineStyle: {normal: {color:'rgb(145,199,174)'}},
		                areaStyle: {normal: {color:'rgb(145,199,174)'}},
		                data: []
		            }	
	            ]
	        };
	 netChart.setOption(option);
}

function updateNewDate(){
    var now=new Date();
    var year=now.getFullYear();
    var month=now.getMonth() + 1;
    var day= now.getDate();
    var hour=now.getHours();
    var minute=now.getMinutes();
    var second=now.getSeconds();
    if(month<10){
    	month="0"+month;
    }
    if(day<10){
    	day="0"+day;
    }
    if(hour<10){
    	hour="0"+hour;
    }
    if(minute<10){
    	minute="0"+minute;
    }
    if(second<10){
    	second="0"+second;
    }
    
    return year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
}


function updateCpuChart(cpuChart,msg,cpuData,curtDate){
	 var tmpData = eval('(' + msg.data + ')');
     var cpuRatio = tmpData.cpuRatio;
     if(cpuData.length>=30){
      	  cpuData.shift();
        }
     cpuData.push([curtDate,cpuRatio]);   
     cpuChart.setOption({
         series: [{
       	  	 name: 'cpuSeries',
             data: cpuData
         }]
     });
}

function updateMemoryChart(memoryChart,msg,memoryData,curtDate){
	var tmpData = eval('(' + msg.data + ')');
    var memoryRatio = parseFloat(tmpData.memoryUsed)/parseFloat(tmpData.memoryTotal);
    if(memoryData.length>=30){
    	memoryData.shift();
       }
    memoryData.push([curtDate,memoryRatio]);   
    memoryChart.setOption({
        series: [{
      	  	name: 'memorySeries',
            data: memoryData
        }]
    });
}

function updateDiskChart(diskChart,msg,diskData,curtDate){
	var tmpData = eval('(' + msg.data + ')');
    var diskRatio = parseFloat(tmpData.fsUsed)/parseFloat(tmpData.fsTotal);
    if(diskData.length>=30){
    	diskData.shift();
       }
    diskData.push([curtDate,diskRatio]);   
    diskChart.setOption({
        series: [{
      	  	name: 'diskSeries',
            data: diskData
        }]
    });
}

function updateNetChart(netChart,msg,receiveNetData,sendNetData,curtDate){
	var tmpData = eval('(' + msg.data + ')');
    var receiveFlow = tmpData.netStatus[0].getKb;
    if(receiveNetData.length>=30){
    	receiveNetData.shift();
    }
    receiveNetData.push([curtDate,receiveFlow]);   
    netChart.setOption({
        series: [{
      	  	name: 'receiveSeries',
            data: receiveNetData
        }]
    });
    
    var sendFlow = tmpData.netStatus[0].sendKb;
    if(sendNetData.length>=30){
    	sendNetData.shift();
    }
    sendNetData.push([curtDate,sendFlow]);   
    netChart.setOption({
        series: [{
      	  	name: 'sendSeries',
            data: sendNetData
        }]
    });
    
    
}




