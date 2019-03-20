package com.example.rajneesh.googlesignin;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService  extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("refreshtoken", "Refreshed token: " + refreshedToken);


      //  sendRegistrationToServer(refreshedToken);
    }
}
