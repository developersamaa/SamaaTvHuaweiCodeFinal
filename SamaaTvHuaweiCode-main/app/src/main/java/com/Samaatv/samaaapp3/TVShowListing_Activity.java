package com.Samaatv.samaaapp3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import me.relex.circleindicator.CircleIndicator;


public class TVShowListing_Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    static final boolean GRID_LAYOUT = false;
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    // private static final int NUM_PAGES = 5;

    static PublisherInterstitialAd mPublisherInterstitialAd;
    static FrameLayout frame_footer;
    SwipeRefreshLayout swipeLayout;
    RecyclerView.LayoutManager layoutManager;
    JSONArray programsList = null;
    String feedURL;
    String url;
    int pos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String TAG = TVShowListing_Activity.class.getSimpleName();
    private PublisherAdView mAdView;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_shows_listing_activity);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar_layoutcolor)));


        Intent intent = getIntent();
        if (intent != null)
        {
            url = intent.getStringExtra("url");
            pos = intent.getExtras().getInt("item_selected_key");
        }

        feedURL = url;
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // mAdView = (PublisherAdView) view.findViewById(R.id.ad);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);


        swipeLayout.setColorSchemeColors(R.color.toolbar_layoutcolor,
                R.color.heading1_black,
                R.color.heading2_black,
                R.color.blue);

        frame_footer = (FrameLayout) findViewById(R.id.footer);


        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        }
        else
        {
            layoutManager = new LinearLayoutManager(getApplicationContext());
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        new AsyncLoadXMLFeed().execute();

        //  indicator.setViewPager(mPager);
        //  mPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {

        finish();
        /*if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }*/
    }

    public void onRefresh()
    {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mAdapter = new RecyclerViewAdapterProgramsListingAds(getApplicationContext(), programsList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        }, 5000);
       /* new AsyncLoadXMLFeed().execute();
        swipeLayout.setRefreshing(false);*/
    }

    private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void>
    {

        ProgressDialog pDialog;


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Loading Episodes...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params)
        {

            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(feedURL, ServiceHandler.GET);


            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null)
            {
                try
                {

                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node of TV Shows News
                    programsList = jsonObj.getJSONArray("Program-Episodes");


                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
            else
            {
                Log.e("ServiceHandler", "URL'den veri alınamadı.");
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);


            if (null != pDialog && pDialog.isShowing())
            {
                pDialog.dismiss();

                mAdapter = new RecyclerViewAdapterProgramsListingAds(getApplicationContext(), programsList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

        }

    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

}