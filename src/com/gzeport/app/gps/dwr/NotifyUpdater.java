package com.gzeport.app.gps.dwr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletContext;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.context.ServletContextAware;

import com.gzeport.app.gps.pojo.VehicleInfo;

public class NotifyUpdater implements ApplicationListener, ServletContextAware {
	private ServletContext servletContext = null;
	private  ArrayList<VehicleInfo> messages = new ArrayList<VehicleInfo>();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	public void onApplicationEvent(ApplicationEvent event) {
		  if (event instanceof VehicleInfoEvent) {
			  messages = (ArrayList<VehicleInfo>) event.getSource();
	   //    System.out.println("收到了吗?"+messages.size());
	
	       ServerContext ctx = ServerContextFactory.get(servletContext );
//	       Collection<ScriptSession> sessions=ctx.getAllScriptSessions();
	       Collection<ScriptSession> sessions =new LinkedList() ;// new Collection();// ;//  ctx.getScriptSessionsByPage("/gpsDataShow.jsp");  
	     
	       Map map=AddScriptSessionListener.sc;
	   		Iterator<String> it = map.keySet().iterator();
	   		StringBuffer sb = new StringBuffer();
	   		int index =0;
	   		if	(it!=null){
	   			while(it.hasNext()){
	   				String key1 = it.next();
	   				sessions.add((ScriptSession)map.get(key1));
	   			}
	   		}
	       if(sessions!=null&&sessions.size()>0){
	    	   
	       }else{
	    	   System.out.println("sessions is null");
	    	   
	       }
	       
	      StringBuffer pagestr = new StringBuffer(20000);
	       pagestr.append("<table border='1'><tr bgcolor='#A3D1D1'><td>vTKey</td> <td>plate</td><td>recvtime</td><td>gpstime</td><td>Eff" +
	       				  "</td><td>Lat</td><td>Lon</td><td>Alarm</td><td>run</td><td>provider</td></tr>");
	      for (ScriptSession session : sessions) {
	           ScriptBuffer script = new ScriptBuffer();
	           /*String s=null;
	           String s2 = null;
	          try {
	             s = java.net.URLEncoder.encode(messages.get(0).getAlarm(),"UTF-8");
	             s2 = java.net.URLEncoder.encode("通知结束","UTF-8");
	           } catch (UnsupportedEncodingException e) {
	             e.printStackTrace();
	           }*/
	        //   System.out.println("(_):::"+messages.size());
	           
	          
	       

	           
	          if (messages!=null&&messages.size()>0){
	        	  //String str="";
	        	  for(VehicleInfo v:messages){
	        		  pagestr.append("<tr><td>" + v.getVTKey()+"</td>");
	        		  pagestr.append("<td>" + v.getPlate()+"</td>");
	        		  pagestr.append("<td>" + dateFormat.format(v.getRecvtime())+"</td>");
	        		  pagestr.append("<td>" +dateFormat.format(v.getGpstime())+"</td>");
	        		  pagestr.append("<td>" + v.getEff()+"</td>");
	        		  pagestr.append("<td>" + v.getLat()+"</td>");
	        		  pagestr.append("<td>" + v.getLon()+"</td>");
	        		  pagestr.append("<td>" + v.getAlarm()+"</td>");
	        		  pagestr.append("<td>" + v.getRun()+"</td>");
	        		  pagestr.append("<td>" + v.getProvider()+"</td></tr>");
	        		// str+=v.getPlate();
	        		  
	        	  }
	        	  pagestr.append("</table>");
	        	//  System.out.println("str..."+str);
	        	  script.appendScript("putInfo(")
	 	             .appendData(messages)
	 	             .appendScript(");");
	           }else{
	             script.appendScript("alert(decodeURI('").
	                    appendScript("error").appendScript("'));");
	           }
	          
	           System.out.println(script.toString());
	           session.addScript(script);
	       } 
	        //设置页面控件的值   
	        Util util = new Util(sessions);  
	        util.setValue("showdata", pagestr.toString());
	      //  util.setValue("text", "back return value.."); //这里是清空页面输入框的值   
//	        String[] cellarg ={"3", "3", "3"};
	        
	     }else{
	    	 System.out.println("不是该事件 .........."); 
	     }
	}

	 
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}

}
