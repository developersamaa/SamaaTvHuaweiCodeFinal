package com.Samaatv.samaaapp3.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.Samaatv.samaaapp3.ConnectionDetector;
import com.Samaatv.samaaapp3.InternetSpeed;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.YoutubeVideoWeb;
import com.Samaatv.samaaapp3.adapter.MostWatchedAdapter;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.ContactList;
import com.Samaatv.samaaapp3.utils.DataCache;
import com.Samaatv.samaaapp3.utils.DevicePreference;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.taboola.android.TaboolaWidget;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 3/24/2017.
 */
public class JWPlayer_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    public static InterstitialAd interstitialAd = null;
    static PublisherInterstitialAd mPublisherInterstitialAd;
    SwipeRefreshLayout swipeLayout;
    RecyclerView most_recycle;
    TextView outputTextView;
    int currentapiversion = Build.VERSION.SDK_INT;
    ConnectionDetector cd;       // Connection detector class
    ProgressBar progressBar;    //Popular Video Progressbar
    Boolean isConnectedFast = false;    // flag for Internet connection Connection Fast status
    InternetSpeed is;         // internet speed class
    //LiveStream URL For SAMAA TV
    // String live_tv = "https://samaatr-hls.scaleengine.net/samaatr-live/play/samaatr_264k.stream/playlist.m3u8";
    String live_tv = "https://samaatr-hls.secdn.net/samaatr-iphone/play/samaatr_264k.stream/playlist.m3u8";
    RelativeLayout live_ad;
    PublisherAdRequest adRequest1;
    PublisherAdRequest adRequest3;
    ImageView ytplayer;
    private MostWatchedAdapter most_adapter;
    private View parentView;
    private ArrayList<Contact> mostWatch_list;
    //    private JWPlayerSupportFragment mPlayerFragment;
//    private static JWPlayerView mPlayerView;
    private PublisherAdView view;
    private Boolean isStarted = false;
    private Boolean isVisible = false;
    /**
     * An instance of our event handling class
     */

    // Declare variables
    private ProgressDialog pDialog;
    private VideoView playerView;
    private PublisherAdView mAdView1;
    //    private PublisherAdView mAdView3;
    private TaboolaWidget taboolaWidget;

    public JWPlayer_Fragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onStart()
    {
        super.onStart();

        isStarted = true;
        if (isVisible && isStarted)
        {
            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                mostWatched();
                DevicePreference.getInstance().setFromNotif(false);
            }
            else
            {
                mostWatched();
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        ViewGroup rootView = checkScreenAndSetLayout(inflater, container);

        init(rootView);
        checkingVersion();
        swipingAndRecyclerView(rootView);
        return rootView;
    }

    private ViewGroup checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup container)
    {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        ViewGroup rootView;
        if (diagonalInches >= 6.5)
        {
            // 6.5inch device or bigger
            rootView = (ViewGroup) inflater.inflate(R.layout.activity_jwplayerview_large, container, false);
        }
        else
        {
            // smaller device
            rootView = (ViewGroup) inflater.inflate(R.layout.activity_jwplayerview, container, false);
        }
        return rootView;
    }

    private void init(final ViewGroup rootView)
    {

        cd = new ConnectionDetector(getActivity());

        adRequest1 = new PublisherAdRequest.Builder().build();
        adRequest3 = new PublisherAdRequest.Builder().build();
        mAdView1 = (PublisherAdView) rootView.findViewById(R.id.ad_view1);
//        mAdView3 = (PublisherAdView) rootView.findViewById(R.id.ad_view3);
        live_ad = (RelativeLayout) rootView.findViewById(R.id.live_ad);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        outputTextView = (TextView) rootView.findViewById(R.id.output);
        ytplayer = (ImageView) rootView.findViewById(R.id.ytplayer);
        taboolaWidget = (TaboolaWidget) rootView.findViewById(R.id.taboola_view);
        videoPalayerAspects();
        implTaboolaAd();
        isConnectedFast = is.isConnectedFast(getActivity());
    }

    private void implTaboolaAd()
    {
        taboolaWidget.setPublisher("samaa-androidapp-sdkstandard")
                .setMode("thumbnails-a")
                .setPlacement("App Below Article Thumbnails")
                .setPageUrl("https://play.google.com/store/apps/details?id=com.Samaatv.samaaapp3")
                .setPageType("article")
                .setTargetType("mix");
        taboolaWidget.setInterceptScroll(true);
        taboolaWidget.setMinimumHeight(taboolaWidget.getHeight());
        taboolaWidget.fetchContent();
    }

    private void videoPalayerAspects()
    {
        ad_live_videoPlay();
        setAndProcessingAd();
        mAdView1.loadAd(adRequest1);
//        mAdView3.loadAd(adRequest3);
    }

    private void setAndProcessingAd()
    {
        PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
        mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
        mPublisherAdView.setAdUnitId("/14309701/and-en/live/and-en.live.mrec-1");
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
        live_ad.addView(mPublisherAdView);
        mPublisherAdView.loadAd(publisherAdRequestBuilder.build());
    }

    private void checkingVersion()
    {

        //Support for older api versions of android
        if (currentapiversion <= 15)
        {

            if (mPublisherInterstitialAd != null && mPublisherInterstitialAd.isLoaded())
            {
//                mPublisherInterstitialAd.show();
            }
            else
            {
//                PublisherAdRequest publisherAdRequest = new PublisherAdRequest.Builder().build();
//                mPublisherInterstitialAd.loadAd(publisherAdRequest);
            }

            String videoUrl = "rtsp://38.96.148.99:1935/live/bb";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(videoUrl));
            startActivity(i);
        }
    }

    private void swipingAndRecyclerView(ViewGroup rootView)
    {

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(R.color.toolbar_layoutcolor,
                R.color.heading1_black,
                R.color.heading2_black,
                R.color.blue);

        most_recycle = (RecyclerView) rootView.findViewById(R.id.pop_videos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        //RecyclerView for vertical (Tiles) layout
        most_recycle.setLayoutManager(layoutManager);
        most_recycle.setNestedScrollingEnabled(false);
        most_recycle.setHasFixedSize(true);
    }

    private void ad_live_videoPlay()
    {

//        mPlayerView.addOnFullscreenListener(this);
//        new KeepScreenOnHandler(mPlayerView, getActivity().getWindow());
//        mEventHandler = new JWEventHandler(mPlayerView, outputTextView);
//
//           List<PlaylistItem> playlist = new ArrayList<PlaylistItem>();
//           // Build a PlaylistItem and assign adSchedule
//           PlaylistItem video = new PlaylistItem.Builder()
//                   .file("https://samaatr-hls.scaleengine.net/samaatr-live/play/samaatr_264k.stream/playlist.m3u8")
//                   .build();
//
//           playlist.add(video);
//           // Create your player config
//           PlayerConfig playerConfig = new PlayerConfig.Builder()
//                   .playlist(playlist)
//                   .autostart(true)
//                   .build();
//            // Setup your player with the config
//            mPlayerView.setup(playerConfig);

        // YouTube webview starts here
        ytplayer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent in = new Intent(getContext(), YoutubeVideoWeb.class);
                in.putExtra("videourl", "https://www.youtube.com/embed/live_stream?channel=UCJekW1Vj5fCVEGdye_mBN6Q&autoplay=1");
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(in);
            }
        });

    }


    private void mostWatched()
    {
//        mostWatch_list = DataPreference.getInstance().getMostWatch_list();
        mostWatch_list = DataCache.retrieveMostWatched(getActivity());

        if (mostWatch_list != null)
        {
            most_adapter = new MostWatchedAdapter(getActivity(), mostWatch_list);
            most_recycle.setAdapter(most_adapter);
            progressBar.setVisibility(View.GONE);
        }
        else
        {
            mostWatch_list = new ArrayList<Contact>();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void mostWatchedRefreshList()
    {
        if (InternetConnection.checkConnection(getActivity()))
        {

            ApiService api = RetroClient.getApiService();
            Call<ContactList> call = api.getWebAPI();
            call.enqueue(new Callback<ContactList>()
            {
                @Override
                public void onResponse(Call<ContactList> call, Response<ContactList> response)
                {
                    if (response.isSuccessful())
                    {

                        if (response.body() != null)
                        {
                            mostWatch_list = response.body().getMostWatched();

                            if (mostWatch_list != null)
                            {
                                most_adapter = new MostWatchedAdapter(getActivity(), mostWatch_list);
                                most_recycle.setAdapter(most_adapter);
                                progressBar.setVisibility(View.GONE);
                                DataCache.saveMostWatched(getActivity(), mostWatch_list);
                                swipeLayout.setRefreshing(false);
                            }
                            else
                            {
                                mostWatch_list = new ArrayList<Contact>();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Oops, Something went wrong !", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Something is wrong !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ContactList> call, Throwable t)
                {
                    progressBar.setVisibility(View.GONE);
                    if (t instanceof NullPointerException)
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError)
                    {
                        Toast.makeText(getActivity(), "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException)
                    {
                        Toast.makeText(getActivity(), "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException)
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getActivity(), "Internet Connection Not Available !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        // Set fullscreen when the device is rotated to landscape
//        mPlayerView.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE, true);
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onRefresh()
    {
        mostWatchedRefreshList();
    }

    @Override
    public void onResume()
    {
        // Let JW Player know that the app has returned from the background
        super.onResume();
//        mPlayerView.onResume();
        if (mAdView1 != null)
        {
            mAdView1.resume();
//            mAdView3.resume();
        }
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SamaaAppAnalytics.getInstance().trackScreenView("Live Stream Screen");
                SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "Live Stream Screen");
            }
        }, 6000);
    }

    @Override
    public void onPause()
    {
        if (mAdView1 != null)
        {
            mAdView1.pause();
//            mAdView3.pause();
        }
//        mPlayerView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        if (mAdView1 != null)
        {
            mAdView1.destroy();
//            mAdView3.destroy();
        }
//      try {
//          mPlayerView.onDestroy();
//      } catch (IllegalArgumentException ie){
//          Log.e("Illegal Exception","Bekar error",ie);
//      }

        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

//        if(isVisibleToUser){
//            mostWatched();
//        }

        isVisible = isVisibleToUser;
        if (isVisible && isStarted)
        {
            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                mostWatched();
                DevicePreference.getInstance().setFromNotif(false);
            }
            else
            {
                mostWatched();
            }
        }
    }
}
