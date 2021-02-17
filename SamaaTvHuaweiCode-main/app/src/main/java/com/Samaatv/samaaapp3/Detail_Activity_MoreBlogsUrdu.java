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

import com.Samaatv.samaaapp3.adapter.ListingsAdapterUrdu;
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


public class Detail_Activity_MoreBlogsUrdu extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
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
    private ListingsAdapterUrdu moreblogs_adapter;
    private ArrayList<Contact> moreblogs_list;
    private View parentView;

    //private PublisherAdView mAdView;
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
        setContentView(R.layout.fragment_recyclerview);


        //   getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getActionBar().setHomeButtonEnabled(true);
        //setTitle("My new title");

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        // ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER;
        // getSupportActionBar().setDisplayUseLogoEnabled(true);
        //   getSupportActionBar().setLogo(R.drawable.samaa_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar_layoutcolor)));


        // getSupportActionBar().setDisplayUseLogoEnabled(true);
        //   getSupportActionBar().setB
     /*   Bundle args = getIntent().getExtras();
        if (args  != null )
        {
            url = args.getString("url");
            pos = args.getInt("item_selected_key");

        }*/

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        detail_array = (ArrayList<Contact>) bundle.getSerializable("array");
        // pos = bundle.getInt("pos");
        blog_cat = bundle.getString("blog_cat");

      /*  Intent intent = getIntent();
        if (intent != null){
            url = intent.getStringExtra("url");
            pos = intent.getExtras().getInt("item_selected_key");
            feedURL = url;
        }
*/
        //new AsyncLoadXMLFeed().execute();
        //String pos=getArguments().getString("pos");
//        url= this.getIntent().getExtras().getString("url");

        // Inflate the layout for this fragment
        moreblogs_recycle = (RecyclerView) findViewById(R.id.recyclerView);
        //mAdView = (PublisherAdView) findViewById(R.id.ad_view);


        //PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();

        // Start loading the ad in the background.
        //mAdView.loadAd(adRequest);
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
            Call<ObjectBlogsCategoryList> call = api.getBlogDataUrdu();

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


                            /**
                             * Binding that List to Adapter
                             */
                            if (moreblogs_list != null)
                            {
                                moreblogs_adapter = new ListingsAdapterUrdu(getApplicationContext(), moreblogs_list);
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
                        Toast.makeText(Detail_Activity_MoreBlogsUrdu.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError)
                    {
                        Toast.makeText(Detail_Activity_MoreBlogsUrdu.this, "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException)
                    {
                        Toast.makeText(Detail_Activity_MoreBlogsUrdu.this, "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException)
                    {
                        Toast.makeText(Detail_Activity_MoreBlogsUrdu.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
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
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void NewsRefresh()
    {

        if (moreblogs_list != null)
        {
            moreblogs_adapter = new ListingsAdapterUrdu(getApplicationContext(), moreblogs_list);
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

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */

