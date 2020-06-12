package com.ecnu.traceability.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnu.traceability.entity.ReportInfo;

@Repository
public interface ReportInfoMapper {

	public void addReportInfo(ReportInfo reportInfo);

	public List<ReportInfo> getReportInfoByMacAddress(String macAddress);
}
