package com.ecnu.traceability.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnu.traceability.entity.TransportationInfo;

@Repository
public interface TransportationInfoMapper {
	public void addTranpostationInfo(TransportationInfo tranportationInfo);

	public List<TransportationInfo> getTranpostationIndoByMacAddress(String macAddress);

}
