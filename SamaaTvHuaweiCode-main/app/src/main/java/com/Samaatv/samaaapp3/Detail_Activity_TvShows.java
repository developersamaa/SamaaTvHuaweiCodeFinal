package com.Samaatv.samaaapp3;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.Samaatv.samaaapp3.adapter.TVShowsAdapterEpisodeListing;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import me.relex.circleindicator.CircleIndicator;


public class Detail_Activity_TvShows extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    static final boolean GRID_LAYOUT = false;
    static PublisherInterstitialAd mPublisherInterstitialAd;
    static FrameLayout frame_footer;
    SwipeRefreshLayout swipeLayout;
    RecyclerView.LayoutManager layoutManager;
    JSONArray programsList = null;
    String feedURL;
    String url;
    int pos;
    PublisherAdView mAdView;
    PublisherAdRequest adRequest;
    RecyclerView tvepisode_recycle;
    private ArrayList<Contact> detail_array;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String TAG = TVShowListing_Activity.class.getSimpleName();
    //    private TVShows_EpisodeListing_Adapter tvepisode_adapter;
    private TVShowsAdapterEpisodeListing tvepisode_adapter;
    private ArrayList<Contact> tvepisode_list;
    private View parentView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recyclerview);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar_layoutcolor)));


        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        detail_array = (ArrayList<Contact>) bundle.getSerializable("array");
        pos = bundle.getInt("pos");
        url = bundle.getString("url");

        // Inflate the layout for this fragment
        tvepisode_recycle = (RecyclerView) findViewById(R.id.recyclerView);
        mAdView = (PublisherAdView) findViewById(R.id.ad_view_tv);
        adRequest = new PublisherAdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
        tvepisode_recycle.setLayoutManager(layoutManager);
        tvepisode_recycle.setHasFixedSize(true);

        if (InternetConnection.checkConnection(this))
        {
            // webView.loadUrl(URL);
            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(this);
            dialog.setTitle("SAMAA TV News");
            dialog.setMessage("Loading Program Episodes...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            //Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
            Call<NewsCategoryList> call = api.getTVShowsEpisodeURL(url);

            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {


                    if (response.isSuccessful())
                    {
                        /**
                         * Got Successfully
                         */
                        tvepisode_list = response.body().getTVShowsEpisode();

                        if (tvepisode_list != null)
                        {

                            tvepisode_adapter = new TVShowsAdapterEpisodeListing(getApplicationContext(), tvepisode_list);
                            tvepisode_recycle.setAdapter(tvepisode_adapter);
                            if (dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                        }
                        else
                        {
                            tvepisode_list = new ArrayList<Contact>();
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<NewsCategoryList> call, Throwable t)
                {
                    if (dialog.isShowing())
                    {
                        dialog.dismiss();
                    }
                    if (t instanceof NullPointerException)
                    {
                        Toast.makeText(Detail_Activity_TvShows.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError)
                    {
                        Toast.makeText(Detail_Activity_TvShows.this, "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException)
                    {
                        Toast.makeText(Detail_Activity_TvShows.this, "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException)
                    {
                        Toast.makeText(Detail_Activity_TvShows.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "No internet connection !", Toast.LENGTH_SHORT).show();
        }


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
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {

        finish();
    }

    @Override
    public void onRefresh()
    {
        NewsRefresh();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mAdView != null)
        {
            mAdView.resume();
        }
    }

    @Override
    public void onPause()
    {
        // Pause the PublisherAdView.
        if (mAdView != null)
        {
            mAdView.pause();
        }
        super.onPause();
    }


    @Override
    public void onDestroy()
    {
        // Destroy the PublisherAdView.
        if (mAdView != null)
        {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    public void NewsRefresh()
    {

        if (tvepisode_list != null)
        {

            tvepisode_adapter = new TVShowsAdapterEpisodeListing(getApplicationContext(), tvepisode_list);
            tvepisode_recycle.setAdapter(tvepisode_adapter);
            swipeLayout.setRefreshing(false);
        }
        else
        {
            tvepisode_list = new ArrayList<Contact>();
        }
    }


}



