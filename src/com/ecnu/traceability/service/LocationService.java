package com.ecnu.traceability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.LocationInfo;
import com.ecnu.traceability.mapper.GPSLocationMapper;

@Transactional
@Service
public class LocationService {

	@Autowired
	private GPSLocationMapper gpsLocationDao;

	public boolean addGPSLocation(List<LocationInfo> gpsLocationInfoList) {

		try {
			for (LocationInfo locinfo : gpsLocationInfoList) {
				gpsLocationDao.addGPSLocation(locinfo);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<LocationInfo> getGPSLocationListByMacAddress(String macAddress) {
		return gpsLocationDao.getGPSLocationListByMacAddress(macAddress);

	}

}
