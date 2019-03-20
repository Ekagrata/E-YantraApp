package com.example.rajneesh.googlesignin.ResponseHolderClasses;

import com.google.gson.annotations.SerializedName;

public class NewsFeedResponse {

    @SerializedName("ID")
    int newsid;
    @SerializedName("username")
    String username;
    @SerializedName("title")
    String title;
    @SerializedName("desc")
    String desc;
    @SerializedName("link")
    String photolink;
    @SerializedName("updated_at")
    String updatedat;
    @SerializedName("created_at")
    String createdat;

    public String getPhotolink() {
        return photolink;
    }

    public void setPhotolink(String photolink) {
        this.photolink = photolink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }
}
