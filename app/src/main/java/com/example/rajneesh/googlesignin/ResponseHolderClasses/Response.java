package com.example.rajneesh.googlesignin.ResponseHolderClasses;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("Response")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
