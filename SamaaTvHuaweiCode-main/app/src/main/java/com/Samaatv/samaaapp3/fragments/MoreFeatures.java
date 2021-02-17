package com.Samaatv.samaaapp3.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Samaatv.samaaapp3.Detail_Activity;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.adapter.HealthBlogAdapter;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.utils.DataCache;
import com.Samaatv.samaaapp3.utils.DevicePreference;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.PublishAds;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 4/13/2017.
 */
public class MoreFeatures extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    public static LinearLayout moreListLay;
    static FragmentTransaction tr;
    private static Context context;
    private static int healthPos, lifePos, socialPos, sciPos, weirdPos;
    private static String healthNewsCat;
    private static String lifeNewsCat;
    private static String socialNewsCat;
    private static String techNewsCat;
    private static String weirdNewsCat;
    ArrayList<Contact> healthList;
    ArrayList<Contact> lifeList;
    ArrayList<Contact> socialList;
    ArrayList<Contact> scitechList;
    ArrayList<Contact> weirdList;

    ProgressDialog dialog;
    private SwipeRefreshLayout swipeLayout;
    private LinearLayout healthLay, lifeLay, socialLay, sciLay, weirdLay;
    private TextView healthHead1, healthHead2, lifeHead1, lifeHead2, socialHead1, socialHead2,
            sciTechHead1, sciTechHead2, weirdHead1, weirdHead2;
    private RecyclerView listHealth, listLife, listSocial, listSci, listWeird;
    private PublisherAdView mAdView, mAdView1, mAdView2;
    private Button healthBtn, lifeBtn, socialBtn, sciBtn, weirdBtn;
    private Boolean isStarted = false;
    private Boolean isVisible = false;

    public MoreFeatures()
    {
    }

    @Override
    public void onStart()
    {
        super.onStart();

        isStarted = true;
        if (isVisible && isStarted)
        {
            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                fetchMoreIfNotif();
                DevicePreference.getInstance().setFromNotif(false);
            }
            else
            {
                fetchHealthPreference();
                fetchLifePref();
                fetchSocialPref();
                fetchSciPref();
                fetchWeirdPref();
            }
        }
    }

    private void fetchWeirdPref()
    {
        // for weird
        weirdList = DataCache.retrieveWeirdList(getActivity());
        if (weirdList != null)
        {
            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(weirdList, context);
            listWeird.setAdapter(hbAdapter);
            DevicePreference.getInstance().setmContext(getActivity());
            DevicePreference.getInstance().setWeirdList(weirdList);

            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                setData();    // from push
                DevicePreference.getInstance().setFromNotif(false);
            }
        }
        else
        {
            weirdList = new ArrayList<Contact>();
        }
    }

    private void fetchSciPref()
    {
        // for scitech
        scitechList = DataCache.retrieveSciList(getActivity());
        if (scitechList != null)
        {
            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(scitechList, context);
            listSci.setAdapter(hbAdapter);
            DevicePreference.getInstance().setmContext(getActivity());
            DevicePreference.getInstance().setTechList(scitechList);

            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                setData();    // from push
                DevicePreference.getInstance().setFromNotif(false);
            }
        }
        else
        {
            scitechList = new ArrayList<Contact>();
        }
    }

    private void fetchSocialPref()
    {
        // for social
        socialList = DataCache.retrieveSocialList(getActivity());
        if (socialList != null)
        {
            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(socialList, context);
            listSocial.setAdapter(hbAdapter);
            DevicePreference.getInstance().setmContext(getActivity());
            DevicePreference.getInstance().setSocialList(socialList);

            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                setData();    // from push
                DevicePreference.getInstance().setFromNotif(false);
            }
        }
        else
        {
            socialList = new ArrayList<Contact>();
        }
    }

    private void fetchLifePref()
    {
        // for lifestyle
        lifeList = DataCache.retrieveLifeList(getActivity());
        if (lifeList != null)
        {
            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(lifeList, context);
            listLife.setAdapter(hbAdapter);
            DevicePreference.getInstance().setmContext(getActivity());
            DevicePreference.getInstance().setTvList(lifeList);

            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                setData();    // from push
                DevicePreference.getInstance().setFromNotif(false);
            }
        }
        else
        {
            lifeList = new ArrayList<Contact>();
        }
    }

    private void fetchHealthPreference()
    {
        // for health
        healthList = DataCache.retrieveHealthList(getActivity());
        if (healthList != null)
        {
            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(healthList, context);
            listHealth.setAdapter(hbAdapter);
            hbAdapter.notifyDataSetChanged();
            DevicePreference.getInstance().setmContext(getActivity());
            DevicePreference.getInstance().setHealthList(healthList);

            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                setData();    // from push
                DevicePreference.getInstance().setFromNotif(false);
            }
        }
        else
        {
            healthList = new ArrayList<Contact>();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        context = getActivity();
        tr = getFragmentManager().beginTransaction();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        ViewGroup rootView = checkScreenAndSetLayout(inflater, container);

        DevicePreference.getInstance().setRootView(rootView);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeMoreContainer);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(R.color.toolbar_layoutcolor,
                R.color.heading1_black,
                R.color.heading2_black,
                R.color.blue);

        init(rootView);
        applyFontToHeads();
        layoutManagerSetup();
        PublishAds.loadAd(mAdView2);
        deActivateAllViews();
        selectionOfData();

        return rootView;
    }

    private void populationOfDataFromPush()
    {
        if (healthPos == 1)
        {
            moreListLay.setVisibility(View.GONE);
            activateHealth();
            deActivateLife();
            deActivateSocial();
            deActivateSci();
            deActivateWeird();
//            populateDataHealth();
        }
        if (lifePos == 2)
        {
            moreListLay.setVisibility(View.GONE);
            activateLife();
            deActivateHealth();
            deActivateSocial();
            deActivateSci();
            deActivateWeird();
//            populateDataLifeStyle();
        }
        if (socialPos == 3)
        {
            moreListLay.setVisibility(View.GONE);
            activateSocial();
            deActivateHealth();
            deActivateLife();
            deActivateSci();
            deActivateWeird();
//            populateDataSocial();
        }
        if (sciPos == 4)
        {
            moreListLay.setVisibility(View.GONE);
            activateSci();
            deActivateHealth();
            deActivateLife();
            deActivateSocial();
            deActivateWeird();
//            populateDataSciTech();
        }
        if (weirdPos == 5)
        {
            moreListLay.setVisibility(View.GONE);
            activateWeird();
            deActivateHealth();
            deActivateLife();
            deActivateSocial();
            deActivateSci();
//            populateDataWeird();
        }

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
            rootView = (ViewGroup) inflater.inflate(R.layout.more_features_layout_large, container, false);
        }
        else
        {
            // smaller device
            rootView = (ViewGroup) inflater.inflate(R.layout.more_features_layout, container, false);
        }
        return rootView;
    }


    private void selectionOfData()
    {

        healthBtn.setOnClickListener(new HealthActiveListner());
        lifeBtn.setOnClickListener(new LifeActiveListner());
        socialBtn.setOnClickListener(new SocialActiveListner());
        sciBtn.setOnClickListener(new SciTectActiveListner());
        weirdBtn.setOnClickListener(new WeirdActiveListner());
    }

    private void init(ViewGroup rootView)
    {

        healthLay = (LinearLayout) rootView.findViewById(R.id.framehealth);
        lifeLay = (LinearLayout) rootView.findViewById(R.id.framelife);
        socialLay = (LinearLayout) rootView.findViewById(R.id.framesocial);
        sciLay = (LinearLayout) rootView.findViewById(R.id.framesci);
        weirdLay = (LinearLayout) rootView.findViewById(R.id.frameweird);

        healthHead1 = (TextView) rootView.findViewById(R.id.health_head1);
        healthHead2 = (TextView) rootView.findViewById(R.id.health_head2);
        listHealth = (RecyclerView) rootView.findViewById(R.id.list_health);

        lifeHead1 = (TextView) rootView.findViewById(R.id.life_head1);
        lifeHead2 = (TextView) rootView.findViewById(R.id.life_head2);
        listLife = (RecyclerView) rootView.findViewById(R.id.list_lifestyle);

        socialHead1 = (TextView) rootView.findViewById(R.id.social_head1);
        socialHead2 = (TextView) rootView.findViewById(R.id.social_head2);
        listSocial = (RecyclerView) rootView.findViewById(R.id.list_socialbuzz);

        sciTechHead1 = (TextView) rootView.findViewById(R.id.sci_head1);
        sciTechHead2 = (TextView) rootView.findViewById(R.id.sci_head2);
        listSci = (RecyclerView) rootView.findViewById(R.id.list_scitech);

        weirdHead1 = (TextView) rootView.findViewById(R.id.weird_head1);
        weirdHead2 = (TextView) rootView.findViewById(R.id.weird_head2);
        listWeird = (RecyclerView) rootView.findViewById(R.id.list_weird);


        mAdView2 = (PublisherAdView) rootView.findViewById(R.id.ad_view3);

        moreListLay = (LinearLayout) rootView.findViewById(R.id.more_list_lyout);

        healthBtn = (Button) rootView.findViewById(R.id.btn_health);
        lifeBtn = (Button) rootView.findViewById(R.id.btn_life);
        socialBtn = (Button) rootView.findViewById(R.id.btn_social);
        sciBtn = (Button) rootView.findViewById(R.id.btn_sci);
        weirdBtn = (Button) rootView.findViewById(R.id.btn_weird);
    }


    private void applyFontToHeads()
    {
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "font/Heading1.ttf");
        Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(), "font/Heading2.ttf");

        healthHead1.setTypeface(face);
        healthHead2.setTypeface(face1);

        lifeHead1.setTypeface(face);
        lifeHead2.setTypeface(face1);

        socialHead1.setTypeface(face);
        socialHead2.setTypeface(face1);

        socialHead1.setTypeface(face);
        socialHead2.setTypeface(face1);

        sciTechHead1.setTypeface(face);
        sciTechHead2.setTypeface(face1);

        weirdHead1.setTypeface(face);
        weirdHead2.setTypeface(face1);

    }

    private void layoutManagerSetup()
    {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //RecyclerView for vertical layout
        listHealth.setLayoutManager(layoutManager);
        listHealth.setNestedScrollingEnabled(true);
//        listHealth.setHasFixedSize(true);

        listLife.setLayoutManager(layoutManager2);
        listLife.setNestedScrollingEnabled(true);
//        listLife.setHasFixedSize(true);

        listSocial.setLayoutManager(layoutManager3);
        listSocial.setNestedScrollingEnabled(true);
//        listSocial.setHasFixedSize(true);

        listSci.setLayoutManager(layoutManager4);
        listSci.setNestedScrollingEnabled(true);
//        listSci.setHasFixedSize(true);

        listWeird.setLayoutManager(layoutManager5);
        listWeird.setNestedScrollingEnabled(true);
//        listWeird.setHasFixedSize(true);

    }

    private void populateData()
    {

        if (InternetConnection.checkConnection(getActivity()))
        {
            populateDataHealth();
            populateDataLifeStyle();
            populateDataSocial();
            populateDataSciTech();
            populateDataWeird();
        }
    }

    private void populateDataHealth()
    {

        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getHealthBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        healthList = response.body().getHealthBlogs();
                        if (healthList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(healthList, context);
                            listHealth.setAdapter(hbAdapter);
                            DataCache.saveHealthList(getActivity(), healthList);
                            swipeLayout.setRefreshing(false);
                        }
                        else
                        {
                            healthList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void populateDataLifeStyle()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getLifeStyleBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        lifeList = response.body().getLifestyleblogs();
                        if (lifeList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(lifeList, context);
                            listLife.setAdapter(hbAdapter);
                            DataCache.saveLifeList(getActivity(), lifeList);
                            swipeLayout.setRefreshing(false);
                        }
                        else
                        {
                            lifeList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void populateDataSocial()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getSocialBuzzBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        socialList = response.body().getSocialBuzzBlogs();
                        if (socialList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(socialList, context);
                            listSocial.setAdapter(hbAdapter);
                            DataCache.saveSocialList(getActivity(), socialList);
                            swipeLayout.setRefreshing(false);
                        }
                        else
                        {
                            socialList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void populateDataSciTech()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getSciTechBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        scitechList = response.body().getSciTechBlogs();
                        if (scitechList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(scitechList, context);
                            listSci.setAdapter(hbAdapter);
                            DataCache.saveSciList(getActivity(), scitechList);
                            swipeLayout.setRefreshing(false);
                        }
                        else
                        {
                            scitechList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void populateDataWeird()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getWeirdBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        weirdList = response.body().getWeirdBlogs();
                        if (weirdList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(weirdList, context);
                            listWeird.setAdapter(hbAdapter);
                            DataCache.saveWeirdList(getActivity(), weirdList);
                            swipeLayout.setRefreshing(false);
                        }
                        else
                        {
                            weirdList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void setData()
    {
        populationOfDataFromPush();
        if (healthPos == 1)
        {
            if (DevicePreference.getInstance().getNewsCat() != null)
            {
                ArrayList<Contact> tempList = DevicePreference.getInstance().getHealthList();
                if (tempList != null)
                {
                    for (int i = 0; i < tempList.size(); i++)
                    {
                        if (String.valueOf(tempList.get(i).getId()).contains(DevicePreference.getInstance().getNewsCat()))
                        {
                            ArrayList<Contact> reqList = new ArrayList<Contact>();
                            reqList.add(tempList.get(i));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("array", reqList);
                            Intent intent = new Intent(DevicePreference.getInstance().getmContext(), Detail_Activity.class);
                            intent.putExtras(bundle);
                            intent.putExtra("pos", i);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            DevicePreference.getInstance().getmContext().startActivity(intent);
                        }
                    }
                }
            }
        }
        if (lifePos == 2)
        {
            if (DevicePreference.getInstance().getNewsCat() != null)
            {
                ArrayList<Contact> tempList = DevicePreference.getInstance().getTvList();
                if (tempList != null)
                {
                    for (int i = 0; i < tempList.size(); i++)
                    {
                        if (String.valueOf(tempList.get(i).getId()).contains(DevicePreference.getInstance().getNewsCat()))
                        {
                            ArrayList<Contact> reqList = new ArrayList<Contact>();
                            reqList.add(tempList.get(i));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("array", reqList);
                            Intent intent = new Intent(DevicePreference.getInstance().getmContext(), Detail_Activity.class);
                            intent.putExtras(bundle);
                            intent.putExtra("pos", i);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            DevicePreference.getInstance().getmContext().startActivity(intent);
                        }
                    }
                }
            }
        }
        if (socialPos == 3)
        {
            if (DevicePreference.getInstance().getNewsCat() != null)
            {
                ArrayList<Contact> tempList = DevicePreference.getInstance().getSocialList();
                if (tempList != null)
                {
                    for (int i = 0; i < tempList.size(); i++)
                    {
                        if (String.valueOf(tempList.get(i).getId()).contains(DevicePreference.getInstance().getNewsCat()))
                        {
                            ArrayList<Contact> reqList = new ArrayList<Contact>();
                            reqList.add(tempList.get(i));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("array", reqList);
                            Intent intent = new Intent(DevicePreference.getInstance().getmContext(), Detail_Activity.class);
                            intent.putExtras(bundle);
                            intent.putExtra("pos", i);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            DevicePreference.getInstance().getmContext().startActivity(intent);
                        }
                    }
                }
            }
        }
        if (sciPos == 4)
        {
            if (DevicePreference.getInstance().getNewsCat() != null)
            {
                ArrayList<Contact> tempList = DevicePreference.getInstance().getTechList();
                if (tempList != null)
                {
                    for (int i = 0; i < tempList.size(); i++)
                    {
                        if (String.valueOf(tempList.get(i).getId()).contains(DevicePreference.getInstance().getNewsCat()))
                        {
                            ArrayList<Contact> reqList = new ArrayList<Contact>();
                            reqList.add(tempList.get(i));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("array", reqList);
                            Intent intent = new Intent(DevicePreference.getInstance().getmContext(), Detail_Activity.class);
                            intent.putExtras(bundle);
                            intent.putExtra("pos", i);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            DevicePreference.getInstance().getmContext().startActivity(intent);
                        }
                    }
                }
            }
        }
        if (weirdPos == 5)
        {
            if (DevicePreference.getInstance().getNewsCat() != null)
            {
                ArrayList<Contact> tempList = DevicePreference.getInstance().getWeirdList();
                if (tempList != null)
                {
                    for (int i = 0; i < tempList.size(); i++)
                    {
                        if (String.valueOf(tempList.get(i).getId()).contains(DevicePreference.getInstance().getNewsCat()))
                        {
                            ArrayList<Contact> reqList = new ArrayList<Contact>();
                            reqList.add(tempList.get(i));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("array", reqList);
                            Intent intent = new Intent(DevicePreference.getInstance().getmContext(), Detail_Activity.class);
                            intent.putExtras(bundle);
                            intent.putExtra("pos", i);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            DevicePreference.getInstance().getmContext().startActivity(intent);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onRefresh()
    {
        populateData();
    }

    private void activateHealth()
    {
        if (healthLay != null && listHealth != null)
        {
            healthLay.setVisibility(View.VISIBLE);
            listHealth.setVisibility(View.VISIBLE);
        }
    }

    private void deActivateHealth()
    {
        if (healthLay != null && listHealth != null)
        {
            healthLay.setVisibility(View.GONE);
            listHealth.setVisibility(View.GONE);
        }
    }

    private void activateLife()
    {
        if (lifeLay != null && listLife != null)
        {
            lifeLay.setVisibility(View.VISIBLE);
            listLife.setVisibility(View.VISIBLE);
        }
    }

    private void deActivateLife()
    {
        if (lifeLay != null && listLife != null)
        {
            lifeLay.setVisibility(View.GONE);
            listLife.setVisibility(View.GONE);
        }
    }

    private void activateSocial()
    {
        if (socialLay != null && listSocial != null)
        {
            socialLay.setVisibility(View.VISIBLE);
            listSocial.setVisibility(View.VISIBLE);
        }
    }

    private void deActivateSocial()
    {
        if (socialLay != null && listSocial != null)
        {
            socialLay.setVisibility(View.GONE);
            listSocial.setVisibility(View.GONE);
        }
    }

    private void activateSci()
    {
        if (sciLay != null && listSci != null)
        {
            sciLay.setVisibility(View.VISIBLE);
            listSci.setVisibility(View.VISIBLE);
        }
    }

    private void deActivateSci()
    {
        if (sciLay != null && listSci != null)
        {
            sciLay.setVisibility(View.GONE);
            listSci.setVisibility(View.GONE);
        }
    }

    private void activateWeird()
    {
        if (weirdLay != null && listWeird != null)
        {
            weirdLay.setVisibility(View.VISIBLE);
            listWeird.setVisibility(View.VISIBLE);
        }
    }

    private void deActivateWeird()
    {
        if (weirdLay != null && listWeird != null)
        {
            weirdLay.setVisibility(View.GONE);
            listWeird.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        SamaaAppAnalytics.getInstance().trackScreenView("More Features News English");
        SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "More Features News English");
        deActivateAllViews();
    }

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
        healthPos = args.getInt("HealthValue");
        lifePos = args.getInt("LifeValue");
        socialPos = args.getInt("SocialValue");
        sciPos = args.getInt("SciValue");
        weirdPos = args.getInt("WeirdValue");

        healthNewsCat = args.getString("healthnews");
        if (healthNewsCat != null)
        {
            DevicePreference.getInstance().setNewsCat(healthNewsCat);
        }
        lifeNewsCat = args.getString("lifenews");
        if (lifeNewsCat != null)
        {
            DevicePreference.getInstance().setNewsCat(lifeNewsCat);
        }
        socialNewsCat = args.getString("socialnews");
        if (socialNewsCat != null)
        {
            DevicePreference.getInstance().setNewsCat(socialNewsCat);
        }
        techNewsCat = args.getString("technews");
        if (techNewsCat != null)
        {
            DevicePreference.getInstance().setNewsCat(techNewsCat);
        }
        weirdNewsCat = args.getString("weirdnews");
        if (weirdNewsCat != null)
        {
            DevicePreference.getInstance().setNewsCat(weirdNewsCat);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;

        if (isVisible && isStarted)
        {
            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                fetchMoreIfNotif();
                DevicePreference.getInstance().setFromNotif(false);
            }
            else
            {
                fetchHealthPreference();
                fetchLifePref();
                fetchSocialPref();
                fetchSciPref();
                fetchWeirdPref();
            }
        }
    }

    private void fetchMoreIfNotif()
    {

        if (InternetConnection.checkConnection(getActivity()))
        {
            populateDataHealthNotif();
            populateDataLifeStyleNotif();
            populateDataSocialNotif();
            populateDataSciTechNotif();
            populateDataWeirdNotif();
        }
    }

    private void populateDataWeirdNotif()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getWeirdBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        weirdList = response.body().getWeirdBlogs();
                        if (weirdList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(weirdList, context);
                            listWeird.setAdapter(hbAdapter);
                            DevicePreference.getInstance().setmContext(getActivity());
                            DevicePreference.getInstance().setWeirdList(weirdList);

                            setData();  // from push
                        }
                        else
                        {
                            weirdList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void populateDataSciTechNotif()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getSciTechBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        scitechList = response.body().getSciTechBlogs();
                        if (scitechList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(scitechList, context);
                            listSci.setAdapter(hbAdapter);
                            DevicePreference.getInstance().setmContext(getActivity());
                            DevicePreference.getInstance().setTechList(scitechList);

                            setData();  // from push
                        }
                        else
                        {
                            scitechList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void populateDataSocialNotif()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getSocialBuzzBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        socialList = response.body().getSocialBuzzBlogs();
                        if (socialList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(socialList, context);
                            listSocial.setAdapter(hbAdapter);
                            DevicePreference.getInstance().setmContext(getActivity());
                            DevicePreference.getInstance().setSocialList(socialList);

                            setData();  // from push
                        }
                        else
                        {
                            socialList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void populateDataLifeStyleNotif()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getLifeStyleBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        lifeList = response.body().getLifestyleblogs();
                        if (lifeList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(lifeList, context);
                            listLife.setAdapter(hbAdapter);
                            DevicePreference.getInstance().setmContext(getActivity());
                            DevicePreference.getInstance().setTvList(lifeList);

                            setData();  // from push
                        }
                        else
                        {
                            lifeList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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

    private void populateDataHealthNotif()
    {
        ApiService api = RetroClient.getApiService();
        Call<NewsCategoryList> call = api.getHealthBlogs();
        call.enqueue(new Callback<NewsCategoryList>()
        {
            @Override
            public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        healthList = response.body().getHealthBlogs();
                        if (healthList != null)
                        {
                            HealthBlogAdapter hbAdapter = new HealthBlogAdapter(healthList, context);
                            listHealth.setAdapter(hbAdapter);
                            DevicePreference.getInstance().setmContext(getActivity());
                            DevicePreference.getInstance().setHealthList(healthList);

                            setData();  // from push
                        }
                        else
                        {
                            healthList = new ArrayList<Contact>();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryList> call, Throwable t)
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


    private void deActivateAllViews()
    {
        moreListLay.setVisibility(View.VISIBLE);
        deActivateHealth();
        deActivateLife();
        deActivateSocial();
        deActivateSci();
        deActivateWeird();
    }

    private class HealthActiveListner implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {

            moreListLay.setVisibility(View.GONE);
            activateHealth();
            deActivateLife();
            deActivateSocial();
            deActivateSci();
            deActivateWeird();
            fetchHealthPreference();
//            populateDataHealth();
        }
    }

    private class LifeActiveListner implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            moreListLay.setVisibility(View.GONE);
            activateLife();
            deActivateHealth();
            deActivateSocial();
            deActivateSci();
            deActivateWeird();
            fetchLifePref();
//            populateDataLifeStyle();
        }
    }

    private class SocialActiveListner implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            moreListLay.setVisibility(View.GONE);
            activateSocial();
            deActivateHealth();
            deActivateLife();
            deActivateSci();
            deActivateWeird();
            fetchSocialPref();
//            populateDataSocial();
        }
    }

    private class SciTectActiveListner implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            moreListLay.setVisibility(View.GONE);
            activateSci();
            deActivateHealth();
            deActivateLife();
            deActivateSocial();
            deActivateWeird();
            fetchSciPref();
//            populateDataSciTech();
        }
    }

    private class WeirdActiveListner implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            moreListLay.setVisibility(View.GONE);
            activateWeird();
            deActivateHealth();
            deActivateLife();
            deActivateSocial();
            deActivateSci();
            fetchWeirdPref();
//            populateDataWeird();
        }
    }
}
