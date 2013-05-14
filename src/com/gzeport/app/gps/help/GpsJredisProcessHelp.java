package com.gzeport.app.gps.help;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gzeport.app.gps.dao.ICarNoDao;
import com.gzeport.app.gps.dao.IVehicleInfoDao;
import com.gzeport.app.gps.pojo.CarNo;
import com.gzeport.app.gps.pojo.VehicleInfo; 
 

public class GpsJredisProcessHelp implements Serializable {
	
	private static final long serialVersionUID = 3520334388791253481L;
	Log log = LogFactory.getLog(this.getClass().getName());
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	private static String ip ="192.168.101.12";
	private IVehicleInfoDao  InfoDao;
	private static JedisPool  pool ;
	private ICarNoDao  carDao;
 
	static{
		JedisPoolConfig config =new JedisPoolConfig();
		//#最大分配的对象数 
		config.setMaxActive(1024 );
		//#最大能够保持idel状态的对象数 
		config.setMaxIdle(200);
		//#当池内没有返回对象时，最大等待时间 
		config.setMaxWait(1000); 
		//#当调用borrow Object方法时，是否进行有效性检查
	    config.setTestOnBorrow(true); 
	    //#当调用return Object方法时，是否进行有效性检查 
	    config.setTestOnReturn(true);   

		pool= new JedisPool(config,ip,6379 );
	}
	 private static  Jedis jedis = null;
	
	
	private RedisConnection  conn =null;
	
   private  Gson gson = new Gson();
	public IVehicleInfoDao getInfoDao() {
		return InfoDao; 
	}


	public void setInfoDao(IVehicleInfoDao infoDao) {
		InfoDao = infoDao;
	}
	public ICarNoDao getCarDao() {
		return carDao;
	}


	public void setCarDao(ICarNoDao carDao) {
		this.carDao = carDao;
	}
	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}


	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * @功能: 采聚数据第一种 方法 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-5下午09:51:05 
	 */
	public  List<VehicleInfo>  getVehicleInfoByJredis(){
			 List<VehicleInfo> vehicleInfoList=null;   
			 log.info("进来jedis 获取数据..");
			try {
				jedis = pool.getResource();
				log.info(" jedis pool is  OK..");
				// 判断是否正常返回   
				 if (jedis.isConnected()) {
					 log.info("isConnected"+jedis.keys("*")+jedis.get("count")); 
					List<String> jsonStr =jedis.lrange("eport.queue.gps",0,20);
					log.info(jsonStr);
					if(conn==null){
						log.info("conn is Null");
					} 
					vehicleInfoList = gson.fromJson(jsonStr.toString(), new TypeToken<List<VehicleInfo>>(){}.getType());   
					for(VehicleInfo d:vehicleInfoList){
						//System.out.println(l+d.getPlate()+d.getVTKey()+d.getRecvtime()+d.getGpstime()+d.getLat()+d.getLon());
						d.setPlatecolor(d.getPlate().substring(d.getPlate().length()-1));
						d.setPlate(d.getPlate().substring(0,d.getPlate().length()-2));
					//	returnInfoList.add(d);
						this.InfoDao.addVehicleInfo(d);
					}
				 }else{ 
					 log.info("异常情况jedis为空");
		        }
			}catch(JedisConnectionException j){
				j.getMessage();
			}
			catch (Exception e) {
				jedis.disconnect();
				e.printStackTrace();
			} finally{
				pool.returnResource(jedis);   //记得关闭
			}
			log.info("执行完毕返回----");
			return null;
		}
	
	/**
	 * @功能: 采集数据第一种 方法 由于下面的那种方法存在BUG could not get a resource from the pool   所以添加该方法
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-5下午09:51:05 
	 */
	public  List<VehicleInfo>  getVehicleInfoByJredis1(){
			 List<VehicleInfo> vehicleInfoList=null;   
	         final String   dateStr  =new SimpleDateFormat("yyyyMMdd").format(new Date());
	    	 Calendar calendar =Calendar.getInstance();
	    	 calendar.setTime(new Date());
	    	 calendar.add(Calendar.DAY_OF_MONTH,-1);
	    	 Date  secondDate = calendar.getTime();
	    	 final String  lastdateStr  =new SimpleDateFormat("yyyyMMdd").format(secondDate);
			 
			 log.info("进来jedis 获取数据..");
			try {
				jedis = pool.getResource();
				log.info(" jedis pool is  OK..");
				// 判断是否正常返回   
				 if (jedis.isConnected()) {
				//	log.info("isConnected"+jedis.keys("*")+jedis.get("count")); 
					List<String> jsonStr =jedis.lrange("eport.queue.gps",0,100);
				//	log.info(jsonStr);
					vehicleInfoList = gson.fromJson(jsonStr.toString(), new TypeToken<List<VehicleInfo>>(){}.getType());   
					if(vehicleInfoList!=null){
						List<CarNo> carlist =this.carDao.carList();
						for(VehicleInfo vi:vehicleInfoList){
							vi.setPlatecolor(vi.getPlate().substring(vi.getPlate().length()-1));
							vi.setPlate(vi.getPlate().substring(0,vi.getPlate().length()-2));
							this.InfoDao.addVehicleInfo(vi);
							boolean has =false;
							for(final CarNo car:carlist){
								if(car.getPlate().trim().equals(vi.getPlate().trim())){
									has=true;
									final String redisKey= "plate"+dateStr+car.getId();
									final Long lzscore =vi.getVehicleId(); //把ID设为排序号
									final String jsonValue =gson.toJson(vi);
	 	//	log.info("车牌号"+vi.getPlate()+"对象 找到："+car.getPlate()+"Key会是："+redisKey);
									jedis.set("p"+car.getId(), jsonValue);
									jedis.select(1);
									Boolean backValue  =jedis.exists(redisKey);
									if(backValue){
										jedis.zadd(redisKey, lzscore, jsonValue);
									}else{
										String oldKey ="plate"+lastdateStr+car.getId();
										if(car.getRedkey()!=null){
											oldKey=car.getRedkey();
										}
										Long b =	jedis.del(oldKey);
										car.setRedkey(redisKey);
										jedis.zadd(redisKey, lzscore, jsonValue);
										this.carDao.update(car);
									}
									jedis.select(0);
									break;
								}
							}
							if(!has){
						 		log.info("未找到匹配"+vi.getPlate()+"的车辆！");
						 	}
						}
					}
				 }else{ 
					 log.info("异常情况jedis为空");
		        }
			}catch(JedisConnectionException j){
				j.getMessage();
			}
			catch (Exception e) {
				jedis.disconnect();
				e.printStackTrace();
			} finally{
				pool.returnResource(jedis);   //记得关闭
			}
			log.info("执行完毕---->返回");
			return null;
		}
	/**
	 * @功能: 采集GPS数据另一种 方法  并保存一份到 有序集合Zset 存在BUG could not get a resource from the pool
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-5下午09:51:05 
	 */
	@SuppressWarnings("unchecked")
	public  List<VehicleInfo>  getVehicleInfoByJredis22(){
		List<VehicleInfo> vehicleInfoList=null; 
		 log.info("进来jedis 获取数据2..");
		 final   String   dateStr  =new SimpleDateFormat("yyyyMMdd").format(new Date());
    	 Calendar calendar =Calendar.getInstance();
    	 calendar.setTime(new Date());
    	 calendar.add(Calendar.DAY_OF_MONTH,-1);
    	 Date  secondDate = calendar.getTime();
    	  final String  lastdateStr  =new SimpleDateFormat("yyyyMMdd").format(secondDate);
			 
		 
		 
	 	List<CarNo> carlist =this.carDao.carList();
		try {
			vehicleInfoList =	(List<VehicleInfo>) this.getRedisTemplate().execute(new RedisCallback<Object>() {   
				 public List<VehicleInfo> doInRedis(RedisConnection connection)   
				      throws DataAccessException {   
				      @SuppressWarnings("unused")
					List <byte[]> backlist = connection.lRange(redisTemplate.getStringSerializer().serialize("eport.queue.gps"), 0,50);   
					List<VehicleInfo> infoList = new ArrayList<VehicleInfo>();
					for(byte[] value:backlist){
						String jsonStr =redisTemplate.getStringSerializer().deserialize(value);
						VehicleInfo d = gson.fromJson(jsonStr, VehicleInfo.class);   
						d.setPlatecolor(d.getPlate().substring(d.getPlate().length()-1));
						d.setPlate(d.getPlate().substring(0,d.getPlate().length()-2));
						infoList.add(d);
					}
					return infoList;   
				}   
			   });   
			/**
			 * 入库 同时保存在redis内存数据库 有序集合Zset
			 */
			for(final VehicleInfo vi:vehicleInfoList){
				this.InfoDao.addVehicleInfo(vi);  //入库
	//			log.info( "进行车牌号==="+vi.getPlate()+"入库后操作！");
				boolean has =false;
			 	for(final CarNo car:carlist){
//	/	log.info(car.getPlate()+"==?相等 ?="+vi.getPlate());
					if(car.getPlate().trim().equals(vi.getPlate().trim())){
						final String redisKey= "plate"+dateStr+car.getId();
						final Long lzscore =vi.getVehicleId(); //把ID设为排序号
						final String jsonValue =gson.toJson(vi);
						this.redisTemplate.getConnectionFactory().getConnection().set(redisTemplate.getStringSerializer().serialize("p"+car.getId()), redisTemplate.getStringSerializer().serialize(jsonValue));
 		log.info("车牌号"+vi.getPlate()+"对象 找到："+car.getPlate()+"Key会是："+redisKey);
 						has=true;
						this.redisTemplate.execute(new RedisCallback<Object>() {
							@Override
					        public Object doInRedis(RedisConnection connection)   
					            throws DataAccessException { 
								connection.select(1);
								//保存在redis
								Boolean backValue =connection.exists(redisTemplate.getStringSerializer().serialize(redisKey));
								log.info("this plate is exists data in redis??"+backValue);
								if(backValue){
									connection.zAdd(redisTemplate.getStringSerializer().serialize(redisKey),lzscore,
							        	redisTemplate.getStringSerializer().serialize(jsonValue));
								}else{
									final String oldKey ="plate"+lastdateStr+car.getId();
									connection.del(redisTemplate.getStringSerializer().serialize(oldKey));
									connection.zAdd(redisTemplate.getStringSerializer().serialize(redisKey),lzscore,
							        		redisTemplate.getStringSerializer().serialize(jsonValue));
								}
								connection.select(0);
					            return null;   
					         }   
					     });
						break;
					}
				}
			 	if(!has){
			 		log.info("未找到匹配"+vi.getPlate()+"的车辆！");
			 	}
			}
		}catch(JedisConnectionException j){
			log.info(j.getMessage());
			 return null;
		}catch (Exception e) {
			log.info(e.getMessage());
		} finally{
			
		}
	log.info("执行完毕返回----");
		return vehicleInfoList;
	}
	/**
	 * @功能: 从内存redis读出 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-11-7上午11:02:34 
	 */
	@SuppressWarnings("unchecked")
	public  List<VehicleInfo>  getTestByJredis(){
		List<VehicleInfo> vehicleInfoList=null;   
//		List<CarNo> carlist =this.carDao.carList();
		try {
			vehicleInfoList =	(List<VehicleInfo>) this.getRedisTemplate().execute(new RedisCallback<Object>() {   
				 public List<VehicleInfo> doInRedis(RedisConnection connection)   
				      throws DataAccessException {   
				      @SuppressWarnings("unused")
				//	List <byte[]> backlist = connection.lRange(redisTemplate.getStringSerializer().serialize("eport.queue.gps"), 0, 3);   
					 //zRevRange根据score值去倒序排序||zrange 顺序排列
				      Set<byte[]> k = connection.zRevRange(redisTemplate.getStringSerializer().serialize("Plate70"), 0, 3);
				      List<VehicleInfo> infoList = new ArrayList<VehicleInfo>();
					for(byte[] value:k){
						String jsonStr =redisTemplate.getStringSerializer().deserialize(value);
						log.info(jsonStr);
						VehicleInfo d = gson.fromJson(jsonStr, VehicleInfo.class);   
						d.setPlatecolor(d.getPlate().substring(d.getPlate().length()-1));
						d.setPlate(d.getPlate().substring(0,d.getPlate().length()-2));
						infoList.add(d);
					}
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
	public static void testGetHttp(){}
	/**
	 * 
	 * 执行模板
	redisTemplate.execute(new RedisCallback<Object>() {   
        @Override  
         public Object doInRedis(RedisConnection connection)   
                throws DataAccessException {   
           connection.set(   
                    redisTemplate.getStringSerializer().serialize(   
                            "user.uid." + user.getUid()),   
                    redisTemplate.getStringSerializer().serialize(   
                            user.getAddress()));   
            return null;   
         }   
       });
	 */

}
