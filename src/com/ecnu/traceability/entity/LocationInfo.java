package com.ecnu.traceability.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LocationInfo {
    private Integer id;

    private String macaddress;

    private String longitude;

    private String latitude;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    public LocationInfo() {
		super();
	}


	public LocationInfo(String macaddress, String longitude, String latitude, Date date) {
		this.macaddress = macaddress;
		this.longitude = longitude;
		this.latitude = latitude;
		this.date = date;
	}
    

	public LocationInfo(Integer id, String macaddress, String longitude, String latitude, Date date) {
		this.id = id;
		this.macaddress = macaddress;
		this.longitude = longitude;
		this.latitude = latitude;
		this.date = date;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}