<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<%
	String root = request.getContextPath();
%>
<%
	//添加监听器
	///Container container = ServerContextFactory.get().getContainer();
//	ScriptSessionManager manager = container
	//		.getBean(ScriptSessionManager.class);
//	manager.addScriptSessionListener(new AddScriptSessionListener());
%>


<html>
	<head>
		<script type="text/javascript"
			src="http://maps.google.com/maps/api/js?sensor=false"> </script>
		<script type='text/javascript' src='<%=root%>/My97DatePicker/WdatePicker.js'></script>
		<script type="text/javascript" src="<%=root%>/scripts/Dateutil.js"></script>
		<script type='text/javascript' src='<%=root%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=root%>/dwr/util.js'></script>
		<script type='text/javascript'
			src='<%=root%>/dwr/interface/dwrCarService.js'></script>
		<style type="text/css">
		
.search{
  margin-top:0px;
  margin-bottom:5px;
  width: 1325;
  height:50px;
  line-height:50px;
  background-color: #f5f5f5;
  vertical-align: middle;
  display: inline;
  overflow:hidden;
  position:relative;
  
}
.search_txt{
    height: 30px;
    width: 150px;
    margin-left: 8px;
    margin-top: 10px;
     margin-bottom: 10px;
    font-size: 20px;
    color: #CDD0D3;
    float:left; 
}
.search_txt2{
    height: 30px;
    width: 150px;
    margin-left: 8px;
    margin-top: 10px;
    font-size: 20px;
    margin-bottom: 10px;
    color: #0000FF;
    float:left; 
}
.submit{
  width: 75px;
  height: 30px;
  margin-top: 10px;
  margin-left: 10px;
  vertical-align:middle;
}
.divmain{
 width: 100%;
  height:100%;
}
.divleft{
 width:21%;
 height:100%;
 float:left; 
}
.map_canvas {
    margin:0px;
	width: 78%;
	height: 100%;
	float:right;
}
.scrolldata{
   border-right:black 1px solid;
   border-top: black 1px solid;  
   border-left: black 1px solid; 
   border-bottom: black 1px solid;
   scrollbar-face-color: #889b9f; 
   scrollbar-highlight-color: #c3d6da;
   overflow: auto;
   width: 100%; 
   scrollbar-shadow-color: #3d5054; 
   scrollbar-3dlight-color: #3d5054; 
   scrollbar-arrow-color: #ffd6da;
   scrollbar-darkshadow-color: #85989c; 
    height: 100%;  
    
}
.datalist {
    height:20px;
    padding:0 5 5 0px;
    margin-right:5px;
	width: 100%;
	height:530px;
	text-align: center;
	 background-color: #f5f5f5;
	float: left;
}
.tb_css{
   width: 100%;
   text-align:center;
   background-color: #CDD0D3;
}
.dl_title {
    height:30px;
    text-align:left;
	background-color: #87CEEB;
}
.dl_title1 {
    height:20px;
    text-align: left;
    background-color:expression(rowIndex%2==0?'#DODODO':'#FFFFFF') ;
	/**background-color: #ffffff;*/
}
.dl_title2 {
    height:20px;
    text-align:center;
	background-color: #f5f5f5;
}



		/**
			* 弹出层css
			*/
			.pop {
				width: 80%;
				border: 1px #96D1DF solid;
				background: #fff;
				padding: 1px;
				color: #333;
				position: absolute;
				top: 15%;
				left: 15%;
				height: auto;
				z-index: 10
			}
			
			/**
			* 弹出层css
			*/
			.pop_ {
				width: 80%;
				border: 1px #96D1DF solid;
				background: #fff;
				padding: 1px;
				color: #333;
				position: absolute;
				top: 15%;
				left: 15%;
				height: auto;
				z-index: 10
			}
			
			.pop_title {
				float: left;
				width: 100%;
				height: 24px;
				line-height: 24px;
				/* background: #ecf9ff url ('../images/title_move.gif') center no-repeat; */
				background: #ecf9ff;
				border-bottom: 1px #d3ecf3 solid;
				color: #444;
				font-weight: bold
			}
			
			.pop_title_left {
				float: left;
				width: 90%;
				overflow: hidden;
				text-indent: 6px;
				cursor: move
			}
			
			.pop_title_left img {
				float: left;
				border: 0;
				margin: 10px 0 0 5px
			}
			
			.pop_title_right {
				width: 5%;
				float: right;
				text-align: right;
				cursor: pointer
			}
			
			.pop_title_right_print {
				width: 3%;
				float: left;
				text-align: right
			}
			
			.pop_title_right img {
				margin: 5px 10px 5px 10px;
				cursor: pointer
			}
			
			.pop_title_right_print img {
				margin: 5px 10px 5px 10px;
				cursor: pointer
			}
			
			.pop_content {
				float: left;
				width: 100%;
				margin: 1px 0 50 0;
				font-size: 14px
			}
			
			.pop_function {
				float: left;
				width: 100%;
				height: 30px;
				line-height: 30px;
				border-top: 1px #fff solid;
				text-align: center
			}
			
			.pop_search {
				width: 100%;
				height: 35px;
				border-top: 1px #fff solid;
				text-align: center;
				font-size: 12px;
				font-weight: bold;
				margin: 5px 0 5px 0;
				border-bottom: 3px #96D1DF dotted;
			}
			
			.pop_page {
				width: 100%;
				height: 25px;
				text-align: center;
				font-size: 12px;
				border-top: 3px #96D1DF dotted;
				vertical-align: middle;
			}

</style>
	</head>
	<!-- 通过 dwr.engine.setActiveReverseAjax(true); 启动该页面的Reverse Ajax功能  -->
	<body onload="initialize()">
		<script type="text/javascript">
	var now = DateUtil.Format("yyyy-MM-dd hh:mm:ss",new Date());
	var today = DateUtil.Format("yyyy-MM-dd",new Date());
	
    var map;
    var geocoder;
    var address="未获取到位置信息";
    var temp;
    var locations = {};
    var myLatlng = new google.maps.LatLng(22.69058208, 113.63173943); 
    var markersArray = [];
    var latlngs = []; 
 	var  makerLineInfo=[];  
    var polyline=[];
    function initialize() {
  //  dwr.engine.setActiveReverseAjax(true);
   // dwr.engine.setErrorHandler(function(){});
      var myOptions = {
	    zoom: 11,
	    center: myLatlng,
	     mapTypeId: google.maps.MapTypeId.ROADMAP  
	  };
	  map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	  google.maps.event.addListener(map,"click", function(event) {       
       });  
	  geocoder= new  google.maps.Geocoder();
	  getDataFromServer();
    }
    function getCarGpsData(carNo,carId){
   	 temp=carNo;
     dwrCarService.findByCarNo(carNo,carId,{  
           callback: getCarGpsDataServerCallBack  
         }); 
    }  
	function getCarGpsDataServerCallBack(data){
	  var sno="";
	  var lat="";
	  var lon="";
      var plate = "";
      var vkey = "";
	if(data!=null){
		lon=data.lon;
		lat=data.lat;
		plate =data.plate;
		vkey=data.vkey;
		deleteOverlays();
		var recivetime =DateUtil.Format("yyyy-MM-dd hh:mm:ss",data.recvtime);
		var gpstime  =DateUtil.Format("yyyy-MM-dd hh:mm:ss",data.gpstime);   
		var latlng = new google.maps.LatLng(parseFloat(lat),parseFloat(lon));
		
		geocoder= new google.maps.Geocoder();
		    
	    geocoder.geocode({'latLng':latlng},function(result,status){
	        if (status != google.maps.GeocoderStatus.OK) {
	            address = "抱歉具体位置信息尚不明确";
	            showCarInfo(latlng,data,recivetime,gpstime)
	        }
	        else{
	            address = result[0].formatted_address;
	            showCarInfo(latlng,data,recivetime,gpstime)
	        }
	    });
	}else{
	alert("没有车【"+temp+"】的GPS信息");
	}
   }
   function showCarInfo(latlng,data,recivetime,gpstime){
	  var marker =  new google.maps.Marker( {
		 	map :map, //定义在map中显示标记。
			position : latlng
			});
	  var infowindow = new google.maps.InfoWindow();
      var html = "<div> <table><caption align='center' ><b>GPS车辆信息</b></caption>"
       		 +"<tr><td colspan='2'><font color=red>位置:"+  address+ "</font></td></tr>"
      		 +"<tr><td>Plate:</td><td>"+  data.plate+ "("+data.platecolor+") </td></tr>"
             + "<tr><td>Mile:</td><td>"+  data.mile+ " </li>" 
			 + "<tr><td>Recvtime:</td><td>"+  recivetime+ " </td></tr>"
			 + "<tr><td>Gpstime:</td><td>"+  gpstime+ " </td></tr>" 
			 + "<tr><td>Lat:</td><td>"+  data.lat+ " </td></tr>"
    	     + "<tr><td>Lon:</td><td>"+  data.lon+ " </td></tr>" 
			 + "<tr><td>Height:</td><td>"+  data.height+ " </td></tr>"
			 + "<tr><td>Speed:</td><td>"+  data.speed+ " </td></tr>"
			 + "<tr><td>GpsSpeed:</td><td>"+  data.gpsSpeed+ " </td></tr>"
   			 + "<tr><td>Dir:</td><td>"+  data.dir+ " </td></tr>" 
			 + "<tr><td>Eff:</td><td>"+  data.eff+ " </td></tr>"
    		 + "<tr><td>Alarm:</td><td>"+  data.alarm+ " </td></tr>" 
			 + "<tr><td>Run:</td><td>"+  data.run+ " </td></tr>"
			 + "<tr><td>GetMode:</td><td>"+  data.getMode+ " </td></tr>"
			 + "<tr><td>TagChar:</td><td>"+  data.tagChar+ " </td></tr>"
			 + "<tr><td>Protocol:</td><td>"+  data.protocol+ " </td></tr>"
			 + "<tr><td>Provider:</td><td>"+  data.provider+ " </td></tr>"
     		+  "</table></div>";
      
      infowindow.setContent(html);
         
	 google.maps.event.addListener(marker, 'click', function(event) {
	    infowindow.open(map, marker);
	  });
	   infowindow.open(map, marker);
	    markersArray.push(marker);
   }
   
   function searchCarByCarNo(){
	  var txt = document.getElementById("search_txt").value;
	  if(txt == "请输入车牌号" ){
	     dwrCarService.listCarNo({  
           callback: getDataFromServerCallBack  
         }); 
	    }else{
	    	dwrCarService.getListByCarNo(txt,{  
	           callback: getDataFromServerCallBack  
	         }); 
	    }
    }
    
   function getDataFromServer() {  
       dwrCarService.listCarNo({  
           callback: getDataFromServerCallBack  
       });  
    }  
   function getDataFromServerCallBack(dataFromServer) {  
       	var html ="<table class='tb_css'  cellpadding='0' cellspacing='1' border='0'>";
    	 	html+="<tr class='dl_title'><td>序号</td> <td>车牌号码</td><td>车辆轨迹</td></tr>";
        if(dataFromServer.length>0){
    	  for(var i=0;i<dataFromServer.length;i++){
			html = html+ "<tr class='dl_title1'><a href='javascript:getCarGpsData("+dwr.util.toDescriptiveString(dataFromServer[i].plate)+","+dataFromServer[i].id+")'><td>"+dataFromServer[i].id+"</td>"
			//+"<td>"+dataFromServer[i].plate+"</td></a><td><a href='javascript:showGpsPolyline("+dwr.util.toDescriptiveString(dataFromServer[i].plate)+")'><font color='#333CCC'>查看轨迹 </font></a></td></tr>";
         	+"<td>"+dataFromServer[i].plate+"</td></a><td><a href='javascript:showMessageDag("+dataFromServer[i].id+","+dwr.util.toDescriptiveString(dataFromServer[i].plate)+")'><font color='#333CCC'>查看轨迹 </font></a></td></tr>";
          }
        }else{
        html+="<tr class='dl_title1'><td colspan='3'> 没有车辆数据</td></tr>";
        }
       html = html +"</table>";
       document.getElementById("gps_content").innerHTML=html;
       
       }  
       
   function showGpsPolyline(id,carNo,startdate,enddate){
   var data ={
	   "id":id,
	   "carNo":carNo,
	   "startdate":startdate,
	   "enddate":enddate
	   };
   		 dwrCarService.getGpsPolylineData(data,{  
           callback: getPolylingDataServerCallBack  
         }); 
   }  
    function getPolylingDataServerCallBack(sdata){
   	 	latlngs.length=0;
 	 	deleteOverlays();
 	 	//线点
     	for (var i = 0; i < sdata.length; i++) {  
           var lat = sdata[i].lat;  
           var lng = sdata[i].lon;
         //  var gpstime  =DateUtil.Format("yyyy-MM-dd hh:mm:ss",sdata[i].gpstime);  
           var latlng = new google.maps.LatLng(parseFloat(lat),parseFloat(lng));
           latlngs[i] =  latlng;
              
          
      }  
      //标记点
      // var spitlen= sdata.length/20;
     if(sdata.length>0){
	     if(sdata.length>1){
	     	 pushData(sdata[0]);
	    	 pushData(sdata[sdata.length-1]);
	     }else{
	    	 pushData(sdata[0]);
	     }
     }
      /**	for (var i = 0; i < sdata.length; i++) {  
	      	 if(spitlen>0){
		       	if(i%spitlen==0){
		      	  alert('yy'+i);
		      	}
	        	}
           var lat = sdata[i].lat;  
           var lng = sdata[i].lon;
           var gpstime  =DateUtil.Format("yyyy-MM-dd hh:mm:ss",sdata[i].gpstime);  
           var latlng = new google.maps.LatLng(parseFloat(lat),parseFloat(lng));
              
           var store = {latlng: latlng, plate: sdata[i].plate, vkey:sdata[i].VTKey,lat:lat,lng:lng,gpstime:gpstime};
           var latlngHash = (latlng.lat().toFixed(6) + "" + latlng.lng().toFixed(6));
           latlngHash = latlngHash.replace(".","").replace(".", "").replace("-","");
          if (locations[latlngHash] == null) {
            locations[latlngHash] = []
          }
          locations[latlngHash].push(store);  
     } */
      /**for (var latlngHash in locations) {
          var stores = locations[latlngHash];
          if (stores.length > 1) {
           createClusteredMarker(stores);
          } else {
           createMarker(stores);
          }
      };*/ // 移至画线后操作
   	  Drawtrack();
   	  if(sdata.length>0){
   	 	 map.panTo(new google.maps.LatLng(parseFloat(sdata[0].lat),parseFloat(sdata[0].lon)));
   	  }
    } 
    
       //绘制轨迹   
    function Drawtrack() { 
    //  alert(latlngs.length);
        //画轨迹   
          var polylineoption = {  
              strokeColor: "#0914f6",  
               strokeOpacity: 1.0,  
               strokeWeight: 3,  
              path: latlngs  
          };  
          polyline[0] = new google.maps.Polyline(polylineoption);  
          polyline[0].setMap(map); 
        //  alert("得到画点："+latlngs.length);
         for(i=0;i<latlngs.length;i++){
         	//alert(latlngs[i].lat());
      ///   if(i%10==0){
         		var latlngHash = (latlngs[i].lat().toFixed(6) + "" + latlngs[i].lng().toFixed(6));
         		for (var latlngHash in locations) {
		          var stores = locations[latlngHash];
		          if (stores.length > 1) {
		           createClusteredMarker(stores);
		          } else {
		           createMarker(stores);
		          }
		      }
        // 	}
         }
         	// var latlngHash = (latlngs[i].lat().toFixed(6) + "" + latlngs[i].lng().toFixed(6));
         	 
         	/** for (var latlngHash in locations) {
		          var stores = locations[latlngHash];
		          if (stores.length > 1) {
		           createClusteredMarker(stores);
		          } else {
		           createMarker(stores);
		          }
		      };*/
         
          
          
      }   
      
   function pushData( gdata){
    	var lat = gdata.lat;  
        var lng = gdata.lon;
        var gpstime  =DateUtil.Format("yyyy-MM-dd hh:mm:ss",gdata.gpstime);  
        var latlng = new google.maps.LatLng(parseFloat(lat),parseFloat(lng));
        var store = {latlng: latlng, plate: gdata.plate, vkey:gdata.VTKey,lat:lat,lng:lng,gpstime:gpstime};
   	    var latlngHash = (latlng.lat().toFixed(6) + "" + latlng.lng().toFixed(6));
        latlngHash = latlngHash.replace(".","").replace(".", "").replace("-","");
        if (locations[latlngHash] == null) {
            locations[latlngHash] = []
        }
        locations[latlngHash].push(store);
   
   }
   function createMarker(stores) {
      var store = stores[0];
     // var newIcon = MapIconMaker.createMarkerIcon({width: 32, height: 32, primaryColor: "#00ff00"});
      var marker =  new google.maps.Marker( {
				map :map, //定义在map中显示标记。
				position :store.latlng
				});
	  markersArray.push(marker);
	  var infowindow = new google.maps.InfoWindow();
      var html ="<b>plate:"+ store.plate+ "</b><br/>"+ "<b>vkey:" + store.vkey +"</b> <br/>" + "时间:"+ store.gpstime +" <br/>" ;
      
      infowindow.setContent(html);
      google.maps.event.addListener(marker, 'click', function(event) {
	    infowindow.open(map, marker);
	  });
     // return marker;
    }

    function createClusteredMarker(stores) {
         // var newIcon = MapIconMaker.createMarkerIcon({width: 44, height: 44, primaryColor: "#00ff00"});
      var marker = new google.maps.Marker( {
		  map :map, //定义在map中显示标记。
		  position :stores[0].latlng
	   });
	   markersArray.push(marker);	
	  var infowindow = new google.maps.InfoWindow();
	  
      var html = "";
      for (var i = 0; i < stores.length; i++) {
        if(i==0){
          html +="<b>plate:"+ stores[i].plate+ "</b><br/>"+
        "<b>vkey:"+ stores[i].vkey+"</b><br/>"+
        "<b>时间：</b>" + stores[i].gpstime+ "<br/>";
        }else{
         html +=  "<b>时间：</b>" + stores[i].gpstime+ "<br/>";
        }
      }
      infowindow.setContent(html);
      google.maps.event.addListener(marker, 'click', function(event) {
	    infowindow.open(map, marker);
	  });
     // return marker;
    }
    function  setcenter(lat,lng){
       map.panTo(new google.maps.LatLng(lat,lng));
	   var latlng =new google.maps.LatLng(lat,lng);
	   for(var i=0 ; i< markersArray.length;i++){
		  if(markersArray[i].getPosition().equals(latlng)){
		 markersArray[i].setMap(map);
		   google.maps.event.trigger(markersArray[i],'click')
		   
		  }
	   }
    }
    

    
     function deleteOverlays() {
    locations = {};
	  if (markersArray) {
	    for (i in markersArray) {
	      markersArray[i].setMap(null);
	    }
	    markersArray.length = 0;
	    for(i in polyline){
	    // polyline[i].setVisible(false);
	    polyline[i].setMap(null);
	    }
	    polyline.length=0;
	  }
	}

    function cleartxt(){
      var focs_val = document.getElementById("search_txt").value;
      if(focs_val == "请输入车牌号" ){
        document.getElementById("search_txt").value = "";
        document.getElementById("search_txt").className="search_txt2";
      }
    }
    
    function inputtxt(){
      var txt = document.getElementById("search_txt").value;
      if(txt ==""){
        document.getElementById("search_txt").value = "请输入车牌号";
         document.getElementById("search_txt").className="search_txt";
      }
    }
    </script>
		<div class="divmain">
			<div class="divleft">
				<div class="scrolldata" >
					<div>
					<!--
						<div>
							<input type="text" id="startdate" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分ss秒'})" class="Wdate" style="width:200px"/>	
							<input type="text" id="enddate" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
						</div>	
					--->
						<div><input type="text" id="search_txt" class="search_txt" value="请输入车牌号" onfocus="cleartxt();" onblur="inputtxt();"><input src="images/2-2.jpg"   type="image" class="submit" onclick="searchCarByCarNo();"></div>
<!--				    	<div style="margin-top: 10px; text-align:center" ><b>车辆列表</b></div>-->
						<div id ="gps_content"></div>
					</div>
				</div> 
			 </div>
			<div id="map_canvas" class="map_canvas"></div>
		</div>
	<script type="text/javascript">
			function getxy(e) {
				var a = new Array();
				var t = e.offsetTop;
				var l = e.offsetLeft;
				var w = e.offsetWidth;
				var h = e.offsetHeight;
				while (e = e.offsetParent) {
					t += e.offsetTop;
					l += e.offsetLeft;
				}
				a[0] = t;
				a[1] = l;
				a[2] = w;
				a[3] = h;
				return a;
			}
			//----------------------------------
			var DragForAll = {};
			function _enableDragForAll(e, toMoveObj, obj2, obj3) {
				if (DragForAll.o) {
					return false;
				}
				var clickObj = document.getElementById(toMoveObj);
				if (!clickObj) {
					return;
				}
				DragForAll.o = clickObj;
				if (document.getElementById(obj2)) {
					DragForAll.p = document.getElementById(obj2);
				}
				DragForAll.xy = getxy(DragForAll.o);
				e = e || event;
				if (!clickObj.style.left) {
					DragForAll.xx = new Array((e.x - DragForAll.xy[1]), (e.y - DragForAll.xy[0]));
				} else {
					DragForAll.xgap = parseInt(clickObj.style.left.substring(0, clickObj.style.left.indexOf("px")));
					DragForAll.ygap = parseInt(clickObj.style.top.substring(0, clickObj.style.top.indexOf("px")));
					DragForAll.xx = new Array((e.x - DragForAll.xgap), (e.y - DragForAll.ygap));
					DragForAll.xgap -= DragForAll.xy[1];
					DragForAll.ygap -= DragForAll.xy[0];
				}
				DragForAll.fitToCont = obj3;
				if (obj3) {
					DragForAll.fitArea = getxy(DragForAll.fitToCont);
				}
				return false;
			}
			function _DragObjForAll(e) {
				if (!DragForAll.o) {
					return;
				}
				e = e || event;
				var old_left = e.x - DragForAll.xx[0];
				var old_top = e.y - DragForAll.xx[1];
				if (DragForAll.fitToCont) {
					if ((old_left - DragForAll.xgap) - DragForAll.fitArea[1] <= 0 || old_top - DragForAll.ygap - DragForAll.fitArea[0] <= 0) {
						return;
					}
					if (old_left - DragForAll.xgap + DragForAll.xy[2] >= DragForAll.fitArea[1] + DragForAll.fitArea[2] || old_top - DragForAll.ygap + DragForAll.xy[3] >= DragForAll.fitArea[0] + DragForAll.fitArea[3]) {
						return;
					}
				}
				DragForAll.o.style.left = old_left + "px";
				DragForAll.o.style.top = old_top + "px";
				if (DragForAll.p) {
					DragForAll.p.style.left = (old_left + 5) + "px";
					DragForAll.p.style.top = (old_top + 5) + "px";
				}
			}
			function _releaseDragObjForAll(e) {
				DragForAll = {};
			}
			document.onmousemove = function (e) {
				_DragObjForAll(e);
			};
			document.onmouseup=function(e){
				_releaseDragObjForAll(e);
				e=e||event;	
			}
		  function showMessageDag(id,carNo){
		    document.getElementById("datetime1").value =today+" 00:00:00";
	 	    document.getElementById("datetime2").value=now;
			document.getElementById("carid").value=id; 
			document.getElementById("carNo").value=carNo;
	 		   var message =document.getElementById("xingZQYTree");
		       message.style.display='';
		       //var h=(document.documentElement.clientWidth-document.getElementById("xingZQYTree").clientWidth)/2;
		     //  var x= (document.documentElement.clientHeight-document.getElementById("xingZQYTree").clientHeight)/2 ;
		     //  message.style.top=h+"px";
		     //  message.style.left=x+"px";
	   	  }
		 function showValue(){
			  var carid= document.getElementById("carid").value;
			  var carNo= document.getElementById("carNo").value;
		      var v1 =document.getElementById("datetime1").value;
		      var v2 =document.getElementById("datetime2").value;
		      document.getElementById("xingZQYTree").style.display='none';
		      //alert(v1+v2+carid+carNo);
		      showGpsPolyline( carid,carNo,v1,v2);
	   	 }
		</script>	
		
	   <div id="xingZQYTree" class="pop"
			style="display: none; width: 480px; overflow: auto; position: absolute; background-color: #FFFFFF; border: 1px solid #0099CC; padding-left: 0px">
			<div id="pop_title" class="pop_title"
				onmousedown="_enableDragForAll(event,'xingZQYTree');" title='按住鼠标可随意拖动此窗口'>
				<div class="pop_title_left" style="font-size: 14px">
					&nbsp;&nbsp;选择时间
				</div>
				<div class="pop_title_right">
					<label title="关闭此窗口" onclick="xingZQYTree.style.display='none';">
						&nbsp;X&nbsp;
					</label>
				</div>
			</div>
			<div class="pop_content">
				 <div>
				 <input type="hidden" name="carid"/>
				 <input type="hidden" name="carNo"/>
					<input type="text" id="datetime1" value='' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:159px;margin-left: 20px;margin-top: 15px;"/>	
					&nbsp;至&nbsp;<input type="text" id="datetime2" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" class="Wdate" style="width:159px;margin-right: 15px;margin-top: 15px;"/>
				     <a href="#" onclick="showValue();"  style="margin-right: 20px;">查询轨迹</a>
				 </div>
			</div>
		</div>
	</body>
</html>

