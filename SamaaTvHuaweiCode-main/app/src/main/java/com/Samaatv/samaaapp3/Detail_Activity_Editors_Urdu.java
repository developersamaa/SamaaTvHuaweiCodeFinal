package com.Samaatv.samaaapp3;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.json.JSONArray;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

//import me.relex.circleindicator.CircleIndicator;


public class Detail_Activity_Editors_Urdu extends AppCompatActivity
{
    static PublisherInterstitialAd mPublisherInterstitialAd;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public ViewPager mPager;
    JSONArray array;
    String jsonArray;
    int pos;
    String url;
    String cate;
    int sendingPos;
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    // private static final int NUM_PAGES = 5;
    private ArrayList<Contact> detail_array;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar_layoutcolor)));

        // Create the InterstitialAd and set the adUnitId.
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        // Defined in res/values/strings.xml
        mPublisherInterstitialAd.setAdUnitId(getString(R.string.interstitial_unit_id_urd));

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        detail_array = (ArrayList<Contact>) bundle.getSerializable("array");
        pos = bundle.getInt("pos");
        url = bundle.getString("url");

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        // CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(pos);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

                if ((mPager.getCurrentItem() % 4) == 0 && mPublisherInterstitialAd != null && mPublisherInterstitialAd.isLoaded())

                {
                    mPublisherInterstitialAd.show();
                }
                sendingPos = position;
            }

            @Override
            public void onPageSelected(int position)
            {
                Log.d("test", "position = " + position);
                pos = position;
                Log.d("test2", "position2 = " + pos);
                //  pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        //  indicator.setViewPager(mPager);
        //  mPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.share_news:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Please Visit:");
                String title;
                String link;
                title = detail_array.get(sendingPos).getTitle();
                link = detail_array.get(sendingPos).getLink();
                shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + link);
                startActivity(shareIntent.createChooser(shareIntent, "Sharing Via"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (JCVideoPlayer.backPress())
        {
            return;
        }
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }


        @Override
        public Fragment getItem(int position)
        {

            DetailFragmentEditorsUrdu frag = new DetailFragmentEditorsUrdu();
            //pos = position;
            Bundle bundle = new Bundle();
            bundle.putSerializable("array", detail_array);
            bundle.putInt("pos", position);
            bundle.putString("url", url);
            //  bundle.putString("category", cate);

            //frag.set(position, fragment);
            //bundle.putString(KEY_VIDEO, videourl);
            frag.setArguments(bundle);

            return frag;

        }

        @Override
        public int getCount()
        {
            return detail_array.size();
        }
    }
}