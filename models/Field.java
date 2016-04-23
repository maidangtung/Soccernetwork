package com.maidangtung.soccernetwork.models;

import java.io.Serializable;

/**
 * Created by Ngu on 5/15/2015.
 */
public class Field implements Serializable {
    private int id;
    private String name;
    private int district_id;
    private String address;
    private float latitude;
    private float longtitude;

    public Field() {
    }

    public Field(int id, String name, int district_id, String address, float latitude, float longtitude) {
        this.id = id;
        this.name = name;
        this.district_id = district_id;
        this.address = address;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }
}


