package com.Samaatv.samaaapp3.activities;

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
import android.view.Menu;
import android.view.MenuItem;

import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.fragments.MoreBlogDetailFragment;
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class DetailActivity_MoreBlogs extends AppCompatActivity
{

    static PublisherInterstitialAd mPublisherInterstitialAd;
    public ViewPager mPager;
    String jsonArray;
    String array_detail;
    int pos;
    String cate;
    int sendingPos;
    private ArrayList<Contact> detail_array;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity__more_blogs);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar_layoutcolor)));

        // Create the InterstitialAd and set the adUnitId.
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId(getString(R.string.interstitial_unit_id_eng));

        //mz
//        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
//        mPublisherInterstitialAd.loadAd(publisherAdRequestBuilder.build());

        Bundle bundle = getIntent().getExtras();
        detail_array = (ArrayList<Contact>) bundle.getSerializable("array");
        pos = bundle.getInt("pos");

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(pos);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                sendingPos = position;
            }

            @Override
            public void onPageSelected(int position)
            {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
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
            // Share News Functionality
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

    private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {

            MoreBlogDetailFragment frag = new MoreBlogDetailFragment();
            //pos = position;
            Bundle bundle = new Bundle();
            bundle.putSerializable("array", detail_array);
            bundle.putInt("pos", position);
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
