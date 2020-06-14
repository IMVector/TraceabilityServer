package com.ecnu.traceability.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.LocationInfo;
import com.ecnu.traceability.entity.PushInfo;
import com.ecnu.traceability.entity.User;
import com.ecnu.traceability.mapper.GPSLocationMapper;
import com.ecnu.traceability.mapper.PushInfoMapper;
import com.ecnu.traceability.mapper.UserMapper;

@Transactional
@Service
public class LocationService extends JudgeIsPushed {

	@Autowired
	private PushInfoMapper PushInfoDao;
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

	public List<LocationInfo> getGPSLocationListByMacAddress(String patientMacAddress, String userMacAddress) {
		if (!isPushed(patientMacAddress, userMacAddress)) {
			return gpsLocationDao.getGPSLocationListByMacAddress(patientMacAddress);
		} else {
			return new ArrayList<LocationInfo>();
		}
	}



}
