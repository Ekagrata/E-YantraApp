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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.CommentResponse;
import com.example.rajneesh.googlesignin.CustomAdapters.CommentnewsRecyclerAdapter;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.CommentnewsResponse;
import com.example.rajneesh.googlesignin.CustomAdapters.CommentsRecyclerAdapter;
import com.example.rajneesh.googlesignin.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

TextView username;
Button comentbutton;
CircleImageView picture;
EditText comment;
RecyclerView recyclerView;
String photo;
CommentsRecyclerAdapter adapter;
CommentnewsRecyclerAdapter newsadapter;
int id;
String token;
ArrayList<CommentResponse> comments;
ArrayList<CommentnewsResponse> comnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        id= bundle.getInt("id");
        token= bundle.getString("token");





      //  Toast.makeText(this, "id="+id, Toast.LENGTH_SHORT).show();


        username= findViewById(R.id.username);
        SharedPreferences sharedPreferences= getSharedPreferences("googlesignin", Context.MODE_PRIVATE);
        String name= sharedPreferences.getString("name","");
        username.setText(name);
         photo= sharedPreferences.getString("photo","");
        if(photo==null){
            photo="https://www.daviker.co.uk/wp-content/uploads/profile-blank.png";
        }
        picture= findViewById(R.id.picture);
        Glide.with(this).load(photo).into(picture);
        comment= findViewById(R.id.writecomment);


        comentbutton= findViewById(R.id.commentbutton);

        comentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comm= comment.getText().toString();
                String user= username.getText().toString();
                if(token.equals("post")) {
                    int postid = id;
                  //  Toast.makeText(CommentActivity.this, comm, Toast.LENGTH_SHORT).show();

                    Call<ResponseBody> call = APIClient.getInstance().getApi().putComments(user, comm, postid,photo);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                            //Response response1= response.body();
                        //    Toast.makeText(CommentActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(CommentActivity.this, "Error occured. Check your internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else if(token.equals("newsfeed")){
                    int feedid = id;
                  //  Toast.makeText(CommentActivity.this, comm, Toast.LENGTH_SHORT).show();

                    Call<ResponseBody> call = APIClient.getInstance().getApi().putfeedComments(user, comm, feedid);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                            //Response response1= response.body();
                          //  Toast.makeText(CommentActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(CommentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                fetchComments();
                }



            });


        recyclerView= findViewById(R.id.commentlist);
        comments= new ArrayList<>();
        comnews= new ArrayList<>();
        fetchComments();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void fetchComments() {
        if (token.equals("post")) {
            Call<List<CommentResponse>> call = APIClient.getInstance().getApi().getComments(id);
            call.enqueue(new Callback<List<CommentResponse>>() {
                @Override
                public void onResponse(Call<List<CommentResponse>> call, Response<List<CommentResponse>> response) {
                    comments = (ArrayList) response.body();
                    adapter = new CommentsRecyclerAdapter(CommentActivity.this, comments, new CommentsRecyclerAdapter.OnItemClickListner() {
                        @Override
                        public void OnItemClicked(int position) {
                        //    Toast.makeText(CommentActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        }
                    }, getWindowManager());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<CommentResponse>> call, Throwable t) {
                //    Toast.makeText(CommentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(token.equals("newsfeed")){
            Call<List<CommentnewsResponse>> call = APIClient.getInstance().getApi().getfeedcom(id);
            call.enqueue(new Callback<List<CommentnewsResponse>>() {
                @Override
                public void onResponse(Call<List<CommentnewsResponse>> call, Response<List<CommentnewsResponse>> response) {
                    comnews = (ArrayList) response.body();
                    newsadapter = new CommentnewsRecyclerAdapter(CommentActivity.this, comnews, new CommentnewsRecyclerAdapter.OnItemClickListner() {
                        @Override
                        public void OnItemClicked(int position) {
                         //   Toast.makeText(CommentActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        }
                    }, getWindowManager());
                    recyclerView.setAdapter(newsadapter);
                    newsadapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<CommentnewsResponse>> call, Throwable t) {
                    Toast.makeText(CommentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
