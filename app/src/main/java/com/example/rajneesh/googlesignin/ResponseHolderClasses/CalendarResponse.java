package com.example.rajneesh.googlesignin.ResponseHolderClasses;

import com.google.gson.annotations.SerializedName;

public class CalendarResponse {
    @SerializedName("S.no")
    int sno;
    @SerializedName("Date")
    String date;
    @SerializedName("title")
    String title;
    @SerializedName("Description")
    String description;
    @SerializedName("Initiative")
    String initiative;
    @SerializedName("event_loc")
    String location;
    @SerializedName("updated_at")
    String updatedat;
    @SerializedName("created_at")
    String createdat;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitiative() {
        return initiative;
    }

    public void setInitiative(String initiative) {
        this.initiative = initiative;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
