package com.Samaatv.samaaapp3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

//import com.crashlytics.android.Crashlytics;

//import io.fabric.sdk.android.Fabric;

/**
 * Created by Shahzaib (ShazZ) on 6/18/2015.
 */

public class SplashScreen extends AppCompatActivity
{
    MediaPlayer mp;
    Boolean language_eng;
    Boolean language_urdu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getExtras() != null)
        {

            String news_lang = getIntent().getExtras().getString("post_language");
            String message = getIntent().getExtras().getString("message");
            String news_url = getIntent().getExtras().getString("url");
            String news_cat = getIntent().getExtras().getString("post_category");

            if (news_lang != null)
            {
                if (news_lang.contains("english"))
                {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("message", message);
                    intent.putExtra("news_cat", news_cat);
                    intent.putExtra("news_url", news_url);
                    startActivity(intent);
                    finish();
                }

                if (news_lang.contains("urdu"))
                {
                    Intent intent = new Intent(this, MainActivityUrdu.class);
                    intent.putExtra("message", message);
                    intent.putExtra("news_cat", news_cat);
                    intent.putExtra("news_url", news_url);
                    startActivity(intent);
                    finish();
                }
            }
            else
            {
                //Toast.makeText(SplashScreen.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        }


        // second checkbox
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);

        boolean checked2 = sharedPref.getBoolean("startup_sound", true);

        if (checked2)
        {
            mp = new MediaPlayer();
            mp = MediaPlayer.create(getBaseContext(), R.raw.samaa_notify);
            mp.start(); //Starts SamaaTV Splash Sound
        }
        //   SharedPreferences sharedPreflang = PreferenceManager.getDefaultSharedPreferences(Splash.this);
        language_eng = sharedPref.getBoolean("english", true);
        language_urdu = sharedPref.getBoolean("urdu", true);


        final ImageView img = (ImageView) findViewById(R.id.splash);
        final Animation ai = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        // final Animation ai2  = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);

        img.startAnimation(ai);
        ai.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {

                finish();

                if (language_eng)
                {
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                }
                else if (language_urdu)
                {
                    Intent i = new Intent(getBaseContext(), MainActivityUrdu.class);
                    startActivity(i);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }
}
