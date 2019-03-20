package com.example.rajneesh.googlesignin.Activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.rajneesh.googlesignin.R;

public class elsiActivity extends AppCompatActivity {
    ImageButton mailus;
    ImageButton download;
    ImageButton maps;
    ImageButton resources;
    ViewFlipper viewFlipper;
    long queue_id;
    DownloadManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elsi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("eLSI");
        viewFlipper= findViewById(R.id.viewflipper);
        int image[]= {R.drawable.gal123,R.drawable.gal11,R.drawable.gal13,R.drawable.gal14,R.drawable.fireb};
       for(int i=0;i<image.length;i++){
           flipimages(image[i]); }

        BroadcastReceiver receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action= intent.getAction();
                if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
                    DownloadManager.Query req_query= new DownloadManager.Query();
                    req_query.setFilterById(queue_id);

                    Cursor cursor= dm.query(req_query);
                    if(cursor.moveToFirst()){
                        int col_index= cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if(DownloadManager.STATUS_SUCCESSFUL==cursor.getInt(col_index)){
                            Toast.makeText(elsiActivity.this, "Download Successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(elsiActivity.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };

        registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        maps= findViewById(R.id.maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(elsiActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        resources= findViewById(R.id.resources);
        resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(elsiActivity.this,ResourcesActivity.class);
                startActivity(intent);
            }
        });

        download= findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_VIEW);
                String url = "http://www.e-yantra.org/img/e-Yantra_Pamphlet2018.pdf";
                shareIntent.setData(Uri.parse(url));
                startActivity(Intent.createChooser(shareIntent,"Choose your Browser"));
//                dm= (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//                DownloadManager.Request request= new DownloadManager.Request(Uri.parse("http://www.e-yantra.org/img/e-Yantra_Pamphlet2018.pdf"));
//                queue_id= dm.enqueue(request);

            }
        });
        mailus= findViewById(R.id.mailus);
        mailus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SENDTO);
                shareIntent.setData(Uri.parse("mailto:helpdesk@e-yantra.org"));

                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"For setting up lab");
                //shareIntent.putExtra(Intent.EXTRA_TEXT,"info");

                startActivity(Intent.createChooser(shareIntent,"Open Mail"));
            }
        });


    }


    public void openURL(View view){

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_VIEW);
        String url = "http://gdgnd.org";
        shareIntent.setData(Uri.parse(url));
        startActivity(Intent.createChooser(shareIntent,"Share text abcd"));

    }

    public  void flipimages(int image){
        ImageView imageView= new ImageView(this);
        Glide.with(this).load(image).into(imageView);
       // imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);

        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

}
