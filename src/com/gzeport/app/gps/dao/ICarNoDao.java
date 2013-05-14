package com.gzeport.app.gps.dao;

import java.util.Date;
import java.util.List;

import com.gzeport.app.gps.pojo.CarNo;
import com.gzeport.app.gps.pojo.VehicleInfo;

public interface ICarNoDao {
	public List<CarNo> carList( );
	public List<CarNo> getCarList(String carNo);
	public  VehicleInfo getVehicInfoByCarNo(String carNo);
	public  VehicleInfo getVehicInfoByRedis(String carNo,long carId);
	public List<VehicleInfo> getGpsPolylineData(String carNo);
	public List<VehicleInfo> getGpsPolylineByDB(int carId,String carNo,Date startdate,Date enddate);
	public List<VehicleInfo> getGpsPolylineByRedis(int carid, String carno,
			Date startdate, Date enddate);
	public CarNo update(CarNo carno);
}
