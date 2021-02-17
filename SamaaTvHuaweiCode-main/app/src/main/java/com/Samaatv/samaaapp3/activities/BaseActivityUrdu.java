package com.Samaatv.samaaapp3.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Administrator on 5/17/2017.
 */
public abstract class BaseActivityUrdu extends AppCompatActivity
{

    InterstitialAd mInterstitialAd;

    protected void loadInterstitialUrdu()
    {
        String adUnitLive = "/14309701/and-ur/and-ur.interstitial";
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(adUnitLive);
        loadAdUrdu();

        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdFailedToLoad(int i)
            {
                super.onAdFailedToLoad(i);

            }

            @Override
            public void onAdLoaded()
            {
                super.onAdLoaded();
//                showInterstitialUrdu();
            }

            @Override
            public void onAdClosed()
            {
                super.onAdClosed();

            }
        });
    }

    protected void loadAdUrdu()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    protected void showInterstitialUrdu()
    {
        if (mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();

        }
        else
        {
            loadAdUrdu();
        }
    }
}
