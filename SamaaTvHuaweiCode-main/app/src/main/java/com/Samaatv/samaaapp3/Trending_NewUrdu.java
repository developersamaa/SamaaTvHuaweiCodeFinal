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

import com.Samaatv.samaaapp3.adapter.EditorsChoiceAdapterUrdu;
import com.Samaatv.samaaapp3.adapter.TrendingAdapterUrdu;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.ContactList;
import com.Samaatv.samaaapp3.model.ObjectNewsContactList;
import com.Samaatv.samaaapp3.utils.DataCacheUrdu;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trending_NewUrdu extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    TextView titletrend, datetrend, title1, date1;
    ImageView imagetrending, playbtn;
//    ArrayList<String> ticker_array = new ArrayList<String>();
    float textWidth_actual;
    float textWidth_start;
    //    private String tickerFullSize,ticker_main;
    SwipeRefreshLayout swipeLayout;
    //    TextView more_pak, more_glo, more_enter, more_bus, more_editor, more_sports;
//    TextView pakistan_head1,pakistan_head2, global_head1, global_head2, editor_head1, editor_head2, sports_head1, sports_head2,
//            enter_head1, enter_head2, bus_head1, bus_head2;
    String videourl;
    //    CardView cardView;
    RecyclerView pakistan_recycle, world_recycle, sports_recycle, edchoice_recycle, enter_recycle, business_recycle;
    PublisherAdRequest adRequest;
    ContactList tempList;
    int ticker_index;
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
    private TrendingAdapterUrdu pakistan_adapter, world_adapter, sports_adapter, enter_adapter, business_adapter;
    private EditorsChoiceAdapterUrdu edchoice_adapter;
    private PublisherAdView mAdView, mAdView1, mAdView2;
    //    private ArrayList<Contact> trendList;
    private ArrayList<Contact> nationList;
    private ArrayList<Contact> sportsList;
    private ArrayList<Contact> editorList;
    private ArrayList<Contact> enterList;
    private ArrayList<Contact> economyList;
    private ArrayList<Contact> globalList;
    private Boolean isStarted = false;
    private Boolean isVisible = false;

    public Trending_NewUrdu()
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

        parentView = rootView.findViewById(R.id.coordinatorLayout);
        playbtn = (ImageView) rootView.findViewById(R.id.play_vdo);
        home_ad_head = (RelativeLayout) rootView.findViewById(R.id.home_ad_head);

        setAd_load();

        pakistan_recycle = (RecyclerView) rootView.findViewById(R.id.list_pak);
        world_recycle = (RecyclerView) rootView.findViewById(R.id.list_glo);
        sports_recycle = (RecyclerView) rootView.findViewById(R.id.list_spo);
        enter_recycle = (RecyclerView) rootView.findViewById(R.id.list_enter);
        business_recycle = (RecyclerView) rootView.findViewById(R.id.list_bus);
        edchoice_recycle = (RecyclerView) rootView.findViewById(R.id.editor_view);

//        cardView = (CardView) rootView.findViewById(R.id.card_view1);
//        datetrend = (TextView) rootView.findViewById(R.id.date_trend);
//        titletrend = (TextView) rootView.findViewById(R.id.title_trend);
//        imagetrending = (ImageView) rootView.findViewById(R.id.img_trend);

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
//                MainActivityUrdu.viewPager.setCurrentItem(7);
////                getActivity().getActionBar().setSelectedNavigationItem(2);
//            }
//        });
//
//        more_glo.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivityUrdu.viewPager.setCurrentItem(6);
////                getActivity().getActionBar().setSelectedNavigationItem(2);
//            }
//        });
//
//        more_enter.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivityUrdu.viewPager.setCurrentItem(3);
////                getActivity().getActionBar().setSelectedNavigationItem(2);
//            }
//        });
//
//        more_editor.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivityUrdu.viewPager.setCurrentItem(2);
////                getActivity().getActionBar().setSelectedNavigationItem(2);
//            }
//        });
//
//        more_bus.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivityUrdu.viewPager.setCurrentItem(5);
////                getActivity().getActionBar().setSelectedNavigationItem(2);
//            }
//        });
//
//        more_sports.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view)
//            {
//                MainActivityUrdu.viewPager.setCurrentItem(4);
////                getActivity().getActionBar().setSelectedNavigationItem(2);
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
        edchoice_recycle.setHasFixedSize(true);

        if (InternetConnection.checkConnection(getActivity()))
        {
            fetchNewsHit();
        }
        else
        {
            fetchHomeListUrduPreference();
        }


        return rootView;
    }

    private void setAd_load()
    {
        PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
        mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
        mPublisherAdView.setAdUnitId("/14309701/and-ur/home/and-ur.home.lb-atf");
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
            rootView = (ViewGroup) inflater.inflate(R.layout.trend_layout_urdu_large, container, false);

        }
        else
        {
            // smaller device
            rootView = (ViewGroup) inflater.inflate(R.layout.trend_layout_urdu, container, false);

        }
        return rootView;

    }

//    private void clickableTicker(final int ticker_index, final ArrayList<Contact> breakingList) {
//        MainActivityUrdu.ticker_head.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", breakingList);
//                Intent intent = new Intent(getActivity(), Detail_Activity_Urdu.class);
//                intent.putExtras(bundle);
//                intent.putExtra("pos",ticker_index );
//                startActivity(intent);
//            }
//        });
//    }

    private void fetchHomeListUrduPreference()
    {

        tempList = DataCacheUrdu.retrieveHomeList(getActivity());

        if (tempList == null)
        {
            fetchNewsHit();
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
//            catch (IndexOutOfBoundsException ioe) {
//                Log.e("IOE", "index out ka bekar exception", ioe);
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
//            System.out.println(".....Date..." + newFormat);
//            // end date format
//
//            long milliseconds = testDate.getTime();
//            String gettime = getTimeAgo(milliseconds);
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
//
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("array", trendList);
//                    Intent intent = new Intent(getActivity(), Detail_Activity_Urdu.class);
//                    intent.putExtras(bundle);
//                    intent.putExtra("pos", 0);
//                    getActivity().startActivity(intent);
//
//                }
//            });
//
//            MainActivityUrdu.ticker_head.setText("");
//            tickerFullSize = "";
//            ticker_index = 0;
//
//            try {
//                ticker_main = breakingList.get(ticker_index).getTitle();
//                tickerFullSize = ticker_main;
//            } catch (IndexOutOfBoundsException ioe) {
//                Log.e("IOE", "index out ka bekar exception", ioe);
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
//            Animation animationToRight = new TranslateAnimation(-textWidth_actual, textWidth_start, 0, 0);
//            animationToRight.setDuration(20000);
//            animationToRight.setRepeatMode(Animation.RESTART);
//            animationToRight.setRepeatCount(Animation.INFINITE);
//
//            MainActivityUrdu.ticker_head.setAnimation(animationToRight);
//
//            animationToRight.setAnimationListener(new Animation.AnimationListener() {
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
//                            MainActivityUrdu.ticker_head.setText(ticker_main);
//                            clickableTicker(ticker_index, breakingList);
//                            ticker_index++;
//                        } else {
//                            ticker_index = 0;
//                            ticker_main = breakingList.get(ticker_index).getTitle();
//                            MainActivityUrdu.ticker_head.setText(ticker_main);
//                            clickableTicker(ticker_index, breakingList);
//                        }
//                    } catch (IndexOutOfBoundsException ioe) {
//                        Log.e("IOE", "index out ka bekar exception", ioe);
//                    }
//                }
//            });

            pakistan_adapter = new TrendingAdapterUrdu(getActivity(), nationList, "National");
            pakistan_recycle.setAdapter(pakistan_adapter);
            pakistan_adapter.notifyDataSetChanged();

            sports_adapter = new TrendingAdapterUrdu(getActivity(), sportsList, "Sports");
            sports_recycle.setAdapter(sports_adapter);
            sports_adapter.notifyDataSetChanged();

            edchoice_adapter = new EditorsChoiceAdapterUrdu(getActivity(), editorList);
            edchoice_recycle.setAdapter(edchoice_adapter);
            edchoice_adapter.notifyDataSetChanged();

            enter_adapter = new TrendingAdapterUrdu(getActivity(), enterList, "Entertainment");
            enter_recycle.setAdapter(enter_adapter);
            enter_adapter.notifyDataSetChanged();

            business_adapter = new TrendingAdapterUrdu(getActivity(), economyList, "Economy");
            business_recycle.setAdapter(business_adapter);
            business_adapter.notifyDataSetChanged();

            world_adapter = new TrendingAdapterUrdu(getActivity(), globalList, "Global");
            world_recycle.setAdapter(world_adapter);
            world_adapter.notifyDataSetChanged();

        }
        else
        {
            tempList = new ContactList();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (mAdView != null && mAdView1 != null && mAdView2 != null)
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
                SamaaAppAnalytics.getInstance().trackScreenView("Home News Screen Urdu");
                SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "Home News Screen Urdu");
            }
        }, 6000);
    }


    @Override
    public void onPause()
    {
        // Pause the PublisherAdView.
        if (mAdView != null && mAdView1 != null && mAdView2 != null)
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
        if (mAdView != null && mAdView1 != null && mAdView2 != null)
        {
            mAdView.destroy();
            mAdView1.destroy();
//            mAdView2.destroy();
        }
        super.onDestroy();
    }


    private void fetchNewsHit()
    {
        if (InternetConnection.checkConnection(getActivity()))
        {

            ApiService api = RetroClient.getApiService();
            Call<ObjectNewsContactList> call = api.getTrendUrdu();
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
                                DataCacheUrdu.saveHomeList(getActivity(), tempList);
//                                trendList = tempList.getTrend();
                                nationList = tempList.getNational();
                                sportsList = tempList.getSports();
                                editorList = tempList.getEditorChoice();
                                enterList = tempList.getEntertainment();
                                economyList = tempList.getEconomy();
                                globalList = tempList.getGlobal();
//                                breakingList = tempList.getBreaking();

//                                //Setting value for single trending news
//                                try {
//                                    titletrend.setText(trendList.get(0).getTitle());
//                                    image = trendList.get(0).getImage();
//                                    d1 = trendList.get(0).getDate();
//                                    videourl = trendList.get(0).getVideo();
//                                } catch (IndexOutOfBoundsException ioe) {
//                                    Log.e("IOE", "index out ka bekar exception", ioe);
//                                }
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
//                                System.out.println(".....Date..." + newFormat);
//                                // end date format
//
//                                long milliseconds = testDate.getTime();
//
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
//
//                                cardView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Bundle bundle = new Bundle();
//                                        bundle.putSerializable("array", trendList);
//                                        Intent intent = new Intent(getActivity(), Detail_Activity_Urdu.class);
//                                        intent.putExtras(bundle);
//                                        intent.putExtra("pos", 0);
//                                        getActivity().startActivity(intent);
//
//                                    }
//                                });
//
//                                //Settings Trending ends here

//                                //Setting ticker starts here
//                                MainActivityUrdu.ticker_head.setText("");
//                                tickerFullSize = "";
//                                ticker_index = 0;
//
//                                try {
//                                    ticker_main = breakingList.get(ticker_index).getTitle();
//                                    tickerFullSize = ticker_main;
//                                } catch (IndexOutOfBoundsException ioe) {
//                                    Log.e("IOE", "index out ka bekar exception", ioe);
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
//                                Animation animationToRight = new TranslateAnimation(-textWidth_actual, textWidth_start, 0, 0);
//                                animationToRight.setDuration(20000);
//                                animationToRight.setRepeatMode(Animation.RESTART);
//                                animationToRight.setRepeatCount(Animation.INFINITE);
//
//                                MainActivityUrdu.ticker_head.setAnimation(animationToRight);
//
//                                animationToRight.setAnimationListener(new Animation.AnimationListener() {
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
//                                                MainActivityUrdu.ticker_head.setText(ticker_main);
//                                                clickableTicker(ticker_index, breakingList);
//                                                ticker_index++;
//                                            } else {
//                                                ticker_index = 0;
//                                                ticker_main = breakingList.get(ticker_index).getTitle();
//                                                MainActivityUrdu.ticker_head.setText(ticker_main);
//                                                clickableTicker(ticker_index, breakingList);
//                                            }
//                                        } catch (IndexOutOfBoundsException ioe) {
//                                            Log.e("IOE", "index out ka bekar exception", ioe);
//                                        }
//                                    }
//                                });
//                                //Setting ticker ends here

                                /**
                                 * Binding that List to Adapter
                                 */

                                pakistan_adapter = new TrendingAdapterUrdu(getActivity(), nationList, "National");
                                pakistan_recycle.setAdapter(pakistan_adapter);
                                pakistan_adapter.notifyDataSetChanged();

                                sports_adapter = new TrendingAdapterUrdu(getActivity(), sportsList, "Sports");
                                sports_recycle.setAdapter(sports_adapter);
                                sports_adapter.notifyDataSetChanged();

                                edchoice_adapter = new EditorsChoiceAdapterUrdu(getActivity(), editorList);
                                edchoice_recycle.setAdapter(edchoice_adapter);
                                edchoice_adapter.notifyDataSetChanged();

                                enter_adapter = new TrendingAdapterUrdu(getActivity(), enterList, "Entertainment");
                                enter_recycle.setAdapter(enter_adapter);
                                enter_adapter.notifyDataSetChanged();

                                business_adapter = new TrendingAdapterUrdu(getActivity(), economyList, "Economy");
                                business_recycle.setAdapter(business_adapter);
                                business_adapter.notifyDataSetChanged();

                                world_adapter = new TrendingAdapterUrdu(getActivity(), globalList, "Global");
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
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
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
                }
            });

        }
        else
        {
            Toast.makeText(getActivity(), "Internet Connection Not Available !", Toast.LENGTH_SHORT).show();
        }

    }

    private void relaunchTheApp()
    {
        Intent reLaunchIntent = new Intent(getActivity(), SplashScreen.class);
        startActivity(reLaunchIntent);
    }

    @Override
    public void onRefresh()
    {
        fetchNewsHit();
    }
}