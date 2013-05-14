<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
		<title>hihi</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<style>
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
		<script type='text/javascript' src='<%=basePath%>/My97DatePicker/WdatePicker.js'></script>
		<script>
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
			
			 function showMessageDig(event){
			  alert(event);
		       var message =document.getElementById("xingZQYTree");
		       message.style.display='';
		       message.style.top=event.clientY;
		       message.style.left=event.clientX;
		      
	   		 }
			 function showValue(){
		       var v1 =document.getElementById("d241").value;
		       var v2 =document.getElementById("d233").value;
		       document.getElementById("xingZQYTree").style.display='none';
		       alert(v1+v2);
	   		 }
		</script>
	</head>
	<body>
		<input type="button" value="弹出DIV窗口" onclick="showMessageDig(event)"/>
<!--			onclick="xingZQYTree.style.display='';xingZQYTree.style.top=event.clientY;xingZQYTree.style.left=event.clientX" />-->


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
					<input type="text" id="d241" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:159px;margin-left: 20px;margin-top: 15px;"/>	
					&nbsp;至&nbsp;<input type="text" id="d233" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width:159px;margin-right: 15px;margin-top: 15px;"/>
				     <a href="#" onclick="showValue();"  style="margin-right: 20px;">查询轨迹</a>
				 </div>
			</div>
		</div>
	</body>
</html>
