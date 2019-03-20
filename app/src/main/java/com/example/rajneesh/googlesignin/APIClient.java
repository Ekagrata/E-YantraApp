package com.example.rajneesh.googlesignin;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    APIInterface apiInterface;
    static APIClient client;
    public APIInterface getApi() {
        return apiInterface;
    }




    private APIClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-35-154-66-115.ap-south-1.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiInterface = retrofit.create(APIInterface.class);
    }



    public static APIClient getInstance() {
        if(client == null){
            client = new APIClient();
        }
        return client;
    }
}
