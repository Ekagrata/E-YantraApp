package com.example.rajneesh.googlesignin.ResponseHolderClasses;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapResponse {

    @SerializedName("id")
    int id;
    @SerializedName("latitude")
    Double lat;
    @SerializedName("longitude")
    Double lng;
    @SerializedName("college")
    String clg_name;
    @SerializedName("Grade")
    String grade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getClg_name() {
        return clg_name;
    }

    public void setClg_name(String clg_name) {
        this.clg_name = clg_name;
    }

    public Double getlatfromclgname(String clg_name){
        return this.lat;
    }
}
