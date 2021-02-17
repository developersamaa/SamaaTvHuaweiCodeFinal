package com.Samaatv.samaaapp3.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.Samaatv.samaaapp3.Category_Add_Urdu;
import com.Samaatv.samaaapp3.MainActivityUrdu;
import com.Samaatv.samaaapp3.PakistanNews;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.adapter.ListingsAdapter;
import com.Samaatv.samaaapp3.adapter.MyNewsAdapterUrdu;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.model.ObjectNewsCategoryList;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.PublishAds;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 1/7/2017.
 */
public class ByCategoryNewsUrdu extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    static final boolean GRID_LAYOUT = false;
    private static final String myNewsURL = "https://www.samaa.tv/urdu/my_news/?category=";
    public static boolean AllowRefresh;
    public static LinearLayout addcat_layout;
    static FragmentTransaction tr;
    ;
    static FrameLayout frame_footer;
    private static PublisherAdView mAdView, mAdView1;
    private static ArrayList<Contact> nationList;
    private static ArrayList<Contact> sportsList;
    private static ArrayList<Contact> editorList;
    private static ArrayList<Contact> enterList;
    private static ArrayList<Contact> economyList;
    private static ArrayList<Contact> globalList;
    private static ArrayList<Contact> healthList;
    private static ArrayList<Contact> lifestyleList;
    private static ArrayList<Contact> socialList;
    private static ArrayList<Contact> scitechList;
    private static ArrayList<Contact> weardList;
    private static Context context;
    private final String topics_key = "topics";
    SwipeRefreshLayout swipeLayout;
    RecyclerView.LayoutManager layoutManager;
    Call<NewsCategoryList> call;
    TextView pakistan_head1, pakistan_head2, global_head1, global_head2, editor_head1, editor_head2, sports_head1, sports_head2,
            enter_head1, enter_head2, bus_head1, bus_head2, health_head1, health_head2, life_head1, life_head2, social_head1, social_head2,
            sci_head1, sci_head2, weird_head1, weird_head2;
    TextView more_pak, more_glo, more_enter, more_bus, more_editor, more_sports,
            more_health, more_life, more_social, more_sci, more_weird, firstcat_tv;
    RecyclerView pakistan_recycle, world_recycle, sports_recycle, edchoice_recycle, enter_recycle, business_recycle,
            health_recycle, life_recycle, social_recyle, sci_recycle, weird_recycle;
    //     private static final String myNewsURL ="http://app.samaa.tv/wordpressmu/urdu/my_news/?time=0&category=";
    Button add_cat_btn;
    Set<String> news_topics;
    ArrayList<String> news_ts;
    String URL;
    SharedPreferences Preferences1;
    Call<ObjectNewsCategoryList> call_new;
    NewsCategoryList tempList;
    LinearLayout pakLayout, globalLayout, enterLayout, editLayout, sportsLayout, econLayout,
            healthLayout, lifeLayout, sciLayout, socialLayout, weirdLayout;
    ScrollView scrollView;
    RelativeLayout cat_ad_head;
    String[] topics_cat;
    private MyNewsAdapterUrdu pakistan_adapter, world_adapter, sports_adapter, enter_adapter, edchoice_adapter, business_adapter,
            health_adapter, life_adapter, social_adapter, scitech_adapter, weird_adapter;
    //RecyclerView pak_recycle;
    private ListingsAdapter pak_adapter;
    private ArrayList<Contact> pak_list;
    private View parentView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String TAG = PakistanNews.class.getSimpleName();

    public ByCategoryNewsUrdu()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity();
        tr = getFragmentManager().beginTransaction();

//        populateListUrdu();
    }

    private void checkVisibleViews()
    {

        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);

        if (pakLayout.getVisibility() == View.VISIBLE || globalLayout.getVisibility() == View.VISIBLE
                || editLayout.getVisibility() == View.VISIBLE || sportsLayout.getVisibility() == View.VISIBLE
                || enterLayout.getVisibility() == View.VISIBLE || econLayout.getVisibility() == View.VISIBLE
                || healthLayout.getVisibility() == View.VISIBLE || lifeLayout.getVisibility() == View.VISIBLE
                || sciLayout.getVisibility() == View.VISIBLE || socialLayout.getVisibility() == View.VISIBLE
                || weirdLayout.getVisibility() == View.VISIBLE)
        {

            addcat_layout.setVisibility(View.GONE);

        }
        else
        {
            addcat_layout.setVisibility(View.VISIBLE);
        }
    }

    private void populateListUrdu()
    {
        Preferences1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        news_topics = Preferences1.getStringSet(topics_key, new HashSet<String>());

        topics_cat = news_topics.toArray(new String[news_topics.size()]);
        String convert = Arrays.toString(topics_cat);

        convert = convert.replaceAll("\\[|\\]", "");
        convert = convert.replace(" ", "");
        URL = myNewsURL + convert.trim();

        if (InternetConnection.checkConnection(context))
        {
            // webView.loadUrl(URL);
            final ProgressDialog dialog;
            dialog = new ProgressDialog(context);
            try
            {
                dialog.setTitle("SAMAA TV News");
                dialog.setMessage("Loading SAMAA TV News");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            catch (NullPointerException ne)
            {
                Log.e("NPE", "null pointer ka bekar exception", ne);
            }

            //Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            call_new = api.getMyNewsObj(URL);
            call_new.enqueue(new Callback<ObjectNewsCategoryList>()
            {
                @Override
                public void onResponse(Call<ObjectNewsCategoryList> call, Response<ObjectNewsCategoryList> response)
                {
                    if (response.isSuccessful())
                    {

                        tempList = response.body().getMy_news();

                        if (tempList != null)
                        {
                            nationList = tempList.getNational();
                            sportsList = tempList.getSports();
                            editorList = tempList.getEditorChoice();
                            enterList = tempList.getEntertainment();
                            economyList = tempList.getEconomy();
                            globalList = tempList.getGlobal();

                            healthList = tempList.getHealthBlogs();
                            lifestyleList = tempList.getLifestyleMyNews();
                            socialList = tempList.getSocialMyNews();
                            scitechList = tempList.getSciTechMyNews();
                            weardList = tempList.getWeirdMyNews();

                            forNationList();
                            forSportsList();
                            forEditorsList();
                            forEntertainmentList();
                            forEconomyList();
                            forGlobalList();

                            forHealthList();
                            forLifeList();
                            forSocialList();
                            forSciTechList();
                            forWeirdList();

                            if (dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                        }
                        else
                        {
                            tempList = new NewsCategoryList();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ObjectNewsCategoryList> call, Throwable t)
                {
                    if (dialog.isShowing())
                    {
                        dialog.dismiss();
                    }
                    if (t instanceof NullPointerException)
                    {
                        Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InternalError)
                    {
                        Toast.makeText(context, "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof InterruptedException)
                    {
                        Toast.makeText(context, "Sorry , Time Out !", Toast.LENGTH_SHORT).show();
                    }
                    if (t instanceof IllegalStateException)
                    {
                        Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(context, "Internet Connection Not Available !", Toast.LENGTH_SHORT).show();
        }

    }

    private void forWeirdList()
    {
        if (weardList != null && news_topics.contains("weird"))
        {
            weirdLayout.setVisibility(View.VISIBLE);
            weird_adapter = new MyNewsAdapterUrdu(context, weardList);
            weird_recycle.setAdapter(weird_adapter);
            weird_adapter.notifyDataSetChanged();
        }
        else
        {
            weirdLayout.setVisibility(View.GONE);
        }
    }

    private void forSciTechList()
    {
        if (scitechList != null && news_topics.contains("scitech"))
        {
            sciLayout.setVisibility(View.VISIBLE);
            scitech_adapter = new MyNewsAdapterUrdu(context, scitechList);
            sci_recycle.setAdapter(scitech_adapter);
            scitech_adapter.notifyDataSetChanged();
        }
        else
        {
            sciLayout.setVisibility(View.GONE);
        }
    }

    private void forSocialList()
    {
        if (socialList != null && news_topics.contains("social"))
        {
            socialLayout.setVisibility(View.VISIBLE);
            social_adapter = new MyNewsAdapterUrdu(context, socialList);
            social_recyle.setAdapter(scitech_adapter);
            social_adapter.notifyDataSetChanged();
        }
        else
        {
            socialLayout.setVisibility(View.GONE);
        }
    }

    private void forLifeList()
    {
        if (lifestyleList != null && news_topics.contains("lifestyle"))
        {
            lifeLayout.setVisibility(View.VISIBLE);
            life_adapter = new MyNewsAdapterUrdu(context, lifestyleList);
            life_recycle.setAdapter(life_adapter);
            life_adapter.notifyDataSetChanged();
        }
        else
        {
            lifeLayout.setVisibility(View.GONE);
        }
    }

    private void forHealthList()
    {
        if (healthList != null && news_topics.contains("Health"))
        {
            healthLayout.setVisibility(View.VISIBLE);
            health_adapter = new MyNewsAdapterUrdu(context, healthList);
            health_recycle.setAdapter(health_adapter);
            health_adapter.notifyDataSetChanged();
        }
        else
        {
            healthLayout.setVisibility(View.GONE);
        }
    }


    private void forGlobalList()
    {
        if (globalList != null && news_topics.contains("Global"))
        {
            globalLayout.setVisibility(View.VISIBLE);
            world_adapter = new MyNewsAdapterUrdu(context, globalList, "Global");
            world_recycle.setAdapter(world_adapter);
            world_adapter.notifyDataSetChanged();
        }
        else
        {
            globalLayout.setVisibility(View.GONE);
        }
    }

    private void forEconomyList()
    {
        if (economyList != null && news_topics.contains("Economy"))
        {
            econLayout.setVisibility(View.VISIBLE);
            business_adapter = new MyNewsAdapterUrdu(context, economyList, "Economy");
            business_recycle.setAdapter(business_adapter);
            business_adapter.notifyDataSetChanged();
        }
        else
        {
            econLayout.setVisibility(View.GONE);
        }
    }

    private void forEntertainmentList()
    {
        if (enterList != null && news_topics.contains("Entertainment"))
        {
            enterLayout.setVisibility(View.VISIBLE);
            enter_adapter = new MyNewsAdapterUrdu(context, enterList, "Entertainment");
            enter_recycle.setAdapter(enter_adapter);
            enter_adapter.notifyDataSetChanged();
        }
        else
        {
            enterLayout.setVisibility(View.GONE);
        }
    }

    private void forEditorsList()
    {
        if (editorList != null && news_topics.contains("Editors_Choice"))
        {
            editLayout.setVisibility(View.VISIBLE);
            edchoice_adapter = new MyNewsAdapterUrdu(context, editorList);
            edchoice_recycle.setAdapter(edchoice_adapter);
            edchoice_adapter.notifyDataSetChanged();
        }
        else
        {
            editLayout.setVisibility(View.GONE);
        }
    }

    private void forSportsList()
    {
        if (sportsList != null && news_topics.contains("Sports"))
        {
            sportsLayout.setVisibility(View.VISIBLE);
            sports_adapter = new MyNewsAdapterUrdu(context, sportsList, "Sports");
            sports_recycle.setAdapter(sports_adapter);
            sports_adapter.notifyDataSetChanged();
        }
        else
        {
            sportsLayout.setVisibility(View.GONE);
        }
    }

    private void forNationList()
    {
        if (nationList != null && news_topics.contains("National"))
        {
            pakLayout.setVisibility(View.VISIBLE);
            pakistan_adapter = new MyNewsAdapterUrdu(context, nationList, "National");
            pakistan_recycle.setAdapter(pakistan_adapter);
            pakistan_adapter.notifyDataSetChanged();
        }
        else
        {
            pakLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        ViewGroup rootView = checkScreenAndSetLayout(inflater, container);
        return rootView;
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
            rootView = (ViewGroup) inflater.inflate(R.layout.bycategory_newsurdu_large, container, false);
        }
        else
        {
            // smaller device
            rootView = (ViewGroup) inflater.inflate(R.layout.bycategory_newsurdu, container, false);
        }
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.toolbar_layoutcolor),
                getResources().getColor(R.color.heading1_black),
                getResources().getColor(R.color.heading2_black),
                getResources().getColor(R.color.blue));

        frame_footer = (FrameLayout) view.findViewById(R.id.footer);
        parentView = view.findViewById(R.id.parent_view);

        nationList = new ArrayList<>();
        sportsList = new ArrayList<>();
        editorList = new ArrayList<>();
        enterList = new ArrayList<>();
        economyList = new ArrayList<>();
        globalList = new ArrayList<>();

        healthList = new ArrayList<>();
        lifestyleList = new ArrayList<>();
        socialList = new ArrayList<>();
        scitechList = new ArrayList<>();
        weardList = new ArrayList<>();

        pakLayout = (LinearLayout) view.findViewById(R.id.paklayout);
        globalLayout = (LinearLayout) view.findViewById(R.id.globallayout);
        editLayout = (LinearLayout) view.findViewById(R.id.editorlayout);
        sportsLayout = (LinearLayout) view.findViewById(R.id.sportslayout);
        enterLayout = (LinearLayout) view.findViewById(R.id.entertainmentlayout);
        econLayout = (LinearLayout) view.findViewById(R.id.economylayout);
        healthLayout = (LinearLayout) view.findViewById(R.id.healthlayout);
        lifeLayout = (LinearLayout) view.findViewById(R.id.lifelayout);
        sciLayout = (LinearLayout) view.findViewById(R.id.scilayout);
        socialLayout = (LinearLayout) view.findViewById(R.id.sociallayout);
        weirdLayout = (LinearLayout) view.findViewById(R.id.weirdlayout);
        addcat_layout = (LinearLayout) view.findViewById(R.id.add_cat_layout);
        scrollView = (ScrollView) view.findViewById(R.id.container);

        checkVisibleViews();

        pakistan_recycle = (RecyclerView) view.findViewById(R.id.list_pak);
        world_recycle = (RecyclerView) view.findViewById(R.id.list_glo);
        sports_recycle = (RecyclerView) view.findViewById(R.id.list_spo);
        enter_recycle = (RecyclerView) view.findViewById(R.id.list_enter);
        business_recycle = (RecyclerView) view.findViewById(R.id.list_bus);
        edchoice_recycle = (RecyclerView) view.findViewById(R.id.editor_view);
        health_recycle = (RecyclerView) view.findViewById(R.id.list_health);
        life_recycle = (RecyclerView) view.findViewById(R.id.list_life);
        social_recyle = (RecyclerView) view.findViewById(R.id.list_social);
        sci_recycle = (RecyclerView) view.findViewById(R.id.list_sci);
        weird_recycle = (RecyclerView) view.findViewById(R.id.list_weird);

        pakistan_head1 = (TextView) view.findViewById(R.id.pakistan_head1);
        pakistan_head2 = (TextView) view.findViewById(R.id.pakistan_head2);

        global_head1 = (TextView) view.findViewById(R.id.global_head1);
        global_head2 = (TextView) view.findViewById(R.id.global_head2);

        editor_head1 = (TextView) view.findViewById(R.id.editor_head1);
        editor_head2 = (TextView) view.findViewById(R.id.editor_head2);

        sports_head1 = (TextView) view.findViewById(R.id.sports_head1);
        sports_head2 = (TextView) view.findViewById(R.id.sports_head2);


        enter_head1 = (TextView) view.findViewById(R.id.enter_head1);
        enter_head2 = (TextView) view.findViewById(R.id.enter_head2);

        bus_head1 = (TextView) view.findViewById(R.id.bus_head1);
        bus_head2 = (TextView) view.findViewById(R.id.bus_head2);

        health_head1 = (TextView) view.findViewById(R.id.healthhead1);
        health_head2 = (TextView) view.findViewById(R.id.healthhead2);

        life_head1 = (TextView) view.findViewById(R.id.lifehead1);
        life_head2 = (TextView) view.findViewById(R.id.lifehead2);

        social_head1 = (TextView) view.findViewById(R.id.socialhead1);
        social_head2 = (TextView) view.findViewById(R.id.socialhead2);

        sci_head1 = (TextView) view.findViewById(R.id.scihead1);
        sci_head2 = (TextView) view.findViewById(R.id.scihead2);

        weird_head1 = (TextView) view.findViewById(R.id.weirdhead1);
        weird_head2 = (TextView) view.findViewById(R.id.weirdhead2);

        add_cat_btn = (Button) view.findViewById(R.id.add_cat);
        firstcat_tv = (TextView) view.findViewById(R.id.firstcat_tv);

        applyBoldColorToWords();
        callAddCategoryScreen();
        applyFontsToHeaders();

        mAdView = (PublisherAdView) view.findViewById(R.id.ad_view_head);
        mAdView1 = (PublisherAdView) view.findViewById(R.id.ad_view_foot);

        cat_ad_head = (RelativeLayout) view.findViewById(R.id.cat_ad_head);

        setAd_load();
//            mAdView = (PublisherAdView) view.findViewById(R.id.ad_view_pak);
//            mAdView1 = (PublisherAdView) view.findViewById(R.id.ad_view_global);
//            mAdView2 = (PublisherAdView) view.findViewById(R.id.ad_view_editor);
//            mAdViewsport = (PublisherAdView) view.findViewById(R.id.ad_view_sport);
//            mAdViewenter = (PublisherAdView) view.findViewById(R.id.ad_view_entertainment);
//            mAdViewecon = (PublisherAdView) view.findViewById(R.id.ad_view_economy);
//            mAdViewhealth = (PublisherAdView) view.findViewById(R.id.ad_view_health);
//            mAdViewlife = (PublisherAdView) view.findViewById(R.id.ad_view_life);
//            mAdViewsci = (PublisherAdView) view.findViewById(R.id.ad_view_sci);
//            mAdViewsocial = (PublisherAdView) view.findViewById(R.id.ad_view_social);
//            mAdViewweird = (PublisherAdView) view.findViewById(R.id.ad_view_weird);

        more_pak = (TextView) view.findViewById(R.id.more_pak);
        more_glo = (TextView) view.findViewById(R.id.more_glo);
        more_enter = (TextView) view.findViewById(R.id.more_enter);
        more_editor = (TextView) view.findViewById(R.id.more_edit);
        more_bus = (TextView) view.findViewById(R.id.more_bus);
        more_sports = (TextView) view.findViewById(R.id.more_sport);
        more_health = (TextView) view.findViewById(R.id.more_health);
        more_life = (TextView) view.findViewById(R.id.more_life);
        more_social = (TextView) view.findViewById(R.id.more_social);
        more_sci = (TextView) view.findViewById(R.id.more_sci);
        more_weird = (TextView) view.findViewById(R.id.more_weird);

        more_pak.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                MainActivityUrdu.viewPager.setCurrentItem(8);
//                getActivity().getActionBar().setSelectedNavigationItem(2);
            }
        });

        more_glo.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                MainActivityUrdu.viewPager.setCurrentItem(7);
//                getActivity().getActionBar().setSelectedNavigationItem(2);
            }
        });

        more_enter.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                MainActivityUrdu.viewPager.setCurrentItem(4);
//                getActivity().getActionBar().setSelectedNavigationItem(2);
            }
        });

        more_editor.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                MainActivityUrdu.viewPager.setCurrentItem(3);
//                getActivity().getActionBar().setSelectedNavigationItem(2);
            }
        });

        more_bus.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                MainActivityUrdu.viewPager.setCurrentItem(6);
//                getActivity().getActionBar().setSelectedNavigationItem(2);
            }
        });

        more_sports.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                MainActivityUrdu.viewPager.setCurrentItem(5);
//                getActivity().getActionBar().setSelectedNavigationItem(2);
            }
        });


        more_health.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Bundle sendHealth = new Bundle();
                sendHealth.putInt("HealthValue", 1);
                MoreFeatures moreFragment = new MoreFeatures();
                moreFragment.setArguments(sendHealth);
                MainActivityUrdu.viewPager.setCurrentItem(0);
            }
        });
        more_life.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Bundle sendLife = new Bundle();
                sendLife.putInt("LifeValue", 2);
                MoreFeatures moreFragment = new MoreFeatures();
                moreFragment.setArguments(sendLife);
                MainActivityUrdu.viewPager.setCurrentItem(0);
            }
        });
        more_social.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Bundle sendSocial = new Bundle();
                sendSocial.putInt("SocialValue", 3);
                MoreFeatures moreFragment = new MoreFeatures();
                moreFragment.setArguments(sendSocial);
                MainActivityUrdu.viewPager.setCurrentItem(0);
            }
        });
        more_sci.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Bundle sendSci = new Bundle();
                sendSci.putInt("SciValue", 4);
                MoreFeatures moreFragment = new MoreFeatures();
                moreFragment.setArguments(sendSci);
                MainActivityUrdu.viewPager.setCurrentItem(0);
            }
        });
        more_weird.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Bundle sendWeird = new Bundle();
                sendWeird.putInt("WeirdValue", 5);
                MoreFeatures moreFragment = new MoreFeatures();
                moreFragment.setArguments(sendWeird);
                MainActivityUrdu.viewPager.setCurrentItem(0);
            }
        });

        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager3 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager4 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager5 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager6 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager7 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager8 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager9 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager10 = new GridLayoutManager(getActivity(), 2);

        LinearLayoutManager layoutManager11 = new GridLayoutManager(getActivity(), 2);


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

        //health
        health_recycle.setLayoutManager(layoutManager7);
        health_recycle.setNestedScrollingEnabled(false);
        health_recycle.setHasFixedSize(true);

        //life
        life_recycle.setLayoutManager(layoutManager8);
        life_recycle.setNestedScrollingEnabled(false);
        life_recycle.setHasFixedSize(true);

        //social
        social_recyle.setLayoutManager(layoutManager9);
        social_recyle.setNestedScrollingEnabled(false);
        social_recyle.setHasFixedSize(true);

        // sci tech
        sci_recycle.setLayoutManager(layoutManager10);
        sci_recycle.setNestedScrollingEnabled(false);
        sci_recycle.setHasFixedSize(true);

        //weird
        weird_recycle.setLayoutManager(layoutManager11);
        weird_recycle.setNestedScrollingEnabled(false);
        weird_recycle.setHasFixedSize(true);
    }

    private void setAd_load()
    {
        PublisherAdView mPublisherAdView = new PublisherAdView(getActivity());
        mPublisherAdView.setAdSizes(AdSize.BANNER, new AdSize(300, 50), new AdSize(300, 100), new AdSize(320, 50), new AdSize(320, 100));
        mPublisherAdView.setAdUnitId("/14309701/and-ur/mynews/and-ur.mynews.mrec-1");
        PublisherAdRequest.Builder publisherAdRequestBuilder = new PublisherAdRequest.Builder();
        cat_ad_head.addView(mPublisherAdView);
        mPublisherAdView.loadAd(publisherAdRequestBuilder.build());
    }

    private void callAddCategoryScreen()
    {
        //Open Add News Topics Activity
        add_cat_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Category_Add_Urdu.class);
                startActivity(intent);
            }
        });
    }

    private void applyBoldColorToWords()
    {

        SpannableStringBuilder sb = new SpannableStringBuilder("آپ کے منتخب کردہ زمروں کی تمام تازہ ترین خبریں سننے میں آئیں گی");
        StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        sb.setSpan(bss, 6, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
        sb.setSpan(new ForegroundColorSpan(Color.BLACK), 6, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        firstcat_tv.setText(sb);
    }

    private void applyFontsToHeaders()
    {
        //Applying two fonts for news headings
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
        Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");

        pakistan_head1.setTypeface(face);
        pakistan_head2.setTypeface(face1);

        global_head1.setTypeface(face);
        global_head2.setTypeface(face1);

        editor_head1.setTypeface(face);
        editor_head2.setTypeface(face1);

        sports_head1.setTypeface(face);
        sports_head2.setTypeface(face1);

        enter_head1.setTypeface(face);
        enter_head2.setTypeface(face1);

        bus_head1.setTypeface(face);
        bus_head2.setTypeface(face1);

        health_head1.setTypeface(face);
        health_head2.setTypeface(face1);

        life_head1.setTypeface(face);
        life_head2.setTypeface(face1);

        social_head1.setTypeface(face);
        social_head2.setTypeface(face1);

        sci_head1.setTypeface(face);
        sci_head2.setTypeface(face1);

        weird_head1.setTypeface(face);
        weird_head2.setTypeface(face1);
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

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();

        Log.d("TAGGG","ONRESUME");
        SamaaAppAnalytics.getInstance().trackScreenView("Category News Selection Screen Urdu");
        SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "Category News Selection Screen Urdu");
        checkVisibleViews();
        populateListUrdu();
    }

    public void NewsRefresh()
    {
        populateListUrdu();
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh()
    {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        checkVisibleViews();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {
            Log.d("TAGGG","USER VISIBLE");

            PublishAds.loadAd(mAdView);
            PublishAds.loadAd(mAdView1);
//            PublishAds.loadAd(mAdView2);
//            PublishAds.loadAd(mAdViewsport);
//            PublishAds.loadAd(mAdViewenter);
//            PublishAds.loadAd(mAdViewecon);
//            PublishAds.loadAd(mAdViewhealth);
//            PublishAds.loadAd(mAdViewlife);
//            PublishAds.loadAd(mAdViewsci);
//            PublishAds.loadAd(mAdViewsocial);
//            PublishAds.loadAd(mAdViewweird);

            checkVisibleViews();
            populateListUrdu();
        }
    }


}
