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
import android.view.Menu;
import android.view.MenuItem;

import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


public class Detail_Activity extends AppCompatActivity
{

    static PublisherInterstitialAd mPublisherInterstitialAd;
    public ViewPager mPager;
    String jsonArray;
    String array_detail;
    int pos;
    String newsCatTag = null;
    String cate;
    ArrayList<Contact> ticker_array;
    int ticker_index;
    int sendingPos;
    private ArrayList<Contact> detail_array;
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

        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId(getString(R.string.interstitial_unit_id_eng));

        Bundle bundle = getIntent().getExtras();
        detail_array = (ArrayList<Contact>) bundle.getSerializable("array");
        pos = bundle.getInt("pos");
        newsCatTag = bundle.getString("newsCatTag", "");

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

                if ((mPager.getCurrentItem() % 4) == 0 && mPublisherInterstitialAd != null && mPublisherInterstitialAd.isLoaded())
                {
                    mPublisherInterstitialAd.show();
                }
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

            DetailFragment frag = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("array", detail_array);
            bundle.putInt("pos", position);
            bundle.putString("newsCatTag", newsCatTag);
            frag.setArguments(bundle);
            return frag;
        }

        @Override
        public int getCount()
        {

            if (detail_array != null)
            {
                return detail_array.size();
            }
            else
            {
                return 0;
            }
        }
    }

}