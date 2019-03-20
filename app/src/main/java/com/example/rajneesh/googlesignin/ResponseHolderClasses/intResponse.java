package com.example.rajneesh.googlesignin.ResponseHolderClasses;

import com.google.gson.annotations.SerializedName;

public class intResponse {
    @SerializedName("Response")
    int message;

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
