package com.ecnu.traceability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.PushInfo;
import com.ecnu.traceability.entity.TransportationInfo;
import com.ecnu.traceability.mapper.PushInfoMapper;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Transactional
@Service
public class PushInfoService extends JudgeIsPushed {

	@Autowired
	private PushInfoMapper pushInfoDao;

	public boolean addPushInfo(PushInfo pushInfo) {



		if (!isPushed(pushInfo.getPatientmac(), pushInfo.getUsermac())) {
			try {
				pushInfoDao.addPushInfo(pushInfo);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public List<PushInfo> getPushInfoByUserMacAddress(String userMacAddress) {
		return pushInfoDao.getPushInfoByUserMacAddress(userMacAddress);

	}

	public List<PushInfo> getPushInfoByPatientMacAddress(String patientMacAddress) {
		return pushInfoDao.getPushInfoByPatientMacAddress(patientMacAddress);

	}

}
