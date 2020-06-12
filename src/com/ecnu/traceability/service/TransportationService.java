package com.ecnu.traceability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.TransportationInfo;
import com.ecnu.traceability.mapper.TransportationInfoMapper;
@Transactional
@Service
public class TransportationService {

	@Autowired
	private TransportationInfoMapper transportationDao;

	public boolean addTranpostationInfo(List<TransportationInfo> tranportationInfoList) {
		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		try {
			for(TransportationInfo info:tranportationInfoList) {
				transportationDao.addTranpostationInfo(info);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<TransportationInfo> getTranpostationIndoByMacAddress(String macAddress) {
		return transportationDao.getTranpostationIndoByMacAddress(macAddress);

	}
}
