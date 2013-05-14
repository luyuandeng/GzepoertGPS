package com.gzeport.app.gps.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.google.gson.Gson;
import com.gzeport.app.gps.pojo.CarNo;
import com.gzeport.app.gps.pojo.CarNoDAO;
import com.gzeport.app.gps.pojo.VehicleInfo;
import com.gzeport.app.gps.pojo.VehicleInfoDAO;

public class CarNoDaoImpl implements ICarNoDao,Serializable {
	
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(this.getClass().getName());
	private CarNoDAO carnoDAO;
	private VehicleInfoDAO vehinfoDAO;
	private RedisTemplate<Serializable, Serializable> redisTemplate;
    private  static Gson gson = new Gson();
	public CarNoDAO getCarnoDAO() {
		return carnoDAO;
	}

	public void setCarnoDAO(CarNoDAO carnoDAO) {
		this.carnoDAO = carnoDAO;
	}

	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public VehicleInfoDAO getVehinfoDAO() {
		return vehinfoDAO;
	}

	public void setVehinfoDAO(VehicleInfoDAO vehinfoDAO) {
		this.vehinfoDAO = vehinfoDAO;
	}

	public CarNoDAO getAccDAO() {
		return carnoDAO;
	}

	public void setcarnoDAO(CarNoDAO carnoDAO) {
		this.carnoDAO = carnoDAO;
	}


	public List<CarNo> CarNoList(CarNo CarNo) {
		return carnoDAO.findByExample(CarNo);
	}
	/**
	 * @功能: 查找匹配车牌号 列表
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-5上午11:20:44 
	 */
	public List<CarNo> getCarList(String carNo){
		carNo ="%"+carNo.toUpperCase()+"%";
		return carnoDAO.findMatchProperty("plate", carNo);
	}
	/**
	 * @功能: 所有车辆 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-10-30下午11:52:18 
	 */
	public List<CarNo> carList() {
		return carnoDAO.findAll();
	}
	/**
	 * @功能: 根据车牌号 定位的方法 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-10-30下午11:52:42 
	 */
	public VehicleInfo getVehicInfoByCarNo(String carNo) {
		List<VehicleInfo> list = vehinfoDAO.findByProperty("Plate", carNo);
		if(list!=null&&list.size()>0){
			return list.get(0);
		} 
		return null;
	}
	/**
	 * @功能: 不从数据库找  而去redis查找 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-28上午9:47:58 
	 */
	public VehicleInfo getVehicInfoByRedis(String carNo,long carId) {
		
		final String pkey="p"+carId;
		log.info("Get the info of pkey:"+pkey+"查询！！");
		VehicleInfo vinfo =null;
		try {
		 vinfo =(VehicleInfo)redisTemplate.execute(new RedisCallback<Object>() {   
	        @Override  
	         public Object doInRedis(RedisConnection connection)   
	                throws DataAccessException {   
	           byte[]  valueInfo   =    connection.get( redisTemplate.getStringSerializer().serialize( pkey)); 
	           if(valueInfo!=null){
	        	   String jsonStr =redisTemplate.getStringSerializer().deserialize(valueInfo);
	        	   log.info(jsonStr);
	        	   VehicleInfo vehicleinfo = gson.fromJson(jsonStr, VehicleInfo.class); 
	        	   return vehicleinfo;  
	           }
	           return null;   
	         }   
	       });
		}catch(DataAccessResourceFailureException e){
			log.info(e.getMessage());
		}catch(JedisConnectionException je){
			log.info(je.getMessage());
		}
		if(vinfo==null){
			List<VehicleInfo> list = vehinfoDAO.findByProperty("Plate", carNo);
			if(list!=null&&list.size()>0){
				return list.get(0);
			} 
		}
		return vinfo;
	}
	/**
	 * @功能: 根据车牌号 查找历史轨迹数据 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-4下午08:59:48 
	 * @文件: CarNoDaoImpl.java
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleInfo> getGpsPolylineData(String carNo) {
		List<VehicleInfo> infolist = vehinfoDAO.findLineDataByProperty("Plate", carNo);
		return infolist;
	}
	/**
	 * @功能: 查询两个日期时间区间的GPS数据用以显示轨迹 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-17下午11:12:57 
	 */
	public List<VehicleInfo> getGpsPolylineByDB(int carId,String carNo,Date startdate,Date enddate) {
		List<VehicleInfo> infolist = new ArrayList();
		if(startdate!=null&&enddate!=null){
			infolist = vehinfoDAO.findLineDataByDb(carNo, startdate,enddate);
		}else{
			infolist = vehinfoDAO.findLineDataByProperty("Plate", carNo);
		}
		  //infolist = vehinfoDAO.findLineDataByDb(carNo, startdate,enddate);
		//List<VehicleInfo> infolist = vehinfoDAO.findLineDataByProperty("Plate", carNo);
		return infolist;
	}
	/**
	 * @功能: 从redis库查询数据 生成GPS轨迹
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-17下午11:36:18 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleInfo> getGpsPolylineByRedis(final int carid, String carno,final Date startdate, final Date enddate) {
		  List<VehicleInfo> vehicleInfoList=null;   
		  SimpleDateFormat dformat =new SimpleDateFormat("yyyyMMdd");
		  Date nowDate= new Date();
		  final String   dateStr =  dformat.format(nowDate);
		try {
			vehicleInfoList =	(List<VehicleInfo>) this.getRedisTemplate().execute(new RedisCallback<Object>() {   
				 public List<VehicleInfo> doInRedis(RedisConnection connection)   
				      throws DataAccessException {   
				      @SuppressWarnings("unused")
				      String redisKey ="plate"+dateStr+carid;
					 //zRevRange根据score值去倒序排序||zrange 顺序排列
				      connection.select(1);
				      Set<byte[]> k = connection.zRevRange(redisTemplate.getStringSerializer().serialize(redisKey), 0, -1);
				      List<VehicleInfo> infoList = new ArrayList<VehicleInfo>();
					for(byte[] value:k){
						String jsonStr =redisTemplate.getStringSerializer().deserialize(value);
						log.info(jsonStr);
						VehicleInfo d = gson.fromJson(jsonStr, VehicleInfo.class); 
						//返回某时间段的数据
						if(d.getGpstime().after(startdate)&&d.getGpstime().before(enddate)){
							infoList.add(d);
						}
					}
					  connection.select(0);
					return infoList;   
				        }   
				    });   
		}catch(JedisConnectionException j){
			j.getMessage();
			 return null;
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			
		}
		return vehicleInfoList;
	}

	@Override
	public CarNo update(CarNo carno) {
		return carnoDAO.merge(carno);
	}
}
