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

import com.Samaatv.samaaapp3.adapter.ListingsAdapter;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.model.ObjectBlogsCategoryList;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import me.relex.circleindicator.CircleIndicator;


public class Detail_Activity_MoreBlogs extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    static final boolean GRID_LAYOUT = false;
    static PublisherInterstitialAd mPublisherInterstitialAd;
    //  private RecyclerView mRecyclerView;
    static FrameLayout frame_footer;
    SwipeRefreshLayout swipeLayout;
    RecyclerView.LayoutManager layoutManager;
    // JSONArray programsList=null;
    //private RecyclerView.Adapter mAdapter;
    //  private String TAG = TVShowListing_Activity.class.getSimpleName();
    String feedURL;
    String blog_cat;
    int pos;
    NewsCategoryList tempList;
    RecyclerView moreblogs_recycle;
    private ArrayList<Contact> detail_array;
    private ListingsAdapter moreblogs_adapter;
    private ArrayList<Contact> moreblogs_list;
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
        //pos = bundle.getInt("pos");
        blog_cat = bundle.getString("blog_cat");

        // Inflate the layout for this fragment
        moreblogs_recycle = (RecyclerView) findViewById(R.id.recyclerView);
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
        moreblogs_recycle.setLayoutManager(layoutManager);
        moreblogs_recycle.setHasFixedSize(true);

        if (InternetConnection.checkConnection(this))
        {
            // webView.loadUrl(URL);
            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(this);
            dialog.setTitle("SAMAA TV News");
            dialog.setMessage("Loading More Blogs ...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            //Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
            Call<ObjectBlogsCategoryList> call = api.getBlogDataNews();

            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<ObjectBlogsCategoryList>()
            {
                @Override
                public void onResponse(Call<ObjectBlogsCategoryList> call, Response<ObjectBlogsCategoryList> response)
                {


                    if (response.isSuccessful())
                    {

                        tempList = response.body().getMy_blogs();

                        if (tempList != null)
                        {

                            if (blog_cat.contains("power_games"))
                            {
                                moreblogs_list = tempList.getPowerGames();
                            }
                            else if (blog_cat.contains("sports_blogs"))
                            {
                                moreblogs_list = tempList.getSportBlogs();
                            }
                            else if (blog_cat.contains("food_shood"))
                            {
                                moreblogs_list = tempList.getFoodShood();
                            }
                            else if (blog_cat.contains("glitz_glams"))
                            {
                                moreblogs_list = tempList.getGlitzGlams();
                            }
                            else if (blog_cat.contains("social_pulse"))
                            {
                                moreblogs_list = tempList.getSocialPulse();
                            }

                            if (moreblogs_list != null)
                            {
                                moreblogs_adapter = new ListingsAdapter(getApplicationContext(), moreblogs_list);
                                moreblogs_recycle.setAdapter(moreblogs_adapter);
                                if (dialog.isShowing())
                                {
                                    dialog.dismiss();
                                }
                            }
                            else
                            {
                                moreblogs_list = new ArrayList<Contact>();
                            }
                        }
                        else
                        {
                            tempList = new NewsCategoryList();
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<ObjectBlogsCategoryList> call, Throwable t)
                {
                    if (dialog.isShowing())
                    {
                        dialog.dismiss();
                    }
                    if (t instanceof NullPointerException)
                    {
                        Toast.makeText(Detail_Activity_MoreBlogs.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError)
                    {
                        Toast.makeText(Detail_Activity_MoreBlogs.this, "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException)
                    {
                        Toast.makeText(Detail_Activity_MoreBlogs.this, "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException)
                    {
                        Toast.makeText(Detail_Activity_MoreBlogs.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "No internet connection !", Toast.LENGTH_SHORT).show();
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

    public void NewsRefresh()
    {

        if (moreblogs_list != null)
        {
            moreblogs_adapter = new ListingsAdapter(getApplicationContext(), moreblogs_list);
            moreblogs_recycle.setAdapter(moreblogs_adapter);
            swipeLayout.setRefreshing(false);
        }
        else
        {
            moreblogs_list = new ArrayList<Contact>();
        }
    }

    public void onRefresh()
    {
        NewsRefresh();
    }

}

