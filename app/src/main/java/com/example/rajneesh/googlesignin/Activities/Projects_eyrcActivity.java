package com.example.rajneesh.googlesignin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.R;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.YoutubeVideoResponse;
import com.example.rajneesh.googlesignin.CustomAdapters.YoutubeVideosRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Projects_eyrcActivity extends AppCompatActivity {
    YoutubeVideosRecyclerAdapter adapter;
    ArrayList<YoutubeVideoResponse> videos;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_eyrc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("eYRDC");
        recyclerView= findViewById(R.id.list);
        videos= new ArrayList<>();



        fetchVideos();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
       // recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        
    }

    private void fetchVideos(){
        Call<List<YoutubeVideoResponse>> call= APIClient.getInstance().getApi().getProjectVideos();
        call.enqueue(new Callback<List<YoutubeVideoResponse>>() {
            @Override
            public void onResponse(Call<List<YoutubeVideoResponse>> call, Response<List<YoutubeVideoResponse>> response) {
                videos= (ArrayList)response.body();
                adapter= new YoutubeVideosRecyclerAdapter(Projects_eyrcActivity.this, videos, new YoutubeVideosRecyclerAdapter.OnItemClickListner() {
                    @Override
                    public void OnItemClicked(int position) {
                        YoutubeVideoResponse video= videos.get(position);
                        Intent intent= new Intent(Projects_eyrcActivity.this,YoutubePlayerActivity.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("url",video.getUrl());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }, Projects_eyrcActivity.this.getWindowManager());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
               // Toast.makeText(Projects_eyrcActivity.this, "info fetched"+videos.get(1).getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<YoutubeVideoResponse>> call, Throwable t) {
                Toast.makeText(Projects_eyrcActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

}
