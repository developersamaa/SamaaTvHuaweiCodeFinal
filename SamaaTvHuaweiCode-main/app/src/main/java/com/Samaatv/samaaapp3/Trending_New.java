package com.Samaatv.samaaapp3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Samaatv.samaaapp3.adapter.EditorsChoiceAdapter;
import com.Samaatv.samaaapp3.adapter.TrendingAdapter;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.ContactList;
import com.Samaatv.samaaapp3.model.ObjectNewsContactList;
import com.Samaatv.samaaapp3.utils.DataCache;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trending_New extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    TextView titletrend, datetrend, title1;
    //    private String tickerFullSize,ticker_main;
    ImageView imagetrending, playbtn;

    //    ArrayList<String> ticker_array = new ArrayList<String>();
    float textWidth_actual;
    float textWidth_start;
    SwipeRefreshLayout swipeLayout;
    //    TextView more_pak, more_glo, more_enter, more_bus, more_editor, more_sports;
//    TextView pakistan_head1,pakistan_head2, global_head1, global_head2, editor_head1, editor_head2, sports_head1, sports_head2,
//            enter_head1, enter_head2, bus_head1, bus_head2;
    String videourl;
    //    CardView cardView;
    RecyclerView pakistan_recycle, world_recycle, sports_recycle, edchoice_recycle, enter_recycle, business_recycle;
    Fragment frg = null;
    ContactList tempList;
    PublisherAdRequest adRequest;
    String image;
    String d1;
    RelativeLayout home_ad_head;
    /**
     * Views
     */
    //private ListView listView;
    private RecyclerView recyclerView;
    //    private ArrayList<Contact> breakingList;
    private View parentView;
    private TrendingAdapter pakistan_adapter, world_adapter, sports_adapter, enter_adapter, business_adapter;
    private EditorsChoiceAdapter edchoice_adapter;
    private PublisherAdView mAdView, mAdView1;
    //    private ArrayList<Contact> trendList;
    private ArrayList<Contact> nationList;
    private ArrayList<Contact> sportsList;
    private ArrayList<Contact> editorList;
    //    int ticker_index;
    private ArrayList<Contact> enterList;
    private ArrayList<Contact> economyList;
    private ArrayList<Contact> globalList;

    public Trending_New()
    {
        // Required empty public constructor
    }

    public static String getTimeAgo(long time)
    {
        if (time < 1000000000000L)
        {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0)
        {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS)
        {
            return "Just now";
        }
        else if (diff < 2 * MINUTE_MILLIS)
        {
            return "a minute ago";
        }
        else if (diff < 50 * MINUTE_MILLIS)
        {
            return diff / MINUTE_MILLIS + " minutes ago";
        }
        else if (diff < 90 * MINUTE_MILLIS)
        {
            return "an hour ago";
        }
        else if (diff < 24 * HOUR_MILLIS)
        {
            return diff / HOUR_MILLIS + " hours ago";
        }
        else if (diff < 48 * HOUR_MILLIS)
        {
            return "yesterday";
        }
        else
        {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    private void fetchHomeListPreference()
    {

        tempList = DataCache.retrieveHomeList(getActivity());

        if (tempList == null)
        {
            fetchDataAndOtherExecutions();
        }

        if (tempList != null)
        {
//            trendList = tempList.getTrend();
            nationList = tempList.getNational();
            sportsList = tempList.getSports();
            editorList = tempList.getEditorChoice();
            enterList = tempList.getEntertainment();
            economyList = tempList.getEconomy();
            globalList = tempList.getGlobal();
//            breakingList = tempList.getBreaking();

//            try {
//                titletrend.setText(trendList.get(0).getTitle());
//                image = trendList.get(0).getImage();
//                d1 = trendList.get(0).getDate();
//                videourl = trendList.get(0).getVideo();
//            }
//            catch (IndexOutOfBoundsException ioe){
//                Log.e("IOE","index out ka bekar exception",ioe);
//            }
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
//            Date testDate = null;
//            try {
//                testDate = sdf.parse(d1);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
//            String newFormat = formatter.format(testDate);
//            long milliseconds = testDate.getTime();
//            String gettime = getTimeAgo(milliseconds);
//
//            datetrend.setText(gettime);
//
//            Picasso.with(getActivity())
//                    .load(image)
//                    .error(R.drawable.logo_samaatv)
//                    .placeholder(R.drawable.logo_samaatv)
//                    .into(imagetrending);
//
//            if (videourl != null && !videourl.equals("None")) {
//                playbtn.setVisibility(View.VISIBLE);
//            } else {
//                playbtn.setVisibility(View.GONE);
//            }
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("array", trendList);
//                    Intent intent = new Intent(getActivity(), Detail_Activity.class);
//                    intent.putExtras(bundle);
//                    intent.putExtra("pos", 0);
//                    getActivity().startActivity(intent);
//                }
//            });
//
//            MainActivity.ticker_head.setText("");
//            tickerFullSize = "";
//            ticker_index = 0;
//
//            try {
//                ticker_main = breakingList.get(ticker_index).getTitle();
//                tickerFullSize = ticker_main;
//            }catch (IndexOutOfBoundsException ioe){
//                Log.e("IOE","index out ka bekar exception",ioe);
//            }
//
//            float densityMultiplier = getContext().getResources().getDisplayMetrics().density;
//            float scaledPx = 14 * densityMultiplier;
//            Paint paint = new Paint();
//            paint.setTypeface(Typeface.DEFAULT);
//            paint.setTextSize(scaledPx);
//
//            if (tickerFullSize != null) {
//                textWidth_actual = paint.measureText(tickerFullSize, 0, tickerFullSize.length());
//            }
//            if (ticker_main != null) {
//                textWidth_start = paint.measureText(ticker_main, 0, ticker_main.length());
//            }
//
//            Animation animationToLeft = new TranslateAnimation(textWidth_start, -textWidth_actual, 0, 0);
//            animationToLeft.setDuration(20000); //should i increase more or make it less?
//            animationToLeft.setRepeatMode(Animation.RESTART);
//            animationToLeft.setRepeatCount(Animation.INFINITE);
//
//            MainActivity.ticker_head.setAnimation(animationToLeft);
//
//            animationToLeft.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                    try {
//                        if (ticker_index < breakingList.size()) {
//                            ticker_main = breakingList.get(ticker_index).getTitle();
//                            MainActivity.ticker_head.setText(ticker_main);
//                            clickableTicker(ticker_index, breakingList);
//                            ticker_index++;
//                        } else {
//                            ticker_index = 0;
//                            ticker_main = breakingList.get(ticker_index).getTitle();
//                            MainActivity.ticker_head.setText(ticker_main);
//                            clickableTicker(ticker_index, breakingList);
//                        }
//                    }
//                    catch (IndexOutOfBoundsException ioe){
//                        Log.e("IOE","index out ka bekar exception",ioe);
//                    }
//                }
//            });
//
//            // Ticker work ends here


            pakistan_adapter = new TrendingAdapter(getActivity(), nationList, "National");
            pakistan_recycle.setAdapter(pakistan_adapter);
            pakistan_adapter.notifyDataSetChanged();

            sports_adapter = new TrendingAdapter(getActivity(), sportsList, "Sports");
            sports_recycle.setAdapter(sports_adapter);
            sports_adapter.notifyDataSetChanged();

            edchoice_adapter = new EditorsChoiceAdapter(getActivity(), editorList);
            edchoice_recycle.setAdapter(edchoice_adapter);
            edchoice_adapter.notifyDataSetChanged();

            enter_adapter = new TrendingAdapter(getActivity(), enterList, "Entertainment");
            enter_recycle.setAdapter(enter_adapter);
            enter_adapter.notifyDataSetChanged();

            business_adapter = new TrendingAdapter(getActivity(), economyList, "Economy");
            business_recycle.setAdapter(business_adapter);
            business_adapter.notifyDataSetChanged();

            world_adapter = new TrendingAdapter(getActivity(), globalList, "Global");
            world_recycle.setAdapter(world_adapter);
            world_adapter.notifyDataSetChanged();
        }
        else
        {
            tempList = new ContactList();
        }
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
        ViewGroup rootView = checkScreenAndSetLayout(inflater, container);
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.trend_layout, container, false);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeColors(R.color.toolbar_layoutcolor,
                R.color.heading1_black,
                R.color.heading2_black,
                R.color.blue);

//        trendList = new ArrayList<>();
        nationList = new ArrayList<>();
        sportsList = new ArrayList<>();
        editorList = new ArrayList<>();
        enterList = new ArrayList<>();
        economyList = new ArrayList<>();
        globalList = new ArrayList<>();
//        breakingList = new ArrayList<>();

        parentView = rootView.findViewById(R.id.parent_view);
        playbtn = (ImageView) rootView.findViewById(R.id.play_vdo);
        home_ad_head = (RelativeLayout) rootView.findViewById(R.id.home_ad_head);
        //imagetrending = (ImageView) rootView.findViewById(R.id.img_trend);
        setAd_load();

        pakistan_recycle = (RecyclerView) rootView.findViewById(R.id.list_pak);
        world_recycle = (RecyclerView) rootView.findViewById(R.id.list_glo);
        sports_recycle = (RecyclerView) rootView.findViewById(R.id.list_spo);
        enter_recycle = (RecyclerView) rootView.findViewById(R.id.list_enter);
        business_recycle = (RecyclerView) rootView.findViewById(R.id.list_bus);
        edchoice_recycle = (RecyclerView) rootView.findViewById(R.id.editor_view);

//        cardView = (CardView) rootView.findViewById(R.id.card_view1);
//        titletrend = (TextView) rootView.findViewById(R.id.title_trend);
//        imagetrending = (ImageView) rootView.findViewById(R.id.img_trend);
//        datetrend = (TextView) rootView.findViewById(R.id.date_trend);

//        pakistan_head1=(TextView)rootView.findViewById(R.id.pakistan_head1);
//        pakistan_head2=(TextView)rootView.findViewById(R.id.pakistan_head2);
//
//        global_head1=(TextView)rootView.findViewById(R.id.global_head1);
//        global_head2=(TextView)rootView.findViewById(R.id.global_head2);
//
//        editor_head1=(TextView)rootView.findViewById(R.id.editor_head1);
//        editor_head2=(TextView)rootView.findViewById(R.id.editor_head2);
//
//        sports_head1=(TextView)rootView.findViewById(R.id.sports_head1);
//        sports_head2=(TextView)rootView.findViewById(R.id.sports_head2);
//
//
//        enter_head1=(TextView)rootView.findViewById(R.id.enter_head1);
//        enter_head2=(TextView)rootView.findViewById(R.id.enter_head2);
//
//        bus_head1=(TextView)rootView.findViewById(R.id.bus_head1);
//        bus_head2=(TextView)rootView.findViewById(R.id.bus_head2);


        mAdView = (PublisherAdView) rootView.findViewById(R.id.ad_view1);
        mAdView1 = (PublisherAdView) rootView.findViewById(R.id.ad_view2);
//        mAdView2 = (PublisherAdView) rootView.findViewById(R.id.ad_view3);

        adRequest = new PublisherAdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest);
//        mAdView2.loadAd(adRequest);


//        more_pak = (TextView) rootView.findViewById(R.id.more_pak);
//        more_glo = (TextView) rootView.findViewById(R.id.more_glo);
//
//        more_enter = (TextView) rootView.findViewById(R.id.more_enter);
//        more_editor = (TextView) rootView.findViewById(R.id.more_edit);
//
//        more_bus = (TextView) rootView.findViewById(R.id.more_bus);
//        more_sports = (TextView) rootView.findViewById(R.id.more_sport);
//
//        more_pak.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivity.viewPager.setCurrentItem(3);
//            }
//        });
//
//        more_glo.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivity.viewPager.setCurrentItem(4);
//            }
//        });
//
//        more_enter.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivity.viewPager.setCurrentItem(7);
//            }
//        });
//
//        more_editor.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivity.viewPager.setCurrentItem(8);
//            }
//        });
//
//        more_bus.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivity.viewPager.setCurrentItem(5);
//            }
//        });
//
//        more_sports.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivity.viewPager.setCurrentItem(6);
//            }
//        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutManager6 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        //RecyclerView for vertical layout
        pakistan_recycle.setLayoutManager(layoutManager2);
        pakistan_recycle.setNestedScrollingEnabled(false);
        pakistan_recycle.setHasFixedSize(true);

        //world
        world_recycle.setLayoutManager(layoutManager3);
        world_recycle.setNestedScrollingEnabled(false);
        world_recycle.setHasFixedSize(true);

        //entertainment
        enter_recycle.setLayoutManager(layoutManager4);
        enter_recycle.setNestedScrollingEnabled(false);
        enter_recycle.setHasFixedSize(true);

        //business
        business_recycle.setLayoutManager(layoutManager5);
        business_recycle.setNestedScrollingEnabled(false);
        business_recycle.setHasFixedSize(true);

        //sports
        sports_recycle.setLayoutManager(layoutManager6);
        sports_recycle.setNestedScrollingEnabled(false);
        sports_recycle.setHasFixedSize(true);

        //RecyclerView for horizontal layout
        edchoice_recycle.setLayoutManager(layoutManager);
        edchoice_recycle.setNestedScrollingEnabled(false);
        edchoice_recycle.setHasFixedSize(true);

        if (InternetConnection.checkConnection(getActivity()))
        {
            fetchDataAndOtherExecutions();
        }
        else
        {
            fetchHomeListPreference();
        }

        return rootView;
    }
//
//    private void clickableTicker(final int ticker_index, final ArrayList<Contact> breakingList) {
//        MainActivity.ticker_head.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", breakingList);
//                Intent intent = new Intent(getActivity(), Detail_Activity.class);
//                intent.putExtras(bundle);
//                intent.putExtra("pos",ticker_index );
//                startActivity(intent);
//            }
//        });
//    }

    private void setAd_load()
    {

        PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
        mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
        mPublisherAdView.setAdUnitId("/14309701/and-en/home/and-en.home.lb-atf");
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
        home_ad_head.addView(mPublisherAdView);
        mPublisherAdView.loadAd(publisherAdRequestBuilder.build());
    }

    private ViewGroup checkScreenAndSetLayout(LayoutInflater inflater, ViewGroup container)
    {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        ViewGroup rootView;
        if (diagonalInches >= 6.5)
        {
            // 6.5inch device or bigger
            rootView = (ViewGroup) inflater.inflate(R.layout.trend_layout_large, container, false);

        }
        else
        {
            // smaller device
            rootView = (ViewGroup) inflater.inflate(R.layout.trend_layout, container, false);

        }
        return rootView;
    }

    private void relaunchTheApp()
    {
        Intent reLaunchIntent = new Intent(getActivity(), SplashScreen.class);
        startActivity(reLaunchIntent);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (mAdView != null && mAdView1 != null)
        {
            mAdView.resume();
            mAdView1.resume();
//            mAdView2.resume();
        }

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SamaaAppAnalytics.getInstance().trackScreenView("Home Screen News English");
                SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "Home Screen News English");
            }
        }, 6000);
    }


    @Override
    public void onPause()
    {
        // Pause the PublisherAdView.
        if (mAdView != null && mAdView1 != null)
        {
            mAdView.pause();
            mAdView1.pause();
//            mAdView2.pause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        // Destroy the PublisherAdView.
        if (mAdView != null && mAdView1 != null)
        {
            mAdView.destroy();
            mAdView1.destroy();
//            mAdView2.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh()
    {
        fetchDataAndOtherExecutions();
    }

    private void fetchDataAndOtherExecutions()
    {
        if (InternetConnection.checkConnection(getActivity()))
        {

            ApiService api = RetroClient.getApiService();
            Call<ObjectNewsContactList> call = api.getMyJSON();
            call.enqueue(new Callback<ObjectNewsContactList>()
            {
                @Override
                public void onResponse(Call<ObjectNewsContactList> call, Response<ObjectNewsContactList> response)
                {

                    if (response.isSuccessful())
                    {

                        if (response.body() != null)
                        {
                            tempList = response.body().getObjCateg_MainList();
                            if (tempList != null)
                            {
                                DataCache.saveHomeList(getActivity(), tempList);
//                                trendList = tempList.getTrend();
                                nationList = tempList.getNational();
                                sportsList = tempList.getSports();
                                editorList = tempList.getEditorChoice();
                                enterList = tempList.getEntertainment();
                                economyList = tempList.getEconomy();
                                globalList = tempList.getGlobal();
//                                breakingList = tempList.getBreaking();

//                                try {
//                                    titletrend.setText(trendList.get(0).getTitle());
//                                    image = trendList.get(0).getImage();
//                                    d1 = trendList.get(0).getDate();
//                                    videourl = trendList.get(0).getVideo();
//                                }
//                                catch (IndexOutOfBoundsException ioe){
//                                    Log.e("IOE","index out ka bekar exception",ioe);
//                                }
//
//
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
//                                Date testDate = null;
//                                try {
//                                    testDate = sdf.parse(d1);
//                                } catch (Exception ex) {
//                                    ex.printStackTrace();
//                                }
//
//                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
//                                String newFormat = formatter.format(testDate);
//                                long milliseconds = testDate.getTime();
//                                String gettime = getTimeAgo(milliseconds);
//
//                                datetrend.setText(gettime);
//
//                                Picasso.with(getActivity())
//                                        .load(image)
//                                        .error(R.drawable.logo_samaatv)
//                                        .placeholder(R.drawable.logo_samaatv)
//                                        .into(imagetrending);
//
//                                if (videourl != null && !videourl.equals("None")) {
//                                    playbtn.setVisibility(View.VISIBLE);
//                                } else {
//                                    playbtn.setVisibility(View.GONE);
//                                }
//                                cardView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//
//                                        Bundle bundle = new Bundle();
//                                        bundle.putSerializable("array", trendList);
//                                        Intent intent = new Intent(getActivity(), Detail_Activity.class);
//                                        intent.putExtras(bundle);
//                                        intent.putExtra("pos", 0);
//                                        getActivity().startActivity(intent);
//                                    }
//                                });
//
//                                MainActivity.ticker_head.setText("");
//                                tickerFullSize = "";
//                                ticker_index = 0;
//
//                                try {
//                                    ticker_main = breakingList.get(ticker_index).getTitle();
//                                    tickerFullSize = ticker_main;
//                                }catch (IndexOutOfBoundsException ioe){
//                                    Log.e("IOE","index out ka bekar exception",ioe);
//                                }
//
//                                float densityMultiplier = getContext().getResources().getDisplayMetrics().density;
//                                float scaledPx = 14 * densityMultiplier;
//                                Paint paint = new Paint();
//                                paint.setTypeface(Typeface.DEFAULT);
//                                paint.setTextSize(scaledPx);
//
//                                if (tickerFullSize != null) {
//                                    textWidth_actual = paint.measureText(tickerFullSize, 0, tickerFullSize.length());
//                                }
//                                if (ticker_main != null) {
//                                    textWidth_start = paint.measureText(ticker_main, 0, ticker_main.length());
//                                }
//
//                                Animation animationToLeft = new TranslateAnimation(textWidth_start, -textWidth_actual, 0, 0);
//                                animationToLeft.setDuration(20000); //should i increase more or make it less?
//                                animationToLeft.setRepeatMode(Animation.RESTART);
//                                animationToLeft.setRepeatCount(Animation.INFINITE);
//
//                                MainActivity.ticker_head.setAnimation(animationToLeft);
//
//                                animationToLeft.setAnimationListener(new Animation.AnimationListener() {
//                                    @Override
//                                    public void onAnimationStart(Animation animation) {
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animation animation) {
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animation animation) {
//                                        try {
//                                            if (ticker_index < breakingList.size()) {
//                                                ticker_main = breakingList.get(ticker_index).getTitle();
//                                                MainActivity.ticker_head.setText(ticker_main);
//                                                clickableTicker(ticker_index, breakingList);
//                                                ticker_index++;
//                                            } else {
//                                                ticker_index = 0;
//                                                ticker_main = breakingList.get(ticker_index).getTitle();
//                                                MainActivity.ticker_head.setText(ticker_main);
//                                                clickableTicker(ticker_index, breakingList);
//                                            }
//                                        }
//                                        catch (IndexOutOfBoundsException ioe){
//                                            Log.e("IOE","index out ka bekar exception",ioe);
//                                        }
//                                    }
//                                });
//
//                                // Ticker work ends here

                                pakistan_adapter = new TrendingAdapter(getActivity(), nationList, "National");
                                pakistan_recycle.setAdapter(pakistan_adapter);
                                pakistan_adapter.notifyDataSetChanged();

                                sports_adapter = new TrendingAdapter(getActivity(), sportsList, "Sports");
                                sports_recycle.setAdapter(sports_adapter);
                                sports_adapter.notifyDataSetChanged();

                                edchoice_adapter = new EditorsChoiceAdapter(getActivity(), editorList);
                                edchoice_recycle.setAdapter(edchoice_adapter);
                                edchoice_adapter.notifyDataSetChanged();

                                enter_adapter = new TrendingAdapter(getActivity(), enterList, "Entertainment");
                                enter_recycle.setAdapter(enter_adapter);
                                enter_adapter.notifyDataSetChanged();

                                business_adapter = new TrendingAdapter(getActivity(), economyList, "Economy");
                                business_recycle.setAdapter(business_adapter);
                                business_adapter.notifyDataSetChanged();

                                world_adapter = new TrendingAdapter(getActivity(), globalList, "Global");
                                world_recycle.setAdapter(world_adapter);
                                world_adapter.notifyDataSetChanged();

                                swipeLayout.setRefreshing(false);
                            }
                            else
                            {
                                tempList = new ContactList();
                            }
                        }
                        else
                        {
                            relaunchTheApp();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Something is wrong !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ObjectNewsContactList> call, Throwable t)
                {

                    if (t instanceof NullPointerException)
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError)
                    {
                        Toast.makeText(getActivity(), "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException)
                    {
                        Toast.makeText(getActivity(), "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException)
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IndexOutOfBoundsException)
                    {
                        Toast.makeText(getActivity(), "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getActivity(), "Sorry, internet connection is not available !", Toast.LENGTH_LONG).show();
        }
    }
}