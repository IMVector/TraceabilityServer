package com.ecnu.traceability.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReportInfo {
    private Integer id;

    private String macaddress;
    private String description;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;




	public ReportInfo() {
		super();
	}

	public ReportInfo(String macaddress, String description, Date date) {
		super();
		this.macaddress = macaddress;
		this.description = description;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}