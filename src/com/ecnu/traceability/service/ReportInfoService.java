package com.ecnu.traceability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.ReportInfo;
import com.ecnu.traceability.mapper.ReportInfoMapper;

@Transactional
@Service
public class ReportInfoService {

	@Autowired
	private ReportInfoMapper reportInfoDao;

	public boolean addReportInfo(List<ReportInfo> reportInfoList) {
		try {

			for (ReportInfo report : reportInfoList) {
				reportInfoDao.addReportInfo(report);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public List<ReportInfo> getReportInfoByMacAddress(String macAddress) {
		return reportInfoDao.getReportInfoByMacAddress(macAddress);

	}
}
