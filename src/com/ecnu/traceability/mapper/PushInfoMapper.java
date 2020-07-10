package com.ecnu.traceability.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnu.traceability.entity.PushInfo;
@Repository
public interface PushInfoMapper {

	public void addPushInfo(PushInfo pushInfo);
	
	public List<PushInfo> getPushInfoByUserAndPatientMacAddress(String userMacAddress,String patientMacAddress);

	public List<PushInfo> getPushInfoByUserMacAddress(String userMacAddress);

	public List<PushInfo> getPushInfoByPatientMacAddress(String patientMacAddress);
}
