package com.gzeport.app.gps.dwr.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gzeport.app.gps.dao.ICarNoDao;
import com.gzeport.app.gps.pojo.CarNo;
import com.gzeport.app.gps.pojo.VehicleInfo;

public class DwrCarService {

	Log log = LogFactory.getLog(this.getClass().getName());
	public DwrCarService() {
	}
private ICarNoDao entityCarDao;
	


public ICarNoDao getEntityCarDao() {
	return entityCarDao;
}



public void setEntityCarDao(ICarNoDao entityCarDao) {
	this.entityCarDao = entityCarDao;
}

/**
 * @功能: 查找匹配车牌号车辆 
 * @编码: luyd luyuandeng@gzeport.com
 * @时间: 2012-11-5上午11:21:37 
 */
public List<CarNo> getListByCarNo(String carNo) throws Exception {
	List <CarNo> carList = null;
	carList =this.entityCarDao.getCarList(carNo);
	return carList;
}
/**
 * @功能: 车辆列表 
 * @编码: luyd luyuandeng@gzeport.com
 * @时间: 2012-11-4下午08:54:26 
 * @文件: DwrCarService.java
 */
public List<CarNo> listCarNo() throws Exception {
	List <CarNo> carList = null;
	carList =this.entityCarDao.carList();
	return carList;
}
/**
 * @功能: 根据车牌号查找最新定位地点 
 * @编码: luyd luyuandeng@gzeport.com
 * @时间: 2012-11-4下午08:54:40 
 * @文件: DwrCarService.java
 */
public VehicleInfo findByCarNo(String carNo,long carId){
	VehicleInfo  vehInfo= new VehicleInfo();
	//vehInfo =this.entityCarDao.getVehicInfoByCarNo(carNo);
	vehInfo =this.entityCarDao.getVehicInfoByRedis(carNo, carId);
	return vehInfo;
}
/**
 * @功能: 根据车牌号查找历史轨迹数据 
 * @编码: luyd luyuandeng@gzeport.com
 * @时间: 2012-11-4下午09:01:56 
 * @文件: DwrCarService.java
 */
@SuppressWarnings("unchecked")
public List<VehicleInfo> getGpsPolylineData(Map<String,String> data){
	log.info("get the prams data is :"+data);
	 List<VehicleInfo>   gpsInfolist= new ArrayList();
	 Map p =data;
	 int carid =Integer.parseInt((String)p.get("id"));
	 String carno=(String) p.get("carNo");
	 String strStart=(String) p.get("startdate");
	 String strEnd =(String) p.get("enddate");
	 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date startdate=null;
	 Date enddate = null;
	 try {
		if(!"".equals(strStart)){
			startdate = sf.parse(strStart);
		}
		if(!"".equals(strEnd)){
			enddate = sf.parse(strEnd);
		}
	 } catch (ParseException e) {
		e.printStackTrace();
	 }
	 
	  SimpleDateFormat dformat =new SimpleDateFormat("yyyy-MM-dd");
	//  Calendar calendar =Calendar.getInstance();
	  Date nowDate= new Date();
	  try {
		   nowDate = dformat.parse( dformat.format(nowDate));
	  } catch (ParseException e) {
		  e.printStackTrace();
	  }
	  //calendar.setTime(nowDate);
	//  calendar.add( Calendar.DAY_OF_MONTH,1);
	//  Date  secondDate = calendar.getTime();
	  if(startdate!=null&&enddate!=null){
		  long t1=startdate.getTime(),t2=nowDate.getTime();
		  if(t1>=t2){
			  //从redis库查询
			  gpsInfolist=  this.entityCarDao.getGpsPolylineByRedis(carid,carno,startdate,enddate);
		  } else{
			  gpsInfolist=  this.entityCarDao.getGpsPolylineByDB(carid,carno,startdate,enddate);
		  } 
	  }else{
		  gpsInfolist=  this.entityCarDao.getGpsPolylineByDB(carid,carno,startdate,enddate);
	  }
		return gpsInfolist;
	}
/*public List<VehicleInfo> getGpsPolylineData(String carNo){
 System.out.println(carNo);
 List<VehicleInfo>   gpsInfolist= new ArrayList();
 	gpsInfolist =this.entityCarDao.getGpsPolylineData(carNo);
	return gpsInfolist;
}*/
}