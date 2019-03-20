package com.example.rajneesh.googlesignin.ResponseHolderClasses;

import com.google.gson.annotations.SerializedName;

public class YoutubeVideoResponse {
    @SerializedName("S.no")
    int sno;
    @SerializedName("link")
    String url;
    @SerializedName("name")
    String name;
    @SerializedName("photo")
    String photo;

    public YoutubeVideoResponse(String url, String name, String photo) {
        this.url = url;
        this.name = name;
        this.photo = photo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
