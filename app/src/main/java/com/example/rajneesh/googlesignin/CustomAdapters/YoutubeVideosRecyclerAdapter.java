package com.example.rajneesh.googlesignin.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rajneesh.googlesignin.R;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.YoutubeVideoResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YoutubeVideosRecyclerAdapter extends RecyclerView.Adapter<YoutubeVideosRecyclerAdapter.ViewHolder> {

    int height,width;
    public interface OnItemClickListner{
        void OnItemClicked(int position);
    }

    Context context;
    ArrayList<YoutubeVideoResponse> videos;
    OnItemClickListner listner;
    WindowManager windowManager;

    public YoutubeVideosRecyclerAdapter(Context context, ArrayList<YoutubeVideoResponse> videos, OnItemClickListner listner,WindowManager windowManager) {
        this.context = context;
        this.videos = videos;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.youtube_video_row,parent,false);
        ViewHolder holder= new ViewHolder(itemView);
    //    Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

        getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        YoutubeVideoResponse video= videos.get(position);
        holder.title.setText(video.getName());
     //   Toast.makeText(context, video.getName(), Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());
            }
        });



        Picasso.get().load(video.getPhoto()).resize(width,0).into(holder.photo);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageButton arrow;
        ImageView photo;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            title= itemView.findViewById(R.id.title);
            photo= itemView.findViewById(R.id.photo);
            arrow= itemView.findViewById(R.id.arrow);

        }
    }


}
