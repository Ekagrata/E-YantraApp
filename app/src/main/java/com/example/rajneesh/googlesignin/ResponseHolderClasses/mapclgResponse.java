package com.example.rajneesh.googlesignin.ResponseHolderClasses;

import com.google.gson.annotations.SerializedName;

public class mapclgResponse {
    @SerializedName("FIELD1")
    int id;
    @SerializedName("FIELD2")
    String clg_name;
    @SerializedName("FIELD3")
    String grade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClg_name() {
        return clg_name;
    }

    public void setClg_name(String clg_name) {
        this.clg_name = clg_name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
