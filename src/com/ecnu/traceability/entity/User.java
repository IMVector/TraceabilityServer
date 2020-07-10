package com.ecnu.traceability.entity;

public class User {
	private Integer id;

	private String macaddress;

	private String deviceid;

	private String tel;

	private Boolean flag;

	public User(String macaddress, String deviceid, String tel, Boolean flag) {
		this.macaddress = macaddress;
		this.deviceid = deviceid;
		this.tel = tel;
		this.flag = flag;
	}

	public User(Integer id, String macaddress, String deviceid, Boolean flag) {
		this.id = id;
		this.macaddress = macaddress;
		this.deviceid = deviceid;
		this.flag = flag;
	}

	public User(String macaddress, String deviceid, Boolean flag) {
		this.macaddress = macaddress;
		this.deviceid = deviceid;
		this.flag = flag;
	}

	public User() {
		super();
	}


	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress == null ? null : macaddress.trim();
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid == null ? null : deviceid.trim();
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}