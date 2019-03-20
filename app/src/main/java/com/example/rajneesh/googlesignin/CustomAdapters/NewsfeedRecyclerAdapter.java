package com.example.rajneesh.googlesignin.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.NewsFeedResponse;
import com.example.rajneesh.googlesignin.R;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class NewsfeedRecyclerAdapter extends RecyclerView.Adapter<NewsfeedRecyclerAdapter.ViewHolder>{

    int height,width;
    int id;
    String comno;
    public interface OnItemClickListner{
        void OnItemClicked(int position);
        void OnCommentSelected(int position);
        int getid(int position);

    }

    Context context;
    ArrayList<NewsFeedResponse> feeds;
    NewsfeedRecyclerAdapter.OnItemClickListner listner;
    WindowManager windowManager;

    public NewsfeedRecyclerAdapter(Context context, ArrayList<NewsFeedResponse> feeds, NewsfeedRecyclerAdapter.OnItemClickListner listner, WindowManager windowManager) {
        this.context = context;
        this.feeds = feeds;
        this.listner = listner;
        this.windowManager=windowManager;
    }

    public void getScreenSize(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height=displayMetrics.heightPixels;
        width=displayMetrics.widthPixels;
    }

    @Override
    public NewsfeedRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.newsfeed_row,parent,false);
        NewsfeedRecyclerAdapter.ViewHolder holder= new NewsfeedRecyclerAdapter.ViewHolder(itemView);
       // Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

        getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final NewsfeedRecyclerAdapter.ViewHolder holder, int position) {

        NewsFeedResponse feed= feeds.get(position);
        holder.username.setText("By~ "+feed.getUsername());
      //  Toast.makeText(context, feed.getUsername(), Toast.LENGTH_SHORT).show();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnCommentSelected(holder.getAdapterPosition());
            }
        });
        id= listner.getid(holder.getAdapterPosition());
        Call<Response> call= APIClient.getInstance().getApi().getnewscomno(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                try {
                    Response response1 = response.body();
                    comno = response1.getMessage();
                    Log.d("comno in adapter", comno);
                    holder.commentno.setText(comno);
                }
                catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
//        Log.d("comno in adapter",comno);
//        if(id){ holder.commentno.setText(0+"");}
//        else{
//           holder.commentno.setText(comno);}

        holder.feeddesc.setText(feed.getDesc());
        holder.title.setText(feed.getTitle());





        //Picasso.get().load(.getPhoto()).resize(width,0).into(holder.photo);
        Glide.with(context).load(feed.getPhotolink()).into(holder.postpic);



    }




    @Override
    public int getItemCount() {
        return (feeds==null)?0:feeds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView title;
        TextView feeddesc;
        ImageView postpic;
        ImageView photo;
        Button comment;
        TextView commentno;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            username= itemView.findViewById(R.id.username);
            photo= itemView.findViewById(R.id.picture);
            feeddesc= itemView.findViewById(R.id.feeddesc);
            postpic= itemView.findViewById(R.id.postpic);
            title= itemView.findViewById(R.id.title);
            comment= itemView.findViewById(R.id.comment);
            commentno= itemView.findViewById(R.id.commentno);

        }
    }
}
