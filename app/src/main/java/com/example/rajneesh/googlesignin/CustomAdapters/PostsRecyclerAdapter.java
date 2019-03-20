package com.example.rajneesh.googlesignin.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.PostsResponse;
import com.example.rajneesh.googlesignin.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostsRecyclerAdapter.ViewHolder> {
    int height,width;
    public interface OnItemClickListner{
        void OnItemClicked(int position);
        void OnCommentSelected(int position);
    }

    Context context;
    ArrayList<PostsResponse> posts;
    PostsRecyclerAdapter.OnItemClickListner listner;
    WindowManager windowManager;

    public PostsRecyclerAdapter(Context context, ArrayList<PostsResponse> posts, PostsRecyclerAdapter.OnItemClickListner listner, WindowManager windowManager) {
        this.context = context;
        this.posts = posts;
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
    public PostsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.post_row,parent,false);
        PostsRecyclerAdapter.ViewHolder holder= new PostsRecyclerAdapter.ViewHolder(itemView);
       // Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

        getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final PostsRecyclerAdapter.ViewHolder holder, int position) {

        PostsResponse post= posts.get(position);
        holder.username.setText(post.getUsername());
      //  Toast.makeText(context, post.getUsername(), Toast.LENGTH_SHORT).show();

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

//        Call<ResponseBody> call= APIClient.getInstance().getApi().getpostcount(post.getId());
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String no= response.body().toString();
//                if(no!=null){
//                    holder.comno.setText(no);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
        holder.postdesc.setText(post.getPost());



       // Picasso.get().load(.getPhoto()).resize(width,0).into(holder.photo);
         Glide.with(context).load(post.getPhoto()).into(holder.picture);



    }




    @Override
    public int getItemCount() {
        return (posts==null)?0:posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        CircleImageView picture;
        TextView postdesc;
        ImageView postpic;
        ImageView photo;
        Button comment;
        View itemView;
        TextView comno;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            username= itemView.findViewById(R.id.username);
            photo= itemView.findViewById(R.id.picture);
            postdesc= itemView.findViewById(R.id.postwritten);
            comment= itemView.findViewById(R.id.comment);
            picture= itemView.findViewById(R.id.picture);
            comno= itemView.findViewById(R.id.commentno);

        }
    }
}
