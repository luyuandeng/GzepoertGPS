package com.gzeport.app.gps.dao;

import java.io.Serializable;
import java.util.List;

import com.gzeport.app.gps.pojo.VehicleInfo;
import com.gzeport.app.gps.pojo.VehicleInfoDAO;

public class VehicleInfoDaoImpl implements IVehicleInfoDao,Serializable {
	
	private static final long serialVersionUID = -5942481889688418515L;
	private VehicleInfoDAO  vehileInfoDao ;
	
	public VehicleInfoDAO getVehileInfoDao() {
		return vehileInfoDao;
	}


	public void setVehileInfoDao(VehicleInfoDAO vehileInfoDao) {
		this.vehileInfoDao = vehileInfoDao;
	}

	/**
	 * @功能: 实本查询 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-10-30下午11:50:12 
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleInfo> VehicleInfoList(VehicleInfo VehicleInfo) {
		return vehileInfoDao.findByExample(VehicleInfo);
	}

	/**
	 * @功能: 不分页 根据属性查找 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-10-30下午11:49:39 
	 * @文件: VehicleInfoDaoImpl.java
	 */
	public List<VehicleInfo> accountList(VehicleInfo vInfo) {
		 
		return vehileInfoDao.findByProperty("plate", vInfo.getPlate());
	}


	/**
	 * @功能: 保存对象 
	 * @编码: luyd luyuandeng@gzeport.com
	 * @时间: 2012-10-30下午11:49:15 
	 */
	public void addVehicleInfo( VehicleInfo instance) {
		 this.vehileInfoDao.attachDirty(instance);
		
	}



}
