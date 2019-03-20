package com.example.rajneesh.googlesignin.Activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.APIClient;
import com.example.rajneesh.googlesignin.R;
import com.example.rajneesh.googlesignin.CustomAdapters.VideoTutorialRecyclerAdapter;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.VideoTutorialResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourcesActivity extends AppCompatActivity {
    TextView software;
    ListView downloadList;
    String[] values;
    String[] urls;
    ArrayAdapter<String> adapter;
   //Adapter<String> adapter;
    VideoTutorialRecyclerAdapter videoAdapter;
    ArrayList<VideoTutorialResponse> videos;
    RecyclerView recyclerView,recyclerView1;

    DownloadManager dm;
    long queue_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        downloadList= findViewById(R.id.downloadlist);




        software= findViewById(R.id.softwarelink);
        software.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_VIEW);
                String uri="http://elsi.e-yantra.org/resources#softwares";
                shareIntent.setData(Uri.parse(uri));
                startActivity(Intent.createChooser(shareIntent,"Choose your Browser"));
            }
        });


    //    downloadList= findViewById(R.id.downloadlist);
        urls= new String[]{"https://drive.google.com/file/d/0Bw9Usvm00eZlLVVqdjAxdEM5TjA/view","https://drive.google.com/file/d/0Bw9Usvm00eZlVjNCdkM3YkswMjg/view","https://drive.google.com/file/d/1KPHsGkT8Y3u59Te1kzjqbRGjYMbappk_/view","https://drive.google.com/file/d/1C7BfSQMHAsjC2scVinslO9JxSWjmm5rQ/view"};
        values=new String[]{"Experimants on Firebird V (ATmega2560)","Datasheets And Manuals","AVRDude for Firebird V Robot","AVRDude for Spark V Robot"};
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,values);

        //adapter= new RecyclerView.Adapter<String>(this,)

        recyclerView= findViewById(R.id.videotutlist);
        videos= new ArrayList<>();

        fetchvideos();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        downloadList.setAdapter(adapter);
        downloadList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_VIEW);
                String url = urls[i];
                shareIntent.setData(Uri.parse(url));
                startActivity(Intent.createChooser(shareIntent,"Choose your Browser"));
            }
        });





    }


    public void fetchvideos(){
        Call<List<VideoTutorialResponse>> call= APIClient.getInstance().getApi().getvideoTuts();
        call.enqueue(new Callback<List<VideoTutorialResponse>>() {
            @Override
            public void onResponse(Call<List<VideoTutorialResponse>> call, Response<List<VideoTutorialResponse>> response) {
                videos= (ArrayList) response.body();
                videoAdapter= new VideoTutorialRecyclerAdapter(ResourcesActivity.this, videos, new VideoTutorialRecyclerAdapter.OnItemClickListner() {
                    @Override
                    public void OnItemClicked(int position) {
                        VideoTutorialResponse video= videos.get(position);
                        Intent intent= new Intent(ResourcesActivity.this,YoutubePlayerActivity.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("url",video.getVideo_url());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void OnDownloadSelected(int position,String url) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_VIEW);
                        shareIntent.setData(Uri.parse(url));
                        startActivity(Intent.createChooser(shareIntent,"Choose your Browser"));

                    }
                },ResourcesActivity.this.getWindowManager());
                recyclerView.setAdapter(videoAdapter);
                videoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<VideoTutorialResponse>> call, Throwable t) {
                Toast.makeText(ResourcesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void view_click(View view){
        Intent intent= new Intent();
        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(intent);
    }


}
