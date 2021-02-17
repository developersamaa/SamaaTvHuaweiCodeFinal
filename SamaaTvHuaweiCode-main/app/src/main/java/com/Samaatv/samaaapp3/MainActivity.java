package com.Samaatv.samaaapp3;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.Samaatv.samaaapp3.adapter.Nav_Adapter;
import com.Samaatv.samaaapp3.interfaces.OnClick;
import com.Samaatv.samaaapp3.model.Nav_Model;
import com.Samaatv.samaaapp3.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Samaatv.samaaapp3.activities.BaseActivity;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_home_fragment;
import com.Samaatv.samaaapp3.fragments.ByCategoryNews;
import com.Samaatv.samaaapp3.fragments.JWPlayer_Fragment;
import com.Samaatv.samaaapp3.utils.BackgroundTask;
import com.Samaatv.samaaapp3.utils.DevicePreference;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.NotificationUtils;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.ads.conversiontracking.AdWordsConversionReporter;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnClick
{
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "Permission Request";
    private static final int REQUEST_CONTACTS = 1;
    //SwipeRefreshLayout swipeLayout;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    static String user_email;
    private static String[] Permissions = {android.Manifest.permission.GET_ACCOUNTS};
    public boolean notifBool;
    AlertDialog.Builder builder;
    MediaPlayer mp;
    int count = 0;
    View custom_view;
    // JSON Ticker Just-In XML
    //  String URL = "";
    String accountName;
    int currentapiversion = Build.VERSION.SDK_INT;
//    public static TextView ticker_head;
//    public static LinearLayout frameticker;
    int ITEMS_COUNT = 11;
    SharedPreferences prefs1;
    String language;
    JWPlayer_Fragment jwPlayer;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String message;
    private String newsID;
    private String newsCat;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private InterstitialAd mInterstitialAd;
    private Nav_Adapter nav_adapter;
    List<Nav_Model> navList;
    RecyclerView RvNavItems;
    private DrawerLayout drawer;

    //function for getting user's email
    public static Account getAccount(AccountManager accountManager)
    {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0)
        {
            account = accounts[0];
        }
        else
        {
            account = null;
        }
        return account;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        checkScreenAndSetLayout();

        Calls_home_fragment.callHomeList(getApplicationContext());
        if (InternetConnection.checkConnection(getApplicationContext()))
        {
            new BackgroundTask(getApplicationContext()).execute();
        }

        AdWordsConversionReporter.reportWithConversionId(this.getApplicationContext(),
                "976003944", "EqPwCJa2vmQQ6May0QM", "1.00", false);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        recordScreenView();

        init();

        loadInterstitial();

        if (currentapiversion >= 21)
        {
            //builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.CustomDialog));
            builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialog);
            builder.setTitle("Welcome to SAMAATV App");
            builder.setIcon(R.drawable.samaa_logo);
            builder.setMessage(getApplicationContext().getString(R.string.lang_choose));
            builder.setCancelable(false);
        }
        else
        {
            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Welcome to SAMAATV App");
            builder.setIcon(R.drawable.samaa_logo);
            builder.setMessage(getApplicationContext().getString(R.string.lang_choose));
            builder.setCancelable(false);
        }

        prefs1 = android.preference.PreferenceManager.getDefaultSharedPreferences(this);//this==context
        language = prefs1.getString("choose_language", "");

        if (!prefs1.contains("FirstTime"))
        {
            //Other dialog code


            builder.setPositiveButton("English", new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {


                    if (isGoogelPlayInstalled() && InternetConnection.checkConnection(getApplicationContext()))
                    {

                        SharedPreferences.Editor editor = prefs1.edit();
                        editor.putBoolean("FirstTime", true);
                        editor.putString("choose_language", "English");
                        editor.apply();

                        // yahan acount k permission wala kam tha

                        // [START subscribe_topics to English]
                        FirebaseMessaging.getInstance().subscribeToTopic("english");
                        // [END subscribe_topics to English]
                        // Log and toast
                        DevicePreference.getInstance().setLanguage("English");
                        String msg = getString(R.string.eng_subscribed);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else if (InternetConnection.checkConnection(getApplicationContext()))
                    {

                        SharedPreferences.Editor editor = prefs1.edit();
                        editor.putBoolean("FirstTime", true);
                        editor.putString("choose_language", "English");
                        editor.apply();

                        // [START subscribe_topics to English]
                        FirebaseMessaging.getInstance().subscribeToTopic("english");
                        // [END subscribe_topics to English]
                        // Log and toast
                        DevicePreference.getInstance().setLanguage("English");
                        String msg = getString(R.string.eng_subscribed);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        showAlertDialog(MainActivity.this, "No Internet Connection",
                                "You don't have internet connection.", false);
                    }
                }
            });

            builder.setNegativeButton("اردو", new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                    if (isGoogelPlayInstalled() && InternetConnection.checkConnection(getApplicationContext()))
                    {

                        SharedPreferences.Editor editor = prefs1.edit();
                        editor.putBoolean("FirstTime", true);
                        editor.putString("choose_language", "Urdu");
                        editor.apply();

                        // yahan acount k permission wala kam tha


                        // [START subscribe_topics to Urdu]
                        FirebaseMessaging.getInstance().subscribeToTopic("urdu");
                        // [END subscribe_topics to Urdu]

                        // Log and toast
                        DevicePreference.getInstance().setLanguage("Urdu");
                        String msg = getString(R.string.urdu_subscribed);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), MainActivityUrdu.class);
                        finish();
                        startActivity(i);
                    }
                    else if (InternetConnection.checkConnection(getApplicationContext()))
                    {

                        SharedPreferences.Editor editor = prefs1.edit();
                        editor.putBoolean("FirstTime", true);
                        editor.putString("choose_language", "Urdu");
                        editor.apply();

                        // [START subscribe_topics to Urdu]
                        FirebaseMessaging.getInstance().subscribeToTopic("urdu");
                        // [END subscribe_topics to Urdu]

                        // Log and toast
                        DevicePreference.getInstance().setLanguage("Urdu");
                        String msg = getString(R.string.urdu_subscribed);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), MainActivityUrdu.class);
                        finish();
                        startActivity(i);
                    }

                    else
                    {
                        showAlertDialog(MainActivity.this, "No Internet Connection",
                                "You don't have internet connection.", false);
                    }
                    //more code....
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        viewPager.setOffscreenPageLimit(ITEMS_COUNT - 1);
        setupViewPager(viewPager);
        setupViewPagerListner(viewPager);

        setBackgroundPushNotify();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());

                //For updating menus at runtime
                invalidateOptionsMenu();
                //Ends here for updating menus

                if (tab.getPosition() == 1)
                {
//                    frameticker.setVisibility(View.VISIBLE);
                    custom_view.setVisibility(View.VISIBLE);
                }
                else
                {
//                    frameticker.setVisibility(View.GONE);
                    custom_view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
//                if(tab.getPosition() == 11){
//                    MoreFeatures.moreListLay.setVisibility(View.VISIBLE);
//                }
            }
        });

        //For setting red color for first tab !

//        final ViewGroup root = (ViewGroup) tabLayout.getChildAt(0);
//        final View tab = root.getChildAt(0);
//        tab.setBackgroundColor(getResources().getColor(R.color.heading2_black));
        RvNavItems = findViewById(R.id.RvNavItems);
        RvNavItems.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        navList = new ArrayList<>();
        nav_adapter = new Nav_Adapter(this);
        nav_adapter.setListener(this);
        navList.add(new Nav_Model(getResources().getString(R.string.Live), false));
        navList.add(new Nav_Model(getResources().getString(R.string.Home), true));
        //navList.add(new Nav_Model(getResources().getString(R.string.MyNews), false));
        navList.add(new Nav_Model(getResources().getString(R.string.Pakistan), false));
        navList.add(new Nav_Model(getResources().getString(R.string.Global), false));
        navList.add(new Nav_Model(getResources().getString(R.string.Economy), false));
        navList.add(new Nav_Model(getResources().getString(R.string.Sports), false));
        navList.add(new Nav_Model(getResources().getString(R.string.Entertainment), false));
        navList.add(new Nav_Model(getResources().getString(R.string.Editor), false));
        navList.add(new Nav_Model(getResources().getString(R.string.TVShows), false));
        navList.add(new Nav_Model(getResources().getString(R.string.Blogs), false));
        navList.add(new Nav_Model(getResources().getString(R.string.More), false));
        navList.add(new Nav_Model("اردو", false));
        navList.add(new Nav_Model(getResources().getString(R.string.ShareApp), false));
        nav_adapter.setList(navList);
        RvNavItems.setAdapter(nav_adapter);

//        tabLayout
        Utils.setCustomColorsToTabLayout(this, tabLayout, true);
    }


    private void checkScreenAndSetLayout()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5)
        {
            // 6.5inch device or bigger
            setContentView(R.layout.activity_main_large);
        }
        else
        {
            // smaller device
            setContentView(R.layout.activity_main);
        }
    }

    private void setupViewPagerListner(final ViewPager viewPager)
    {

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener()
                {
                    @Override
                    public void onPageSelected(int position)
                    {
                        // When swiping between pages, select the
                        // corresponding tab.
                        if (position < 11) {
                            nav_adapter.SelectOneRow(position);
                            viewPager.setCurrentItem(position);
                        } else if (position == 11) {
                            Intent i = new Intent(getApplicationContext(), MainActivityUrdu.class);
                            finish();
                            startActivity(i);
                        } else if (position == 12) {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share SAMAA TV App");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://appgallery.cloud.huawei.com/ag/n/app/C100082099?locale=en_GB&source=appshare&subsource=C100082099");
                            startActivity(shareIntent.createChooser(shareIntent, "Sharing Via"));
                        }

                    }
                });
    }

    private void init()
    {
        custom_view = (View) findViewById(R.id.customview);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        frameticker = (LinearLayout) findViewById(R.id.linearlayout2);
//        ticker_head = (TextView) findViewById(R.id.ticker);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
    }

    private void setBackgroundPushNotify()
    {
        message = getIntent().getStringExtra("message");
        newsID = getIntent().getStringExtra("news_url");
        newsCat = getIntent().getStringExtra("news_cat");

        if (newsCat == null)
        {

        }
        else
        {
            String news_category = newsCat.toLowerCase();
            selectionOfTabs(news_category, newsID);
        }
    }

    private void selectionOfTabs(String newsCat, String newsID)
    {

        SamaaAppAnalytics.getInstance().trackScreenView("From Push Notification English");
        SamaaFirebaseAnalytics.syncSamaaAnalytics(this, "From Push Notification English");

        if (newsCat.contains("live_streaming") || newsID.contains("live_streaming"))
        {
            DevicePreference.getInstance().setFromNotif(true);

//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout.setScrollPosition(0, 0f, true);
            viewPager.setCurrentItem(0);
        }

        if (newsCat.contains("international") || newsCat.contains("global"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("globnews", this.newsID);
            WorldNews globFrag = new WorldNews();
            globFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout.setScrollPosition(3, 0f, true);
            viewPager.setCurrentItem(3);
        }

        if (newsCat.contains("pakistan"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("paknews", this.newsID);
            PakistanNews pakFrag = new PakistanNews();
            pakFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout.setScrollPosition(2, 0f, true);
            viewPager.setCurrentItem(2);
        }

        if (newsCat.contains("sports"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("sportnews", this.newsID);
            SportsNews sportsFrag = new SportsNews();
            sportsFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout.setScrollPosition(5, 0f, true);
            viewPager.setCurrentItem(5);
        }

        if (newsCat.contains("entertainment"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("enternews", this.newsID);
            EntertainmentNews enterFrag = new EntertainmentNews();
            enterFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout.setScrollPosition(6, 0f, true);
            viewPager.setCurrentItem(6);
        }

        if (newsCat.contains("economy"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("econnews", this.newsID);
            BusinessNews businessFrag = new BusinessNews();
            businessFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout.setScrollPosition(4, 0f, true);
            viewPager.setCurrentItem(4);
        }

        if (newsCat.contains("editor-s-choice") || newsCat.contains("editors choice"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("editnews", this.newsID);
            EditorsChoice editFrag = new EditorsChoice();
            editFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout.setScrollPosition(7, 0f, true);
            viewPager.setCurrentItem(7);
        }

//        if(newsCat.contains("health")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("HealthValue",1);
//            sendHealth.putString("healthnews", this.newsID);
//            MoreFeatures moreFragment = new MoreFeatures();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout.setScrollPosition(11,0f,true);
//            viewPager.setCurrentItem(11);
//        }
//
//        if(newsCat.contains("lifestyle") || newsCat.contains("life-style")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("LifeValue",2);
//            sendHealth.putString("lifenews", this.newsID);
//            MoreFeatures moreFragment = new MoreFeatures();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout.setScrollPosition(11,0f,true);
//            viewPager.setCurrentItem(11);
//        }
//
//
//        if(newsCat.contains("social") || newsCat.contains("social-buzz")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("SocialValue",3);
//            sendHealth.putString("socialnews", this.newsID);
//            MoreFeatures moreFragment = new MoreFeatures();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout.setScrollPosition(11,0f,true);
//            viewPager.setCurrentItem(11);
//        }
//
//
//        if(newsCat.contains("technology") || newsCat.contains("scitech")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("SciValue",4);
//            sendHealth.putString("technews", this.newsID);
//            MoreFeatures moreFragment = new MoreFeatures();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout.setScrollPosition(11,0f,true);
//            viewPager.setCurrentItem(11);
//        }
//
//        if(newsCat.contains("weird")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("WeirdValue",5);
//            sendHealth.putString("weirdnews", this.newsID);
//            MoreFeatures moreFragment = new MoreFeatures();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout.setScrollPosition(11,0f,true);
//            viewPager.setCurrentItem(11);
//        }
    }

    /**
     * This sample has a single Activity, so we need to manually record "screen views" as
     * we change fragments.
     */
    private void recordScreenView()
    {
        // This string must be <= 36 characters long in order for setCurrentScreen to succeed.
        String screenName = "English Main Screen";

        // [START set_current_screen]
        mFirebaseAnalytics.setCurrentScreen(this, screenName, null /* class override */);
        // [END set_current_screen]
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

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


        // Setting Retry Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "Retry", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(in);

                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }
    //end for user's getting email function

    public void switchContent(int id, Fragment fragment)
    {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        //  getFragmentManager().popBackStackImmediate();
        ft.addToBackStack(null);
        // ft.commitAllowingStateLoss();
        ft.commit();
    }

    // yahan acount k permission wala kam tha


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean isGoogelPlayInstalled()
    {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS)
//        {
//            if (apiAvailability.isUserResolvableError(resultCode))
//            {
//                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
//                        .show();
//            }
//            else
//            {
//                Log.i(TAG, "This device is not supported.");
//                Toast.makeText(getApplicationContext(),
//                        "Google Play Service is not installed",
//                        Toast.LENGTH_SHORT).show();
//                finish();
//            }
//            return false;
//        }
        return true;
    }


    private void setupViewPager(ViewPager viewPager)
    {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new JWPlayer_Fragment(), "Live");
        // adapter.addFrag(new WatchLiveEnglish_web(),"Live");
        adapter.addFrag(new Trending_New(), "Home");
       // adapter.addFrag(new ByCategoryNews(), "My News");
        adapter.addFrag(new PakistanNews(), "Pakistan");
        adapter.addFrag(new WorldNews(), "Global");
        adapter.addFrag(new BusinessNews(), "Economy");
        adapter.addFrag(new SportsNews(), "Sports");
        adapter.addFrag(new EntertainmentNews(), "Culture");
        adapter.addFrag(new EditorsChoice(), "Video");
        adapter.addFrag(new TvShows(), "Programs");
        adapter.addFrag(new BlogFeatures(), "Opinions");
//        adapter.addFrag(new MoreFeatures(),"More");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (count == 1)
        {
            count = 0;
            this.finish();
//            GoogleAnalytics.getInstance(this).reportActivityStop(this);
            System.exit(0);
            moveTaskToBack(true);
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.exit_english), Toast.LENGTH_LONG).show();
            count++;
        }

//            return;

        int presFrag = viewPager.getCurrentItem();

        if (presFrag == 0)
        {
            showInterstitial();
        }

        if (presFrag == 8)
        {
            showInterstitial();
        }

        if (presFrag == 9)
        {
            showInterstitial();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // TODO Auto-generated method stub
        menu.clear();
// for my news menu
//        if (viewPager.getCurrentItem() == 2)
//        {
//            getMenuInflater().inflate(R.menu.mynews_edit, menu);
//            return true;
//        }
//        else
//        {
            getMenuInflater().inflate(R.menu.main, menu);
     //   }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_urdu)
        {
            Intent i = new Intent(getApplicationContext(), MainActivityUrdu.class);
            finish();
            startActivity(i);
            return true;
        }
        else if (id == R.id.edit_news)
        {
            //Open Add News Topics Activity
            Intent intent = new Intent(getApplicationContext(), Category_Add.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item)
//    {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//
//        if (id == R.id.nav_live)
//        {
//            viewPager.setCurrentItem(0);
//
//        }
//        else if (id == R.id.nav_trend)
//        {
//            viewPager.setCurrentItem(1);
//
//        }
//        else if (id == R.id.nav_mynews)
//        {
//            viewPager.setCurrentItem(2);
//
//        }
//        else if (id == R.id.nav_pak)
//        {
//            viewPager.setCurrentItem(3);
//
//        }
//        else if (id == R.id.nav_glo)
//        {
//            viewPager.setCurrentItem(4);
//
//        }
//        else if (id == R.id.nav_eco)
//        {
//            viewPager.setCurrentItem(5);
//
//        }
//        else if (id == R.id.nav_sport)
//        {
//            viewPager.setCurrentItem(6);
//
//        }
//        else if (id == R.id.nav_enter)
//        {
//            viewPager.setCurrentItem(7);
//
//        }
//        else if (id == R.id.nav_editor)
//        {
//            viewPager.setCurrentItem(8);
//
//        }
//        else if (id == R.id.nav_tvshows)
//        {
//            viewPager.setCurrentItem(9);
//        }
//        else if (id == R.id.nav_blog)
//        {
//            viewPager.setCurrentItem(10);
//        }
////        else if (id == R.id.nav_more) {
////            viewPager.setCurrentItem(11);
////        }
//        else if (id == R.id.nav_setting)
//        {
//            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
//            startActivity(intent);
//
//        }
//        else if (id == R.id.nav_share)
//        {
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("text/plain");
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share SAMAA TV App");
//            shareIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=com.Samaatv.samaaapp3");
//            startActivity(shareIntent.createChooser(shareIntent, "Sharing Via"));
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    public void onPause()
    {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onClick(int position) {
        Log.d("TAG", "position: " + position);
         if (position == 9) {
            nav_adapter.SelectOneRow(9);
            viewPager.setCurrentItem(9);
        }
        else if (position == 8) {
            nav_adapter.SelectOneRow(8);
            viewPager.setCurrentItem(8);
        }
        else if (position == 7) {
            nav_adapter.SelectOneRow(7);
            viewPager.setCurrentItem(7);
        }
        else if (position == 6) {
            nav_adapter.SelectOneRow(6);
            viewPager.setCurrentItem(6);
        }
        else if (position == 5) {
            nav_adapter.SelectOneRow(5);
            viewPager.setCurrentItem(5);
        }
        else if (position == 4) {
            nav_adapter.SelectOneRow(4);
            viewPager.setCurrentItem(4);
        }
        else if (position == 3) {
            nav_adapter.SelectOneRow(3);
            viewPager.setCurrentItem(3);
        }
        else if (position == 2) {
            nav_adapter.SelectOneRow(2);
            viewPager.setCurrentItem(2);
        }
        else if (position == 1) {
            nav_adapter.SelectOneRow(1);
            viewPager.setCurrentItem(1);
        }
        else if (position == 0) {
            nav_adapter.SelectOneRow(0);
            viewPager.setCurrentItem(0);
        }
        else if (position == 11) {
             Intent i = new Intent(getApplicationContext(), MainActivityUrdu.class);
             finish();
             startActivity(i);
        }
        else if (position == 12) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share SAMAA TV App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://appgallery.cloud.huawei.com/ag/n/app/C100082099?locale=en_GB&source=appshare&subsource=C100082099");
            startActivity(shareIntent.createChooser(shareIntent, "Sharing Via"));
        }

        drawer.closeDrawer(GravityCompat.START);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }

}
