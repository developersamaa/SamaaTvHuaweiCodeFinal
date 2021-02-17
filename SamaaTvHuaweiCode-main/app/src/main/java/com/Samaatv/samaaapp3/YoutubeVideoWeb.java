package com.Samaatv.samaaapp3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

//import com.google.android.gms.analytics.GoogleAnalytics;

/**
 * Created by Shahzaib (ShazZ) on 5/26/2016.
 */
public class YoutubeVideoWeb extends Activity
{

    static PublisherInterstitialAd mPublisherInterstitialAd;
    ProgressBar progressBar;
    int currentapiversion = Build.VERSION.SDK_INT;
    InterstitialAd mInterstitialAd;
    // flag for Internet connection Connection Fast status
    Boolean isConnectedFast = false;
    // internet speed class class
    InternetSpeed is;
    String videourl, videosplit, editorformat, imageurl;
    private WebView webView;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_videos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);

        loadInterstitial();

        Bundle b = getIntent().getExtras();

        if (b != null)
        {

            videourl = b.getString("videourl");

        }

        //  imageurl = imageurl.replace("http://www.samaa.tv/","");

        if (currentapiversion <= 15)
        {

            // Starting new video
            Intent in = new Intent(getApplicationContext(), VideoTrend.class);
            in.putExtra("videourl", videourl);
            finish();
            startActivity(in);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);


        // Assign webclient.
        webView.setWebViewClient(new WebViewClient()
        {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Log.d("TAG", url);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                // Handle the error
                Toast.makeText(view.getContext(), "Error Loading Live Streaming..", Toast.LENGTH_SHORT).show();
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
            {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }


        });


        isConnectedFast = is.isConnectedFast(getApplicationContext());

        if (isConnectedFast)
        {
            webView.loadUrl(videourl);
        }
        else
        {
            showAlertDialog(YoutubeVideoWeb.this, "No or slow Internet Connection",
                    "Problem with internet connection.", false);
        }


    }

    private void loadInterstitial()
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
//                loadAd();
//                finish();
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
//                finish();
            }
        });
    }

    private void showInterstitial()
    {
        if (mInterstitialAd.isLoaded())
        {
           // mInterstitialAd.show();
//            finish();
        }
        else
        {
            //loadAd();
        }
    }

    private void loadAd()
    {
//    AdRequest adRequest = new AdRequest.Builder().build();
//        mInterstitialAd.loadAd(adRequest);
}

    public void showAlertDialog(Context context, String title, String message, Boolean status)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        alertDialog.setCanceledOnTouchOutside(false);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("Retry", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {


               /* Intent i = new Intent(getApplicationContext(), ListWorldNews.class);
                finish();
                startActivity(i);*/


            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        //GoogleAnalytics.getInstance(this).reportActivityStop(this);

    }

    protected void onStart()
    {
        super.onStart();
        //GoogleAnalytics.getInstance(this).reportActivityStart(this);

    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause()
    {
        super.onPause();

        if (webView != null)
        {
            webView.onPause();
            webView.pauseTimers();
        }
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume()
    {
        super.onResume();

        if (webView != null)
        {
            webView.onResume();
            webView.resumeTimers();
        }

    }

    @Override
    protected void onDestroy()
    {

        if (webView != null)
        {
            webView.destroy();
            webView = null;
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {

        try
        {
            showInterstitial();  // Base Activity wala

            if (webView.canGoBack())
            {
                webView.goBack();
                webView.stopLoading();
                webView.loadUrl("about:blank");
                webView.reload();
                webView = null;
//            webView.clearCache(true);

            }
            else
            {
                super.onBackPressed();
            }
        }
        catch (NullPointerException ne)
        {
            Log.e("NPE", "null pointer ka bekar exception", ne);
        }
    }

}
