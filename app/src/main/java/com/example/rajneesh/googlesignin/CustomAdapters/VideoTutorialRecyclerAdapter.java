package com.example.rajneesh.googlesignin.CustomAdapters;

import android.app.DownloadManager;
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
import com.example.rajneesh.googlesignin.ResponseHolderClasses.VideoTutorialResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoTutorialRecyclerAdapter extends RecyclerView.Adapter<VideoTutorialRecyclerAdapter.ViewHolder>{
    int height,width;
    DownloadManager dm;
    long queue_id;
    public interface OnItemClickListner{
        void OnItemClicked(int position);
        void OnDownloadSelected(int position,String url);
    }

    Context context;
    ArrayList<VideoTutorialResponse> videos;
    VideoTutorialRecyclerAdapter.OnItemClickListner listner;
    WindowManager windowManager;

    public VideoTutorialRecyclerAdapter(Context context, ArrayList<VideoTutorialResponse> videos, VideoTutorialRecyclerAdapter.OnItemClickListner listner, WindowManager windowManager) {
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
    public VideoTutorialRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.video_tutorials_row,parent,false);
        VideoTutorialRecyclerAdapter.ViewHolder holder= new VideoTutorialRecyclerAdapter.ViewHolder(itemView);
     //   Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

        getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final VideoTutorialRecyclerAdapter.ViewHolder holder, int position) {

        final VideoTutorialResponse video= videos.get(position);
        holder.title.setText(video.getTitle());
    //    Toast.makeText(context, video.getTitle(), Toast.LENGTH_SHORT).show();

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

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnDownloadSelected(holder.getAdapterPosition(),video.getPdf_url());
            }
        });


        holder.desc.setText(video.getDescription());

        Picasso.get().load(video.getPhoto()).resize(width,0).into(holder.photo);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView photo;
        ImageButton download;
        ImageButton arrow;
        View itemView;
        TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            title= itemView.findViewById(R.id.title);
            photo= itemView.findViewById(R.id.photo);
            download= itemView.findViewById(R.id.downloadbutton);
            arrow= itemView.findViewById(R.id.arrow);
            desc= itemView.findViewById(R.id.desc);

        }
    }
}
