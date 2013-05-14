package com.gzeport.app.gps.dao;

import java.util.List;

import com.gzeport.app.gps.pojo.VehicleInfo;

public interface IVehicleInfoDao {
	public List<VehicleInfo> accountList(VehicleInfo account);
	public void addVehicleInfo(VehicleInfo instance);
}
