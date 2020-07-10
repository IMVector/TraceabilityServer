package com.ecnu.traceability.entity;

import java.util.Date;

public class Relationship {
    private int id;
    private String originMac;
    private String targetMac;
    private Date date;
    private int flag;

    public Relationship() {
    }

    public Relationship(String originMac, String targetMac, Date date, int flag) {
        this.originMac = originMac;
        this.targetMac = targetMac;
        this.date = date;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginMac() {
        return originMac;
    }

    public void setOriginMac(String originMac) {
        this.originMac = originMac;
    }

    public String getTargetMac() {
        return targetMac;
    }

    public void setTargetMac(String targetMac) {
        this.targetMac = targetMac;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
