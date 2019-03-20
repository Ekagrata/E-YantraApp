package com.example.rajneesh.googlesignin.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rajneesh.googlesignin.ResponseHolderClasses.CommentnewsResponse;
import com.example.rajneesh.googlesignin.R;

import java.util.ArrayList;

public class CommentnewsRecyclerAdapter extends RecyclerView.Adapter<CommentnewsRecyclerAdapter.ViewHolder> {
    int height,width;
    public interface OnItemClickListner{
        void OnItemClicked(int position);

    }

    Context context;
    ArrayList<CommentnewsResponse> comments;
    CommentnewsRecyclerAdapter.OnItemClickListner listner;
    WindowManager windowManager;

    public CommentnewsRecyclerAdapter(Context context, ArrayList<CommentnewsResponse> comments, CommentnewsRecyclerAdapter.OnItemClickListner listner, WindowManager windowManager) {
        this.context = context;
        this.comments = comments;
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
    public CommentnewsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.comment_row,parent,false);
        CommentnewsRecyclerAdapter.ViewHolder holder= new CommentnewsRecyclerAdapter.ViewHolder(itemView);
      //  Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

        getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final CommentnewsRecyclerAdapter.ViewHolder holder, int position) {

        CommentnewsResponse comment= comments.get(position);
        holder.username.setText(comment.getUsername());
     //   Toast.makeText(context,comment.getUsername(), Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });



        holder.commentwritten.setText(comment.getComment());



        // Picasso.get().load(.getPhoto()).resize(width,0).into(holder.photo);
        // Glide.with(context).load("http://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).into(holder.poster);



    }




    @Override
    public int getItemCount() {
        return (comments.size()!=0)?comments.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView commentwritten;
        ImageView photo;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            username= itemView.findViewById(R.id.username);
            photo= itemView.findViewById(R.id.picture);
            commentwritten= itemView.findViewById(R.id.commentwritten);


        }
    }
}
