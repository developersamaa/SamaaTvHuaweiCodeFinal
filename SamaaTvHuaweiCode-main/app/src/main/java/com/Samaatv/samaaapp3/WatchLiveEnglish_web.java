package com.Samaatv.samaaapp3;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Samaatv.samaaapp3.adapter.MostWatchedAdapter;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.ContactList;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import samaaapp3_new.samaatv.com.newappsamaatv.adapter.DataAdapter;

//import com.google.ads.AdRequest;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;
//import android.widget.Toast;

public class WatchLiveEnglish_web extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    private final static String URL = "https://www.samaa.tv/samaaapptest.php"; //For medium_rectangle advertisement
    //"https://www.samaa.tv/samaaapptest.php"; previous email
    private final static String USERNAME = "samaaapp";
    private final static String PASSWORD = "samaaapp786";
    private final static String HOST = "http://www.samaa.tv";
    private final static String REALM = "Users Only";
    //private RecyclerView.Adapter pop_videos_adapter;
    //JSONArray pop_video;
    static PublisherInterstitialAd mPublisherInterstitialAd;
    SwipeRefreshLayout swipeLayout;
    //private String TAG = WatchLiveEnglish_web.class.getSimpleName();
    //private RecyclerView mRecyclerView;
    RecyclerView most_recycle;
    int currentapiversion = Build.VERSION.SDK_INT;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    //URL For Popular Videos
    //private static final String feedURL ="https://www.samaa.tv/videos/jfeedmostwatchedprograms/";
    // Connection detector class
    ConnectionDetector cd;
    //private WebView webViewbro;
    ProgressBar progressBar, progressBar1;
    // flag for Internet connection Connection Fast status
    Boolean isConnectedFast = false;
    // internet speed class
    InternetSpeed is;
    Button buttonPlay;
    Button buttonStop;
    private TabLayout tabLayout;
    private View parentView;
    private PublisherAdView mAdView;
    private MostWatchedAdapter most_adapter;
    //String currentapiversion= Build.VERSION_CODES;
    private ArrayList<Contact> mostWatch_list;
    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    //private AudioStreamActivity musicSrv;
    private Intent playIntent;
    //binding
    private boolean musicBound = false;
    public WatchLiveEnglish_web()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Fragment locked in landscape screen orientation
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        // Inflate the layout for this fragment
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.ramzan, container, false);

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        cd = new ConnectionDetector(getActivity());
		/*getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);*/
        mAdView = (PublisherAdView) rootView.findViewById(R.id.ad_view1);

//		PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
//		// Start loading the ad in the background.
//		mAdView.loadAd(adRequest);  mz

        isConnectedFast = is.isConnectedFast(getActivity());

//        // Create the InterstitialAd and set the adUnitId.
//		mPublisherInterstitialAd = new PublisherInterstitialAd(getActivity());
//		// Defined in res/values/strings.xml
//		mPublisherInterstitialAd.setAdUnitId(getString(R.string.interstitial_unit_id_eng));
//
//		PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
//		mPublisherInterstitialAd.loadAd(publisherAdRequestBuilder.build());   mz


        if (currentapiversion <= 15)
        {

            if (mPublisherInterstitialAd != null && mPublisherInterstitialAd.isLoaded())
            {
                mPublisherInterstitialAd.show();
            }
            else
            {


//				//Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
//				PublisherAdRequest publisherAdRequest = new PublisherAdRequest.Builder().build();
//				mPublisherInterstitialAd.loadAd(publisherAdRequest);    mz

            }

            String videoUrl = "rtsp://38.96.148.99:1935/live/bb";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(videoUrl));
            getActivity().finish();
            startActivity(i);
            //super.onBackPressed();
        }


        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeColors(R.color.toolbar_layoutcolor,
                R.color.heading1_black,
                R.color.heading2_black,
                R.color.blue);


        webView = (VideoEnabledWebView) rootView.findViewById(R.id.webView);

        most_recycle = (RecyclerView) rootView.findViewById(R.id.pop_videos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        //RecyclerView for vertical (Tiles) layout
        most_recycle.setLayoutManager(layoutManager);
        most_recycle.setNestedScrollingEnabled(false);
        most_recycle.setHasFixedSize(true);

        // Initialize the VideoEnabledWebChromeClient and set event handlers
        View nonVideoLayout = rootView.findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) rootView.findViewById(R.id.videoLayout); // Your own view, read class comments
        videoLayout.setVisibility(View.GONE);
        //noinspection all
        View loadingView = getActivity().getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView, most_recycle) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress)
            {
                // Your code...
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback()
        {
            @Override
            public void toggledFullscreen(boolean fullscreen)
            {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen)
                {
                    WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getActivity().getWindow().setAttributes(attrs);
                    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                    MainActivity.tabLayout.setVisibility(View.GONE);
                    //MainActivity.frameticker.setVisibility(View.GONE);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    //tabLayout.
                    if (android.os.Build.VERSION.SDK_INT >= 14)
                    {
                        //noinspection all
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                }
                else
                {
                    WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getActivity().getWindow().setAttributes(attrs);
                    ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                    MainActivity.tabLayout.setVisibility(View.VISIBLE);
                    //MainActivity.frameticker.setVisibility(View.VISIBLE);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    //tabLayout.setVisibility(View.VISIBLE);
                    if (android.os.Build.VERSION.SDK_INT >= 14)
                    {
                        //noinspection all
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });


        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar1 = (ProgressBar) rootView.findViewById(R.id.progressBar3);


        //webViewbro = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new ourViewClient(getActivity(), webView));
        webView.setWebChromeClient(webChromeClient);


        webView.getSettings().setDomStorageEnabled(true);

        webView.setHttpAuthUsernamePassword(HOST, REALM, USERNAME, PASSWORD);

        if (InternetConnection.checkConnection(getActivity()))
        {
            webView.loadUrl(URL);

            //Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
            Call<ContactList> call = api.getWebAPI();

            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<ContactList>()
            {
                @Override
                public void onResponse(Call<ContactList> call, Response<ContactList> response)
                {


                    if (response.isSuccessful())
                    {
                        /**
                         * Got Successfully
                         */
                        mostWatch_list = response.body().getMostWatched();


                        /**
                         * Binding that List to Adapter
                         */
                        most_adapter = new MostWatchedAdapter(getActivity(), mostWatch_list);
                        most_recycle.setAdapter(most_adapter);


                        //Dismiss Dialog
                        progressBar.setVisibility(View.GONE);
                                /*ticker_adapter = new DataAdapter(breakingList);
                                ticker_recycle.setAdapter(ticker_adapter);*/

                        //  adapter1 = new MyContactAdapter(MainActivity.this, trendList);
                        //listView.setAdapter(adapter);

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "No internet connection !", Toast.LENGTH_SHORT).show();
        }


        return rootView;

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
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int in)
                    {

                        getActivity().finish();


                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    /**///** Called when leaving the activity *//**//*
    @Override
    public void onPause()
    {

        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    //**//** Called when returning to the activity *//**//*
    @Override
    public void onResume()
    {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();

    }

    //**//** Called before the activity is destroyed *//**//*
    @Override
    public void onDestroy()
    {

        if (webView != null)
        {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    public void LiveNewsRefresh()
    {

        webView.loadUrl(URL);
        most_adapter = new MostWatchedAdapter(getActivity(), mostWatch_list);
        most_recycle.setAdapter(most_adapter);
        swipeLayout.setRefreshing(false);
    }

    public void onRefresh()
    {
        LiveNewsRefresh();
    }

    public class ourViewClient extends WebViewClient
    {
        private String loginCookie;
        private Context mContext;
        private WebView mWebView;


        public ourViewClient(Context context, WebView webview)
        {
            super();

            mContext = context;
            mWebView = webview;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url)
        {
			/*v.loadUrl(url);
			return true;*/

            if (url != null && url.startsWith("http://"))
            {
                v.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
            else if (url != null && url.startsWith("https://"))
            {
                v.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }

            else
            {
                return false;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);


            //Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setCookie(url, loginCookie);
            progressBar1.setVisibility(View.GONE);

        }

		/*@Override
		public void onReceivedError( WebView view, int errorCode, String description, String failingUrl ) {
			Toast.makeText(view.getContext(), "Error Loading Live Streaming..", Toast.LENGTH_LONG).show();
		}*/

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {
            // Handle the error
            Toast.makeText(view.getContext(), "Error Loading Live Streaming..", Toast.LENGTH_LONG).show();
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
        {
            // Redirect to deprecated method, so you can use it in all SDK versions
            onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
        }

        @Override
        public void onLoadResource(WebView view, String url)
        {
            CookieManager cookieManager = CookieManager.getInstance();
            loginCookie = cookieManager.getCookie(url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
        {
            super.onReceivedSslError(view, handler, error);

            // this will ignore the Ssl error and will go forward to your site
            handler.proceed();

        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, final HttpAuthHandler handler, final String host, final String realm)
        {


            String userName = null;
            String userPass = null;

            if (handler.useHttpAuthUsernamePassword() && view != null)
            {
                String[] haup = view.getHttpAuthUsernamePassword(host, realm);
                if (haup != null && haup.length == 2)
                {
                    userName = haup[0];
                    userPass = haup[1];
                }
            }

            if (userName != null && userPass != null)
            {
                handler.proceed(userName, userPass);
            }
            else
            {
                showHttpAuthDialog(handler, host, realm, null, null, null);
            }
        }

        private void showHttpAuthDialog(final HttpAuthHandler handler, final String host, final String realm, final String title, final String name, final String password)
        {


            String userName = "samaaapp";

            String userPass = "samaaapp786";

            mWebView.setHttpAuthUsernamePassword(host, realm, name, password);

            handler.proceed(userName, userPass);

        }

    }
}





