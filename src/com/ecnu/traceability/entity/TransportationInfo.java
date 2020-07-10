package com.ecnu.traceability.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TransportationInfo {
    private Integer id;

    private String macaddress;

    private String type;

    private String no;

    private String seat;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    
    public TransportationInfo() {
		super();
	}

	public TransportationInfo(String macaddress, String type, String no, String seat, Date date) {
		super();
		this.macaddress = macaddress;
		this.type = type;
		this.no = no;
		this.seat = seat;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat == null ? null : seat.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}