package com.ecnu.traceability.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PushInfo extends PushInfoKey {
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    public PushInfo() {
		super();
	}

	public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}