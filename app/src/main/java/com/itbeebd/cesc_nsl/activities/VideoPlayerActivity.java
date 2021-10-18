package com.itbeebd.cesc_nsl.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.itbeebd.cesc_nsl.R;
import com.potyvideo.library.AndExoPlayerView;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoPlayerActivity extends YouTubeBaseActivity{

    private final String api_key = "A2GXEp_5yjILvY";

    private AndExoPlayerView andExoPlayerView;
    // Get reference to the view of Video player
    private YouTubePlayerView ytPlayerView;
    private YouTubePlayer youTubePlayer;
    private YouTubePlayer ytPlayer;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private String initializeFailed = "Player initialization failed";
    private String ytVideoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        andExoPlayerView = findViewById(R.id.andExoPlayerView);
        ytPlayerView = findViewById(R.id.ytPlayer);

        initYT();

        if(getIntent().hasExtra("web_url")){
           // playVideo(getIntent().getStringExtra("web_url"));
          //  youtubeSetupAndPlay(getIntent().getStringExtra("web_url"));
            ytVideoUrl = getIntent().getStringExtra("web_url");

        }
        else if(getIntent().hasExtra("file_url")){

        }
    }

    private void initYT(){
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
              //  ytPlayer = youTubePlayer;
                if(ytVideoUrl != null) youTubePlayer.cueVideo(getYouTubeId(ytVideoUrl));
              //  youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                initializeFailed = youTubeInitializationResult.toString();
            }
        };
        ytPlayerView.initialize(api_key, onInitializedListener);
    }

    private void playVideo(String url){
        HashMap<String , String> extraHeaders = new HashMap<>();
        extraHeaders.put("url","bar");
        andExoPlayerView.setSource(url, extraHeaders);
    //    andExoPlayerView.setSource(url);
    }

    private void youtubeSetupAndPlay(String url){
        if(youTubePlayer != null){
            youTubePlayer.cueVideo(getYouTubeId(url));
//            youTubePlayer.loadVideo(getYouTubeId(url));
//            youTubePlayer.play();
        }
        else {
            Toast.makeText(this, initializeFailed, Toast.LENGTH_SHORT).show();
        }
    }

    public String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
        return "http://img.youtube.com/vi/"+ getYouTubeId(videoUrl) + "/0.jpg";
    }

    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            System.out.println(">>>>>>>>>>matcher.group()  " + matcher.group());
            return matcher.group();
        } else {
            System.out.println(">>>>>>>>>>matcher.group error");
            return "error";
        }
    }

//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//        this.youTubePlayer = youTubePlayer;
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//      //  Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
//
//        System.out.println("YT failed >>>>>>> " + YouTubeInitializationResult.DEVELOPER_KEY_INVALID );
//        System.out.println("YT failed >>>>>>> " + YouTubeInitializationResult.SERVICE_INVALID );
//        System.out.println("YT failed >>>>>>> " + YouTubeInitializationResult.INVALID_APPLICATION_SIGNATURE );
//
//        initializeFailed = youTubeInitializationResult.toString();
//    }
}