package com.gzeport.app.gps.dwr.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.gzeport.app.gps.help.GpsJredisProcessHelp;
import com.gzeport.app.gps.pojo.VehicleInfo;

public class DwrGpsHelper {   
    List<VehicleInfo> vInfoList =null;
    private static ReentrantLock lock = new ReentrantLock(); //JDK5锁   
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private   GpsJredisProcessHelp gpsJredisProcessHelp; 
    private Log log =LogFactory.getLog(this.getClass().getName());
    
	public GpsJredisProcessHelp getGpsJredisProcessHelp() {
		return gpsJredisProcessHelp;
	}
	public void setGpsJredisProcessHelp(GpsJredisProcessHelp gpsJredisProcessHelp) {
		this.gpsJredisProcessHelp = gpsJredisProcessHelp;
	}
	public void addMessage(){   
        try{   
           lock.lock();  
        	log.info("进入调度程序..........");
          //  vInfoList= gpsJredisProcessHelp.getVehicleInfoByJredis();
         //   vInfoList= gpsJredisProcessHelp.getVehicleInfoByJredis22(); //测试
        	 vInfoList=	gpsJredisProcessHelp.getVehicleInfoByJredis1(); 		//测试 
               //vInfoList= gpsJredisProcessHelp.getTestByJredis();
          /*  messages = new ArrayList();
            if(vInfoList!=null){
            	int sno = 1;
            	  for(VehicleInfo vehicleInfo :vInfoList){
                  	if(vehicleInfo!=null){
                  		System.out.println("准备推送数据："+vehicleInfo.getVTKey());
                  	}
                  	Map map = new HashMap();
                  	map.put("sno", sno++);
                  	map.put("plate", vehicleInfo.getPlate().substring(0,7));
                  	map.put("lat", vehicleInfo.getLat());
                  	map.put("lon", vehicleInfo.getLon());
                  	map.put("vtkey", vehicleInfo.getVTKey());
                	map.put("gpstime", format.format(vehicleInfo.getGpstime()));
                	map.put("mile", vehicleInfo.getMile());
                  	messages.add(map);
                  }
            }*/
          
        }catch(Exception ex){   
            ex.printStackTrace();   
        }finally{   
             lock.unlock();   
        }   
           
       /* ServerContext ctx = ServerContextFactory.get(servletContext );
	    Collection<ScriptSession> sessions =new LinkedList() ;  
	     
	       Map map=AddScriptSessionListener.sc;
	   	   Iterator<String> it = map.keySet().iterator();
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
	    	   
	       }*/
        
          /* ScriptBuffer script = new ScriptBuffer();   
           script.appendScript("clientFunction(")   
            .appendData(messages) 
            .appendScript(");");   
              
           for(ScriptSession session: sessions){   
               session.addScript(script);   
           }   */
              
           //更新这些脚本session的一些元素   
           //utilAll.removeAllOptions("messages");   
        //   utilAll.addOptions("messages", messages, "vTKey", "statusChars");
     //  }    
  
    }  
	
		
}  
