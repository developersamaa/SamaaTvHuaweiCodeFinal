package com.Samaatv.samaaapp3;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.Samaatv.samaaapp3.utils.PublishAds;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TvShowsListing extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    static final boolean GRID_LAYOUT = false;
    static FrameLayout frame_footer;
    SwipeRefreshLayout swipeLayout;
    RecyclerView.LayoutManager layoutManager;
    JSONArray programsList = null;
    String feedURL;
    String url;
    int pos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String TAG = TvShowsListing.class.getSimpleName();
    private PublisherAdView mAdView;

    public TvShowsListing()
    {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        Bundle args = getArguments();
        if (args != null)
        {
            url = args.getString("url");
            pos = args.getInt("item_selected_key");

        }
        //String pos=getArguments().getString("pos");
        url = this.getArguments().getString("url");
        feedURL = url;
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_recyclerview, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // mAdView = (PublisherAdView) view.findViewById(R.id.ad);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);
        mAdView = (PublisherAdView) view.findViewById(R.id.ad_view);

        view.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        swipeLayout.setColorSchemeColors(R.color.toolbar_layoutcolor,
                R.color.heading1_black,
                R.color.heading2_black,
                R.color.blue);

        frame_footer = (FrameLayout) view.findViewById(R.id.footer);

//        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
//        // Start loading the ad in the background.
//        mAdView.loadAd(adRequest);


        new AsyncLoadXMLFeed().execute();

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        }
        else
        {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        }
        else
        {
            layoutManager = new LinearLayoutManager(getActivity());
        }
    }

    public void onRefresh()
    {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mAdapter = new RecyclerViewAdapterProgramsListingAds(getActivity(), programsList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        }, 5000);
       /* new AsyncLoadXMLFeed().execute();
        swipeLayout.setRefreshing(false);*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {
            PublishAds.loadAd(mAdView);
        }
    }

    private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void>
    {

        ProgressDialog pDialog;


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
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
                    Toast.makeText(getActivity(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();

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

                mAdapter = new RecyclerViewAdapterProgramsListingAds(getActivity(), programsList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

        }

    }
}
