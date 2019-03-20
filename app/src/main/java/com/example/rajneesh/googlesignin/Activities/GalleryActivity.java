package com.example.rajneesh.googlesignin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.example.rajneesh.googlesignin.R;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    //RecyclerAdapter adapter1,adapter2,adapter3,adapter4;
    RecyclerView recyclerview1,recyclerview2,recyclerview3,recyclerview4;
    WindowManager windowManager;
    ArrayList<Integer> images;
  //  int[] im= {R.drawable.gal11,R.drawable.gal13,R.drawable.gal14,R.drawable.ey1,R.drawable.ey2,R.drawable.ey5,R.drawable.ey4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerview1= findViewById(R.id.recyclerview1);
        recyclerview2= findViewById(R.id.recyclerview2);
        recyclerview3= findViewById(R.id.recyclerview3);
        recyclerview4= findViewById(R.id.recyclerview4);


//        images= new ArrayList<>();
//        for(int i=0;i<im.length;i++){ images.add(im[i]);}
//        adapter1= new RecyclerAdapter(this, new RecyclerAdapter.OnItemClickListner() {
//            @Override
//            public void OnItemClicked(int position) {
//                Intent intent= new Intent(GalleryActivity.this,photoviewerActivity.class);
//                startActivity(intent);
//
//            }
//        }, images, this.getWindowManager());
//        recyclerview1.setAdapter(adapter1);
//        recyclerview1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerview1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
//        recyclerview1.setItemAnimator(new DefaultItemAnimator());
//
//        adapter1.notifyDataSetChanged();
//
//        adapter2= new RecyclerAdapter(this, new RecyclerAdapter.OnItemClickListner() {
//            @Override
//            public void OnItemClicked(int position) {
//                Intent intent= new Intent(GalleryActivity.this,photoviewerActivity.class);
//                startActivity(intent);
//
//            }
//        }, images, this.getWindowManager());
//        recyclerview2.setAdapter(adapter2);
//        recyclerview2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerview2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
//        recyclerview2.setItemAnimator(new DefaultItemAnimator());
//        adapter2.notifyDataSetChanged();
//
//        adapter3= new RecyclerAdapter(this, new RecyclerAdapter.OnItemClickListner() {
//            @Override
//            public void OnItemClicked(int position) {
//                Intent intent= new Intent(GalleryActivity.this,photoviewerActivity.class);
//                startActivity(intent);
//
//            }
//        }, images, this.getWindowManager());
//        recyclerview3.setAdapter(adapter3);
//        recyclerview3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerview3.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
//        recyclerview3.setItemAnimator(new DefaultItemAnimator());
//        adapter3.notifyDataSetChanged();
//
//        adapter4= new RecyclerAdapter(this, new RecyclerAdapter.OnItemClickListner() {
//            @Override
//            public void OnItemClicked(int position) {
//                Intent intent= new Intent(GalleryActivity.this,photoviewerActivity.class);
//                startActivity(intent);
//            }
//        }, images, this.getWindowManager());
//        recyclerview4.setAdapter(adapter4);
//        recyclerview4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerview4.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
//        recyclerview4.setItemAnimator(new DefaultItemAnimator());
//        adapter4.notifyDataSetChanged();



    }

}
