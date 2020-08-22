package com.ecnu.traceability.service;

import java.util.ArrayList;
import java.util.List;

import com.ecnu.traceability.one_net.OneNETDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.PatientDetail;
import com.ecnu.traceability.entity.User;
import com.ecnu.traceability.mapper.PatientDetailMapper;
import com.ecnu.traceability.mapper.UserMapper;
import com.ecnu.traceability.utils.Email;

@Transactional
@Service
public class PatientDetailService {
	@Autowired
	private PatientDetailMapper patientDetailDao;

	@Autowired
	private UserMapper userDao;

	public boolean addPatientDetail(PatientDetail detail) {

		if (!isAdded(detail)) {
			try {
				patientDetailDao.addPatientDetail(detail);
				// 更新用户的状态
				//				User user = userDao.getUserByMacAddress(detail.getMacAddress());
				//				user.setFlag(false);
				//				userDao.updateUser(user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		List<PatientDetail> patientList = patientDetailDao.getAllPatientDetail();
		if (null != patientList && patientList.size() > 0) {
			System.out.println("==========上传感染病例数量=======");
			OneNETDevice.pushRiskPeople("", patientList);
		}
		List<String> deviceIdList = new ArrayList<>();
		for (PatientDetail details : patientList) {
			deviceIdList.add(userDao.getUserByMacAddress(details.getMacAddress()).getDeviceid());
		}
		if(deviceIdList.size()>0){
			OneNETDevice.pushDeviceIdList("",deviceIdList);
		}


		return false;
	}

	// 每30分钟执行一次
	@Scheduled(cron = "0 */60 * * * ?")
	public void sendEmailToStaff() {
		List<PatientDetail> list = patientDetailDao.getPatientDetail();//查找flag=false的病人用户
//		
		if (null != list) {
			for (PatientDetail detail : list) {
				System.out.println("=======================================");
				String phone = detail.getTelephone();
				String content = "手机号码为 " + phone + " 的用户,风险较高，请与其联系";
				Email.sendEmail("1769326712@qq.com", content);
				try {
					detail.setFlag(true);
					patientDetailDao.updatePatientDetail(detail);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isAdded(PatientDetail detail) {
		PatientDetail dbDetail = patientDetailDao.getPatientDetailByMac(detail.getMacAddress());
		if (null == dbDetail) {
			return false;
		} else {
			return true;
		}
	}
}
