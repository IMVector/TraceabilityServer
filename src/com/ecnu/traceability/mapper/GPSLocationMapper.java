package com.ecnu.traceability.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnu.traceability.entity.LocationInfo;

@Repository
public interface GPSLocationMapper {
	public void addGPSLocation(LocationInfo gpsLocationInfo);

//	public List<LocationInfo> getGPSLocationList();

	public List<LocationInfo> getGPSLocationListByMacAddress(String macAddress);

}