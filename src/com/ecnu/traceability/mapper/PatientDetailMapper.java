package com.ecnu.traceability.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnu.traceability.entity.PatientDetail;

@Repository
public interface PatientDetailMapper {

	public void addPatientDetail(PatientDetail detail);

	public PatientDetail getPatientDetailByMac(String patientMac);

	public PatientDetail getPatientDetailByTel(String patientTel);
	
	public List<PatientDetail> getPatientDetail();
	
	public void updatePatientDetail(PatientDetail detail);

}
