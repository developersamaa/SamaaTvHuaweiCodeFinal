package com.Samaatv.samaaapp3.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.Samaatv.samaaapp3.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdsActivityUrdu extends AppCompatActivity
{

    InterstitialAd mInterstitialAd;
    private String TAG = AdsActivityUrdu.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_activity_urdu);

        String getLive = getIntent().getStringExtra("liveFrag");
        String getEdit = getIntent().getStringExtra("editFrag");
        String getTv = getIntent().getStringExtra("tvFrag");

        String ytWeb = getIntent().getStringExtra("ytWeb");
        String vtWeb = getIntent().getStringExtra("vtTrend");
        String edWeb = getIntent().getStringExtra("edWeb");

        String adUnitLive = "/14309701/and-ur/and-ur.interstitial";


        if (getLive != null)
        {
            setAdUnit(adUnitLive);
        }
        if (getEdit != null)
        {
            setAdUnit(adUnitLive);
        }
        if (getTv != null)
        {
            setAdUnit(adUnitLive);
        }

        if (ytWeb != null)
        {
            setAdUnit(adUnitLive);
        }
        if (vtWeb != null)
        {
            setAdUnit(adUnitLive);
        }
        if (edWeb != null)
        {
            setAdUnit(adUnitLive);
        }
    }

    private void setAdUnit(String adUnit)
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(adUnit);


        loadAd();
        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdFailedToLoad(int i)
            {
                super.onAdFailedToLoad(i);
                finish();
            }

            @Override
            public void onAdLoaded()
            {
                super.onAdLoaded();
                showInterstitial();
            }

            @Override
            public void onAdClosed()
            {
                super.onAdClosed();
                loadAd();
            }
        });
    }

    public void loadAd()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void showInterstitial()
    {
        if (mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
            finish();
        }
    }
}
