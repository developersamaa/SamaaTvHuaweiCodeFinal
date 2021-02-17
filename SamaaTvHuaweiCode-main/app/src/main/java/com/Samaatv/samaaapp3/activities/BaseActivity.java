package com.Samaatv.samaaapp3.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Administrator on 5/16/2017.
 */
public abstract class BaseActivity extends AppCompatActivity
{

    InterstitialAd mInterstitialAd;

    protected void loadInterstitial()
    {
        String adUnitLive = "/14309701/and-en/and-en.interstitial";
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(adUnitLive);
        loadAd();

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
//                showInterstitial();
            }

            @Override
            public void onAdClosed()
            {
                super.onAdClosed();

            }
        });
    }

    protected void loadAd()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    protected void showInterstitial()
    {
        if (mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();

        }
        else
        {
            loadAd();
        }
    }

}
