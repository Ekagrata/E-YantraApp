package com.example.rajneesh.googlesignin.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LginAuthActivity extends AppCompatActivity {
    EditText username,password;
    TextView land;
    LinearLayout linear;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgin_auth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linear= findViewById(R.id.linear);
        username= findViewById(R.id.username);
        password = findViewById(R.id.password);
        land= findViewById(R.id.loginland);
        land.setVisibility(View.GONE);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user,pass,method="auth";
                user= username.getText().toString();
                pass= password.getText().toString();
               // Toast.makeText(LginAuthActivity.this, user +" "+ pass, Toast.LENGTH_SHORT).show();
                Call<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response> call= APIClient.getInstance().getApi().getResponse(user,pass);
                call.enqueue(new Callback<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response>() {
                    @Override
                    public void onResponse(Call<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response> call, Response<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response> response) {
                        com.example.rajneesh.googlesignin.ResponseHolderClasses.Response response1= response.body();
                        if(response1!=null) {
                            if (response1.getMessage().equals("failed")) {
                                Toast.makeText(LginAuthActivity.this, "Please enter correct username and password", Toast.LENGTH_SHORT).show();
                            }
                            else if(response1.getMessage().equals("success")){
                                finish();
                                //Intent intent= new Intent(LginAuthActivity.this,landpage.class);
                               // startActivity(intent);

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<com.example.rajneesh.googlesignin.ResponseHolderClasses.Response> call, Throwable t) {
                        Toast.makeText(LginAuthActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
//              BackgroundTask backgroundTask= new BackgroundTask(LginAuthActivity.this);backgroundTask.execute(method,user);
//                BackgroundTask gettask= new BackgroundTask(LginAuthActivity.this);
//                gettask.execute("abc");
            }
        });


    }

}
