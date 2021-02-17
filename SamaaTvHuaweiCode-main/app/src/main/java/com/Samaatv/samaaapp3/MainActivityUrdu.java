package com.Samaatv.samaaapp3;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.Samaatv.samaaapp3.adapter.Nav_Adapter_Urdu;
import com.Samaatv.samaaapp3.interfaces.OnClick;
import com.Samaatv.samaaapp3.model.Nav_Model;
import com.Samaatv.samaaapp3.utils.BackgroundTaskUrduNew;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Samaatv.samaaapp3.activities.BaseActivityUrdu;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_home_fragment_urdu;
import com.Samaatv.samaaapp3.fragments.ByCategoryNewsUrdu;
import com.Samaatv.samaaapp3.fragments.JWPlayer_Fragment_Urdu;
import com.Samaatv.samaaapp3.utils.BackgroundTaskUrdu;
import com.Samaatv.samaaapp3.utils.DevicePreference;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.ads.conversiontracking.AdWordsConversionReporter;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
//import com.google.android.gms.analytics.GoogleAnalytics;

import java.util.ArrayList;
import java.util.List;

public class MainActivityUrdu extends BaseActivityUrdu implements NavigationView.OnNavigationItemSelectedListener, OnClick
{
    //SwipeRefreshLayout swipeLayout;
    public static TabLayout tabLayout1;
    public static ViewPager viewPager;
    MediaPlayer mp;
    int pos;
    View custom_view;
    int count = 0;
    //    public static TextView ticker_head;
//    public static LinearLayout frameticker;
    int ITEMS_COUNT = 11;
    private String message;
    private String newsID;
    private String newsCat;
    private RecyclerView RvNavItems;
    private List<Nav_Model> navList;
    private Nav_Adapter_Urdu nav_adapter;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        checkScreenAndSetLayout();

        Calls_home_fragment_urdu.callHomeList(getApplicationContext());
        if (InternetConnection.checkConnection(getApplicationContext()))
        {
            BackgroundTaskUrduNew.callbackgroundTasks(getApplicationContext());
            //new BackgroundTaskUrdu(getApplicationContext()).execute();
        }

        AdWordsConversionReporter.reportWithConversionId(this.getApplicationContext(),
                "976003944", "EqPwCJa2vmQQ6May0QM", "1.00", false);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init();

        loadInterstitialUrdu();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager.setOffscreenPageLimit(ITEMS_COUNT - 1);
        setupViewPager(viewPager);
        setupViewPagerListner(viewPager);

        setBackgroundPushNotify();

        tabLayout1.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));
        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());

                //For updating menus at runtime
                invalidateOptionsMenu();
                //Ends here for updating menus

                if (tab.getPosition() == 9)
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
//                if(tab.getPosition() == 0){
//                    MoreFeaturesUrdu.moreListLay.setVisibility(View.VISIBLE);
//                }
            }
        });

        RvNavItems = findViewById(R.id.RvNavItems);
        RvNavItems.setLayoutManager(new LinearLayoutManager(MainActivityUrdu.this));
        navList = new ArrayList<>();
        nav_adapter = new Nav_Adapter_Urdu(this);
        nav_adapter.setListener(this);
        navList.add(new Nav_Model(getResources().getString(R.string.LiveUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.HomeUrdu), true));
//        navList.add(new Nav_Model(getResources().getString(R.string.MyNewsUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.PakistanUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.GlobalUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.EconomyUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.SportsUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.EntertainmentUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.EditorUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.TVShowsUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.BlogsUrdu), false));
        navList.add(new Nav_Model(getResources().getString(R.string.MoreUrdu), false));
        navList.add(new Nav_Model("English", false));
        navList.add(new Nav_Model(getResources().getString(R.string.ShareAppUrdu), false));
        nav_adapter.setList(navList);
        RvNavItems.setAdapter(nav_adapter);


        Utils.setCustomColorsToTabLayout(this, tabLayout1, false);


        //For setting red color for first tab !

//        final ViewGroup root = (ViewGroup) tabLayout1.getChildAt(0);
//        final View tab = root.getChildAt(11);
//        tab.setBackgroundColor(getResources().getColor(R.color.heading2_black));


    }

    private void init()
    {
        custom_view = (View) findViewById(R.id.customview);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout1 = (TabLayout) findViewById(R.id.tabs);
        tabLayout1.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
//        frameticker = (LinearLayout) findViewById(R.id.linearlayout2);
//        ticker_head = (TextView) findViewById(R.id.ticker);
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
            setContentView(R.layout.urdu_main_large);
        }
        else
        {
            // smaller device
            setContentView(R.layout.urdu_main);
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
                        Log.d("TAG", "position: " + position);
                        if (position == 9) {
                            nav_adapter.SelectOneRow(0);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 8) {
                            nav_adapter.SelectOneRow(1);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 7) {
                            nav_adapter.SelectOneRow(2);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 6) {
                            nav_adapter.SelectOneRow(3);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 5) {
                            nav_adapter.SelectOneRow(4);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 4) {
                            nav_adapter.SelectOneRow(5);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 3) {
                            nav_adapter.SelectOneRow(6);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 2) {
                            nav_adapter.SelectOneRow(7);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 1) {
                            nav_adapter.SelectOneRow(8);
                            viewPager.setCurrentItem(position);
                        }
                        else if (position == 0) {
                            nav_adapter.SelectOneRow(9);
                            viewPager.setCurrentItem(position);
                        }

                    }
                });
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

        SamaaAppAnalytics.getInstance().trackScreenView("From Push Notification Urdu");
        SamaaFirebaseAnalytics.syncSamaaAnalytics(this, "From Push Notification Urdu");

        if (newsCat.contains("live_streaming") || newsID.contains("live_streaming"))
        {
            DevicePreference.getInstance().setFromNotif(true);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout1.setScrollPosition(11, 0f, true);
            viewPager.setCurrentItem(11);
        }

        if (newsCat.contains("international") || newsCat.contains("global"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("globnews", this.newsID);
            WorldNewsUrdu globFrag = new WorldNewsUrdu();
            globFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout1.setScrollPosition(7, 0f, true);
            viewPager.setCurrentItem(7);
        }

        if (newsCat.contains("pakistan"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("paknews", this.newsID);
            PakistanNewsUrdu pakFrag = new PakistanNewsUrdu();
            pakFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout1.setScrollPosition(8, 0f, true);
            viewPager.setCurrentItem(8);
        }

        if (newsCat.contains("sports"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("sportnews", this.newsID);
            SportsNewsUrdu sportsFrag = new SportsNewsUrdu();
            sportsFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout1.setScrollPosition(5, 0f, true);
            viewPager.setCurrentItem(5);
        }

        if (newsCat.contains("entertainment"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("enternews", this.newsID);
            EntertainmentNewsUrdu enterFrag = new EntertainmentNewsUrdu();
            enterFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout1.setScrollPosition(4, 0f, true);
            viewPager.setCurrentItem(4);
        }

        if (newsCat.contains("economy"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("econnews", this.newsID);
            BusinessNewsUrdu businessFrag = new BusinessNewsUrdu();
            businessFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout1.setScrollPosition(6, 0f, true);
            viewPager.setCurrentItem(6);
        }

        if (newsCat.contains("editor-s-choice") || newsCat.contains("editors choice"))
        {
            DevicePreference.getInstance().setFromNotif(true);
            Bundle send = new Bundle();
            send.putString("editnews", this.newsID);
            EditorsChoiceUrdu editFrag = new EditorsChoiceUrdu();
            editFrag.setArguments(send);
//            frameticker.setVisibility(View.GONE);
            custom_view.setVisibility(View.GONE);
            tabLayout1.setScrollPosition(3, 0f, true);
            viewPager.setCurrentItem(3);
        }

//        if(newsCat.contains("health")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("HealthValue",1);
//            sendHealth.putString("healthnews", this.newsID);
//            MoreFeaturesUrdu moreFragment = new MoreFeaturesUrdu();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout1.setScrollPosition(0,0f,true);
//            viewPager.setCurrentItem(0);
//        }
//
//        if(newsCat.contains("lifestyle") || newsCat.contains("life-style")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("LifeValue",2);
//            sendHealth.putString("lifenews", this.newsID);
//            MoreFeaturesUrdu moreFragment = new MoreFeaturesUrdu();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout1.setScrollPosition(0,0f,true);
//            viewPager.setCurrentItem(0);
//        }
//
//
//        if(newsCat.contains("social") || newsCat.contains("social-buzz")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("SocialValue",3);
//            sendHealth.putString("socialnews", this.newsID);
//            MoreFeaturesUrdu moreFragment = new MoreFeaturesUrdu();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout1.setScrollPosition(0,0f,true);
//            viewPager.setCurrentItem(0);
//        }
//
//
//        if(newsCat.contains("technology") || newsCat.contains("scitech")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("SciValue",4);
//            sendHealth.putString("technews", this.newsID);
//            MoreFeaturesUrdu moreFragment = new MoreFeaturesUrdu();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout1.setScrollPosition(0,0f,true);
//            viewPager.setCurrentItem(0);
//        }
//
//        if(newsCat.contains("weird")){
//            DevicePreference.getInstance().setFromNotif(true);
//            Bundle sendHealth = new Bundle();
//            sendHealth.putInt("WeirdValue",5);
//            sendHealth.putString("weirdnews", this.newsID);
//            MoreFeaturesUrdu moreFragment = new MoreFeaturesUrdu();
//            moreFragment.setArguments(sendHealth);
//            mAdView.setVisibility(View.GONE);
////            frameticker.setVisibility(View.GONE);
//            custom_view.setVisibility(View.GONE);
//            tabLayout1.setScrollPosition(0,0f,true);
//            viewPager.setCurrentItem(0);
//        }

    }

    public void switchContent(int id, Fragment fragment)
    {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        //  getFragmentManager().popBackStackImmediate();
        ft.addToBackStack(null);
        // ft.commitAllowingStateLoss();
        ft.commit();
    }


    private void setupViewPager(ViewPager viewPager)
    {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

//        adapter.addFrag(new MoreFeaturesUrdu(),"مزيد");
        adapter.addFrag(new BlogFeaturesUrdu(), "تجزیۂ");
        adapter.addFrag(new TvShows(), "ٹی وی شوز");
        adapter.addFrag(new EditorsChoiceUrdu(), "ويڈيو");
        adapter.addFrag(new EntertainmentNewsUrdu(), "اینٹرٹینمنٹ نیوز");
        adapter.addFrag(new SportsNewsUrdu(), "کھیل نیوز");
        adapter.addFrag(new BusinessNewsUrdu(), "بزنس نیوز");
        adapter.addFrag(new WorldNewsUrdu(), "ورلڈ نیوز");
        adapter.addFrag(new PakistanNewsUrdu(), "پاکستان");
//        adapter.addFrag(new ByCategoryNewsUrdu(), "میری خبریں");
        adapter.addFrag(new Trending_NewUrdu(), "ہوم");
        adapter.addFrag(new JWPlayer_Fragment_Urdu(), "لائیو دیکھیں");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(8);

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
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.exit_urdu), Toast.LENGTH_LONG).show();
            count++;
        }

//        return;

        int presFrag = viewPager.getCurrentItem();

        if (presFrag == 10)
        {
//            Intent intent = new Intent(this, AdsActivityUrdu.class);
//            intent.putExtra("liveFrag","live");
//            startActivity(intent);
            showInterstitialUrdu();
        }

        if (presFrag == 2)
        {
//            Intent intent = new Intent(this, AdsActivityUrdu.class);
//            intent.putExtra("editFrag","edit");
//            startActivity(intent);
            showInterstitialUrdu();
        }

        if (presFrag == 1)
        {
//            Intent intent = new Intent(this, AdsActivityUrdu.class);
//            intent.putExtra("tvFrag","tv");
//            startActivity(intent);
            showInterstitialUrdu();
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // TODO Auto-generated method stub
        menu.clear();
        //MenuInflater inflater = getMenuInflater();
        // int currentTab = tabHost.getCurrentTab();
        //Toast.makeText(this, "Pos" + pos, Toast.LENGTH_LONG).show();

//        if (viewPager.getCurrentItem() == 8)
//        {
//            getMenuInflater().inflate(R.menu.mynews_edit, menu);
//            return true;
//        }
//        else
//        {
            getMenuInflater().inflate(R.menu.main, menu);
       // }
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
        if (id == R.id.action_settings_english)
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(i);
            return true;
        }
        else if (id == R.id.edit_news)
        {
            //Open Add News Topics Activity
            Intent intent = new Intent(getApplicationContext(), Category_Add_Urdu.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_live)
        {
            viewPager.setCurrentItem(9);

        }
        else if (id == R.id.nav_trend)
        {
            viewPager.setCurrentItem(8);
        }
//        else if (id == R.id.nav_mynews)
//        {
//            viewPager.setCurrentItem(8);
//        }
        else if (id == R.id.nav_pak)
        {
            viewPager.setCurrentItem(7);

        }
        else if (id == R.id.nav_glo)
        {
            viewPager.setCurrentItem(6);

        }
        else if (id == R.id.nav_eco)
        {
            viewPager.setCurrentItem(5);

        }
        else if (id == R.id.nav_sport)
        {
            viewPager.setCurrentItem(4);

        }
        else if (id == R.id.nav_enter)
        {
            viewPager.setCurrentItem(3);

        }
        else if (id == R.id.nav_editor)
        {
            viewPager.setCurrentItem(2);

        }
        else if (id == R.id.nav_tvshows)
        {
            viewPager.setCurrentItem(1);
        }
        else if (id == R.id.nav_blog)
        {
            viewPager.setCurrentItem(0);
        }
//        else if (id == R.id.nav_more) {
//            viewPager.setCurrentItem(0);
//        }
        else if (id == R.id.nav_setting)
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(i);

        }
        else if (id == R.id.nav_share)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share SAMAA TV App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://appgallery.cloud.huawei.com/ag/n/app/C100082099?locale=en_GB&source=appshare&subsource=C100082099");
            startActivity(shareIntent.createChooser(shareIntent, "Sharing Via"));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        // Pause the PublisherAdView.
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        // Destroy the PublisherAdView.
        super.onDestroy();
    }

    @Override
    public void onClick(int position) {
//        if (position < 12) {
//            nav_adapter.SelectOneRow(position);
//            viewPager.setCurrentItem(position);
//        } else if (position == 12) {
//            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
//            startActivity(intent);
//        } else if (position == 13) {
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("text/plain");
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share SAMAA TV App");
//            shareIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=com.Samaatv.samaaapp3");
//            startActivity(shareIntent.createChooser(shareIntent, "Sharing Via"));
//        }

        Log.d("TAG", "position: " + position);
        if (position == 9) {
            nav_adapter.SelectOneRow(9);
            viewPager.setCurrentItem(0);
        }
        else if (position == 8) {
            nav_adapter.SelectOneRow(8);
            viewPager.setCurrentItem(1);
        }
        else if (position == 7) {
            nav_adapter.SelectOneRow(7);
            viewPager.setCurrentItem(2);
        }
        else if (position == 6) {
            nav_adapter.SelectOneRow(6);
            viewPager.setCurrentItem(3);
        }
        else if (position == 5) {
            nav_adapter.SelectOneRow(5);
            viewPager.setCurrentItem(4);
        }
        else if (position == 4) {
            nav_adapter.SelectOneRow(4);
            viewPager.setCurrentItem(5);
        }
        else if (position == 3) {
            nav_adapter.SelectOneRow(3);
            viewPager.setCurrentItem(6);
        }
        else if (position == 2) {
            nav_adapter.SelectOneRow(2);
            viewPager.setCurrentItem(7);
        }
        else if (position == 1) {
            nav_adapter.SelectOneRow(1);
            viewPager.setCurrentItem(8);
        }
        else if (position == 0) {
            nav_adapter.SelectOneRow(0);
            viewPager.setCurrentItem(9);
        }
        else if (position == 11) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
