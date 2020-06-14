package com.ecnu.traceability.service;

import java.util.List;

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
				// �����û���״̬
				User user = userDao.getUserByMacAddress(detail.getMacAddress());
				user.setFlag(false);
				userDao.updateUser(user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	// ÿ30����ִ��һ��
	@Scheduled(cron = "0 */60 * * * ?")
	public void sendEmailToStaff() {
		List<PatientDetail> list = patientDetailDao.getPatientDetail();
//		
		if (null != list) {
			for (PatientDetail detail : list) {
				System.out.println("=======================================");
				String phone = detail.getTelephone();
				String content = "�ֻ�����Ϊ " + phone + " ���û�,���սϸߣ���������ϵ";
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
			return true;
		} else {
			return false;
		}
	}
}
