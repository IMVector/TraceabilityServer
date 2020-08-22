package com.ecnu.traceability.entity;

import java.util.Date;

public class LocationDataMiningResult {
    private Integer id;
    private String lat;
    private String lon;

    private Date date;
    private Integer batch;

    private String type;


    public LocationDataMiningResult() {
    }

    public LocationDataMiningResult(String lat, String lon, Date date, Integer batch, String type) {
        this.lat = lat;
        this.lon = lon;
        this.date = date;
        this.batch = batch;
        this.type = type;
    }

    public LocationDataMiningResult(Integer id, String lat, String lon, Date date, Integer batch, String type) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
        this.batch = batch;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
