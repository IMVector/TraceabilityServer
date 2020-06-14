package com.ecnu.traceability.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PushInfo {

	private Integer id;
	
	private String usermac;

	private String patientmac;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date date;

	private String disease;

	public PushInfo() {
	}

	public PushInfo(String usermac, String patientmac, Date date, String disease) {
		this.usermac = usermac;
		this.patientmac = patientmac;
		this.date = date;
		this.disease = disease;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsermac() {
		return usermac;
	}

	public void setUsermac(String usermac) {
		this.usermac = usermac;
	}

	public String getPatientmac() {
		return patientmac;
	}

	public void setPatientmac(String patientmac) {
		this.patientmac = patientmac;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}


}