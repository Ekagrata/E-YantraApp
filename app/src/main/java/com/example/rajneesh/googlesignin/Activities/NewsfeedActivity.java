package com.example.rajneesh.googlesignin.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.NewsFeedResponse;
import com.example.rajneesh.googlesignin.CustomAdapters.NewsfeedRecyclerAdapter;
import com.example.rajneesh.googlesignin.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsfeedActivity extends AppCompatActivity {

    TextView comment,username;
    EditText title;
    EditText desc;
    TextView upload;
    RecyclerView recyclerView;
    int id;
    ArrayList<NewsFeedResponse> feeds;
    NewsfeedRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        upload= findViewById(R.id.upload);
        title= findViewById(R.id.title);
        desc= findViewById(R.id.feeddesc);
        recyclerView= findViewById(R.id.feedlist);
        username= findViewById(R.id.username);

        SharedPreferences sharedPreferences= getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
        String name= sharedPreferences.getString("name","");
        username.setText(name);
        feeds= new ArrayList<>();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tit= title.getText().toString();
                String des= desc.getText().toString();
                String user= username.getText().toString();
                retrofit2.Call<ResponseBody> call= APIClient.getInstance().getApi().putfeed(user,des);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(NewsfeedActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(NewsfeedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                fetchfeeds();
            }
        });

        fetchfeeds();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void fetchfeeds() {
        retrofit2.Call<List<NewsFeedResponse>> call=APIClient.getInstance().getApi().getfeeds();
        call.enqueue(new Callback<List<NewsFeedResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<NewsFeedResponse>> call, Response<List<NewsFeedResponse>> response) {
                feeds= (ArrayList) response.body();
                adapter= new NewsfeedRecyclerAdapter(NewsfeedActivity.this, feeds, new NewsfeedRecyclerAdapter.OnItemClickListner() {
                    @Override
                    public void OnItemClicked(int position) {
                        Toast.makeText(NewsfeedActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnCommentSelected(int position) {
                        Intent intent= new Intent(NewsfeedActivity.this,CommentActivity.class);
                        int id= feeds.get(position).getNewsid();
                        Bundle bundle= new Bundle();
                        bundle.putInt("id",id);
                        bundle.putString("token","newsfeed");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public int getid(int position) {
                        return 0;
                    }
                },getWindowManager());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(retrofit2.Call<List<NewsFeedResponse>> call, Throwable t) {
                Toast.makeText(NewsfeedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
