package com.ecnu.traceability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.PushInfo;
import com.ecnu.traceability.mapper.PushInfoMapper;

@Transactional
@Service
public class JudgeIsPushed {
	@Autowired
	private PushInfoMapper PushInfoDao;
	
	public boolean isPushed(String patientMacAddress, String userMacAddress) {
		List<PushInfo> pushInfoList = PushInfoDao.getPushInfoByUserAndPatientMacAddress(userMacAddress,
				patientMacAddress);
		if (null != pushInfoList) {
			if (pushInfoList.size() == 0) {// size为0没有推送过
				return false;
			}

		} else {// 列表为null没有推送过
			return false;
		}
		// 没出现上述情况已经推送过
		return true;
	}
}
