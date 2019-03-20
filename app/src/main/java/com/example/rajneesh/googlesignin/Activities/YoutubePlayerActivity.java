package com.example.rajneesh.googlesignin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayerActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
         url= bundle.getString("url");


        youTubePlayerView = findViewById(R.id.youtubeplayerview);

            onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.setFullscreen(true);
                    youTubePlayer.loadVideo(url);

//                    if(token.equals("elsi")){
//                        Toast.makeText(YoutubePlayerActivity.this,token,Toast.LENGTH_SHORT).show();
//                      youTubePlayer.loadVideo("CUJ3tFEWvSo");
//                    }
//                    else if(token.equals("efsi")) {
//                        Toast.makeText(YoutubePlayerActivity.this,token,Toast.LENGTH_SHORT).show();
//
//                    }

                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                   Log.d("youtube","Initialization failure");
                   // Toast.makeText(YoutubePlayerActivity.this,"Initialization failure",Toast.LENGTH_SHORT).show();

                }
            };
        youTubePlayerView.initialize("@string/api_key", onInitializedListener);
    }
}
