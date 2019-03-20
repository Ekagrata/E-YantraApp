package com.example.rajneesh.googlesignin.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.ProfileResponse;
import com.example.rajneesh.googlesignin.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends Activity {

    ImageView backimage,circleimage;
    int uniqueid;
    TextView name,type, text_email,text_phone,text_college,text_company,text_year,text_branch,text_jobtitle;
    TextView email,phone,college,year,branch,company,jobtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backimage= findViewById(R.id.backimage);
        circleimage= findViewById(R.id.circleimage);
        name= findViewById(R.id.name);
        type= findViewById(R.id.type);
        text_jobtitle= findViewById(R.id.text_jobtitle);
        jobtitle= findViewById(R.id.jobtitle);
        text_email= findViewById(R.id.text_email);
        text_branch= findViewById(R.id.text_branch);
        text_phone= findViewById(R.id.text_phone);
        text_college= findViewById(R.id.text_college);
        text_company= findViewById(R.id.text_company);
        text_year= findViewById(R.id.text_year);
        email= findViewById(R.id.email);
        branch= findViewById(R.id.branch);
        phone= findViewById(R.id.phone);
        college= findViewById(R.id.college);
        company= findViewById(R.id.company);
        year= findViewById(R.id.year);


        Intent intent= getIntent();
        Bundle bundle= new Bundle();
        bundle= intent.getExtras();
        uniqueid=bundle.getInt("uniqueid");
        Log.d("unique",uniqueid+"");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(intent1);
                finish();
            }
        });



        Call<List<ProfileResponse>> call= APIClient.getInstance().getApi().getprofile(uniqueid);
        call.enqueue(new Callback<List<ProfileResponse>>() {
            @Override
            public void onResponse(Call<List<ProfileResponse>> call, Response<List<ProfileResponse>> response) {
                try {
                    ProfileResponse response1 = response.body().get(0);
                    name.setText(response1.getName());
                    email.setText(response1.getEmail());
                    phone.setText(response1.getPhone());
                    type.setText(response1.getType());
                    SharedPreferences sharedPreferences= getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
                    String prophoto= sharedPreferences.getString("photo","");
                    Glide.with(ProfileActivity.this).load(response1.getPhoto()).into(circleimage);
                    Glide.with(ProfileActivity.this).load(prophoto).into(circleimage);
                    Glide.with(ProfileActivity.this).load(R.drawable.profback).into(backimage);
                    if (response1.getBranch() == null) {
                        branch.setVisibility(View.GONE);
                        text_branch.setVisibility(View.GONE);
                    } else {
                        branch.setText(response1.getBranch());
                    }

                    if (response1.getCollege() == null) {
                        college.setVisibility(View.GONE);
                        text_college.setVisibility(View.GONE);
                    } else {
                        college.setText(response1.getCollege());
                    }

                    if (response1.getCompany() == null) {
                        company.setVisibility(View.GONE);
                        text_company.setVisibility(View.GONE);
                    } else {
                        company.setText(response1.getCompany());
                    }

                    if (response1.getYear() == null) {
                        year.setVisibility(View.GONE);
                        text_year.setVisibility(View.GONE);
                    } else {
                        year.setText(response1.getYear());
                    }

                    if (response1.getJobtitle() == null) {
                        jobtitle.setVisibility(View.GONE);
                        text_jobtitle.setVisibility(View.GONE);
                    } else {
                        jobtitle.setText(response1.getYear());
                    }
                }
                catch (Exception e){
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ProfileResponse>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
