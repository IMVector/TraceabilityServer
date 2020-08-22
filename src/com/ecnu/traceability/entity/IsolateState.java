package com.ecnu.traceability.entity;

import java.util.Date;

public class IsolateState {
    private Integer id;
    private String deviceId;
    private String mac;
    private boolean state;
    private Date date;

    public IsolateState() {
    }

    public IsolateState(Integer id, String deviceId, String mac, boolean state, Date date) {
        this.id = id;
        this.deviceId = deviceId;
        this.mac = mac;
        this.state = state;
        this.date = date;
    }

    public IsolateState(String deviceId, String mac, boolean state, Date date) {
        this.deviceId = deviceId;
        this.mac = mac;
        this.state = state;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
