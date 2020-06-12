package com.ecnu.traceability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.PushInfo;
import com.ecnu.traceability.entity.TransportationInfo;
import com.ecnu.traceability.mapper.PushInfoMapper;

@Transactional
@Service
public class PushInfoService {

	@Autowired
	private PushInfoMapper pushInfoDao;

	public boolean addPushInfo(PushInfo pushInfo) {
		try {
			pushInfoDao.addPushInfo(pushInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<PushInfo> getPushInfoByUserMacAddress(String userMacAddress) {
		return pushInfoDao.getPushInfoByUserMacAddress(userMacAddress);

	}

	public List<PushInfo> getPushInfoByPatientMacAddress(String patientMacAddress) {
		return pushInfoDao.getPushInfoByPatientMacAddress(patientMacAddress);

	}

	public boolean isPushed(String userMACAddress, String patientMACAddress) {
		List<PushInfo> pushInfoList = pushInfoDao.getPushInfoByUserAndPatientMacAddress(userMACAddress,
				patientMACAddress);
		if (null != pushInfoList && pushInfoList.size() > 0) {
			return true;
		}
		return false;
	}
}
