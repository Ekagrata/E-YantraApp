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

import com.bumptech.glide.Glide;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.CommentResponse;
import com.example.rajneesh.googlesignin.R;

import java.util.ArrayList;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder> {
    int height,width;
    public interface OnItemClickListner{
        void OnItemClicked(int position);

    }

    Context context;
    ArrayList<CommentResponse> comments;
    CommentsRecyclerAdapter.OnItemClickListner listner;
    WindowManager windowManager;

    public CommentsRecyclerAdapter(Context context, ArrayList<CommentResponse> comments, CommentsRecyclerAdapter.OnItemClickListner listner, WindowManager windowManager) {
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
    public CommentsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.comment_row,parent,false);
        CommentsRecyclerAdapter.ViewHolder holder= new CommentsRecyclerAdapter.ViewHolder(itemView);
       // Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

        getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final CommentsRecyclerAdapter.ViewHolder holder, int position) {

        CommentResponse comment= comments.get(position);
        holder.username.setText(comment.getUser_name());
      //  Toast.makeText(context,comment.getUser_name(), Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.OnItemClicked(holder.getAdapterPosition());

            }
        });



        holder.commentwritten.setText(comment.getComments());



        // Picasso.get().load(.getPhoto()).resize(width,0).into(holder.photo);
         Glide.with(context).load(comment.getPhoto()).into(holder.photo);



    }




    @Override
    public int getItemCount() {
        return comments.size();
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
