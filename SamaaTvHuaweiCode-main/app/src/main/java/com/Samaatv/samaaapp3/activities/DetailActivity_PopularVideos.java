package com.Samaatv.samaaapp3.activities;

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

import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.TVShowListing_Activity;
import com.Samaatv.samaaapp3.adapter.PopularVideosAdapter;
import com.Samaatv.samaaapp3.model.Contact;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.json.JSONArray;

import java.util.ArrayList;

public class DetailActivity_PopularVideos extends AppCompatActivity
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
    ProgressDialog dialog;
    RecyclerView popvideos_recycle;
    private ArrayList<Contact> detail_array;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String TAG = TVShowListing_Activity.class.getSimpleName();
    private PopularVideosAdapter popular_episode_adapter;
    private ArrayList<Contact> tvepisode_list;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etail_activity__popular_videos);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar_layoutcolor)));

        init();
        getDataFromMostWatchedAdapter();
        settingAspectsCallings();
    }


    private void init()
    {
        popvideos_recycle = (RecyclerView) findViewById(R.id.recyclerView_popvideos);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer_popvideos);
        frame_footer = (FrameLayout) findViewById(R.id.footer_popvideos);
    }

    private void settingAspectsCallings()
    {

//        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(R.color.toolbar_layoutcolor,
                R.color.heading1_black,
                R.color.heading2_black,
                R.color.blue);

        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        }
        else
        {
            layoutManager = new LinearLayoutManager(getApplicationContext());
        }

        popvideos_recycle.setLayoutManager(layoutManager);
        popvideos_recycle.setHasFixedSize(true);
    }

    private void getDataFromMostWatchedAdapter()
    {
        Bundle bundle = getIntent().getExtras();
        detail_array = (ArrayList<Contact>) bundle.getSerializable("array");
        pos = bundle.getInt("pos");

        if (detail_array != null)
        {
            showDialogue();
            popular_episode_adapter = new PopularVideosAdapter(getApplicationContext(), detail_array);
            popvideos_recycle.setAdapter(popular_episode_adapter);
            hideDialogue();
        }
        else
        {
            detail_array = new ArrayList<>();
        }
    }

    private void showDialogue()
    {
        dialog = new ProgressDialog(this);
        dialog.setTitle("SAMAA TV News");
        dialog.setMessage("Loading Program Episodes...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void hideDialogue()
    {
        if (dialog.isShowing())
        {
            dialog.dismiss();
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

}
