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
			if (pushInfoList.size() == 0) {// sizeΪ0û�����͹�
				return false;
			}

		} else {// �б�Ϊnullû�����͹�
			return false;
		}
		// û������������Ѿ����͹�
		return true;
	}
}
