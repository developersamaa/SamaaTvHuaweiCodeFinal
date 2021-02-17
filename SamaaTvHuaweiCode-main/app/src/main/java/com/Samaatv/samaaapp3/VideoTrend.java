package com.Samaatv.samaaapp3;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.Samaatv.samaaapp3.activities.BaseActivity;

//import android.view.View;
//import android.widget.Button;

public class VideoTrend extends BaseActivity
{
    static final String KEY_VIDEO = "videourl";
    // XML node keys

   /* static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_DURATION = "duration";*/
    int currentapiversion = Build.VERSION.SDK_INT;
    ProgressDialog progDailog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.play_video);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        loadInterstitial();     // Base Activity wala

        Intent in = getIntent();
        // Get XML values from previous intent
        String videourl = in.getStringExtra(KEY_VIDEO);

        VideoView video = (VideoView) findViewById(R.id.vdo1);


        if (videourl != null)
        {
            video.setVideoURI(Uri.parse(videourl));
            video.setMediaController(new MediaController(this));
            video.requestFocus();
            video.setFocusableInTouchMode(true);
            video.setZOrderOnTop(true);
            video.requestFocus();
            video.start();
        }
        else
        {
            Toast.makeText(VideoTrend.this, "Sorry video is not available", Toast.LENGTH_SHORT).show();
            finish();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        progDailog = ProgressDialog.show(this, "Please wait ...", "Retrieving data ...", true);
        progDailog.setCanceledOnTouchOutside(false);

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {

            @Override
            public void onPrepared(MediaPlayer mp)
            {
                progDailog.dismiss();
            }
        });

        if (currentapiversion >= 17)
        {
            video.setOnInfoListener(new MediaPlayer.OnInfoListener()
            {

                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra)
                {
                    switch (what)
                    {
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            progDailog.show();
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            progDailog.dismiss();
                            break;
                    }
                    return false;
                }
            });
        }
        else
        {

        }

        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {

            @Override
            public void onCompletion(MediaPlayer mp)
            {
              /*  if (english2.mPublisherInterstitialAd != null && english2.mPublisherInterstitialAd.isLoaded()) {
                    english2.mPublisherInterstitialAd.show();
                } else {


                    //Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
                   *//* PublisherAdRequest publisherAdRequest = new PublisherAdRequest.Builder().build();
                    Splash.mPublisherInterstitialAd.loadAd(publisherAdRequest);*//*
                    finish();
                }*/
            }
        });


        progDailog.setCancelable(true);
        progDailog.setOnCancelListener(new OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                progDailog.dismiss();


                //  SendBack Code
                // finish();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        showInterstitial();     // Base Activity wala
        finish();

    }
}




