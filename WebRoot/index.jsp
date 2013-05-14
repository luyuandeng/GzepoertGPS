<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
      <style type="text/css">
    #popupcontent{
	 position: absolute;
	 visibility: hidden;
	 overflow: hidden;
	 border:1px solid #ccc;
	 background-color:#f9f9f9;
	 border:1px solid #333;
	 padding:5px;
	 }
    </style>
	<script type="text/javascript" src="/GPSSystem/dwr/engine.js"></script>  
	<script type="text/javascript" src="/GPSSystem/dwr/util.js"></script>  
	<script type="text/javascript" src="/GPSSystem/dwr/interface/dwrService.js"></script>  
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"> </script>
    
    <script type="text/javascript">

	    var map;
	    var geocoder;
	    var address;
	    var temp;
	    var locations = {};
	    var latlng = new google.maps.LatLng(23.128703, 113.26497);
	    var markersArray = [];
	   
	 function initialize() {
	    dwr.engine.setActiveReverseAjax(true);
	    dwr.engine.setErrorHandler(function(){});
	      var myOptions = {
		    zoom: 11,
		    center: latlng,
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		  };
		  map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
		  geocoder= new  google.maps.Geocoder();
	  }
  
   
      function getDataFromServer() {  
          dwrService.addDataTest({  
           callback: getDataFromServerCallBack  
          });  
        }  
       function getDataFromServerCallBack(dataFromServer) {  
         alert(dwr.util.toDescriptiveString(dataFromServer, 3));  
       }  
       
        var basetext = null; 
		function showpopup(w,h){  
	//	alert(w+h); 
		    var popup = document.getElementById("popupcontent");    
		    popup.style.top = "200px";   
		    popup.style.left = "200px";   
		    popup.style.width = w +"px" ;   
		    popup.style.height = h +"px";    
		    if (basetext == null) 
		    basetext = popup.innerhtml;   
		    popup.innerhtml = basetext + "<div id='statusbar'><button onclick='hidepopup();'>close window<button></div>";   
		    //var sbar = document.getElementById("statusbar");   
		  //  sbar.style.margintop = (parseint(h)-40) + "px";   
		    popup.style.visibility = "visible";
		}
		function hidepopup(){   
		    var popup = document.getElementById("popupcontent");   
		    popup.style.visibility = "hidden";
		}
    </script>
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	<script type="text/javascript">  
        function sendMessage() {   
            DWRHelper.addMessage(dwr.util.getValue("uname"));   
        }   
    </script>-->  

    
  </head>
  
  <body>
  <a href="#" onclick="getDataFromServer();">Retrieve test data</a><br/> 
  <div id="popupcontent">this is a popup window!</div>
  <a href="#" onclick="showpopup(300,200);" >open popup</a>
   <form action="accountController.do" method="post">&nbsp; 
   ”√ªß√˚£∫<input name="uname"/><br/>
  √‹¬Î£∫ <input name="upass"/><br/>
   <input type="submit" value="µ«¬º"/>
   </form>
   <div id="map_canvas" style="width: 820px; height: 638px"></div>

  </body>
</html>
