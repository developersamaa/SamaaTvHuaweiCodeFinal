package com.Samaatv.samaaapp3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.Samaatv.samaaapp3.adapter.ListingsAdapterUrdu;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.utils.DataCacheUrdu;
import com.Samaatv.samaaapp3.utils.DevicePreference;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusinessNewsUrdu extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    static final boolean GRID_LAYOUT = false;
    static FrameLayout frame_footer;
    SwipeRefreshLayout swipeLayout;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView business_recycle;
    private ListingsAdapterUrdu business_adapter;
    private ArrayList<Contact> business_list;
    private View parentView;
    private String TAG = PakistanNews.class.getSimpleName();

    private String econNewsCat;

    private Boolean isStarted = false;
    private Boolean isVisible = false;

    public BusinessNewsUrdu()
    {
        // Required empty public constructor
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
                fetchEconIfNotif();
                DevicePreference.getInstance().setFromNotif(false);
            }
            else
            {
                fetchEconListPreference();
            }
        }
    }

    private void fetchEconListPreference()
    {

        business_list = DataCacheUrdu.retrieveEconomyList(getActivity());

        if (business_list != null)
        {
            business_adapter = new ListingsAdapterUrdu(getActivity(), business_list, "Economy");
            business_recycle.setAdapter(business_adapter);

            DevicePreference.getInstance().setmContext(getActivity());
            DevicePreference.getInstance().setEconList(business_list);

            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                setData();    // from push
                DevicePreference.getInstance().setFromNotif(false);
            }
        }
        else
        {
            business_list = new ArrayList<Contact>();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //MainActivity.frameticker.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recyclerview, container, false);
        return rootView;

    }

    private void setData()
    {
        if (DevicePreference.getInstance().getNewsCat() != null)
        {
            ArrayList<Contact> tempList = DevicePreference.getInstance().getEconList();
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
                        Intent intent = new Intent(DevicePreference.getInstance().getmContext(), Detail_Activity_Urdu.class);
                        intent.putExtras(bundle);
                        intent.putExtra("pos", i);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        DevicePreference.getInstance().getmContext().startActivity(intent);
                    }
                }
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        business_recycle = (RecyclerView) view.findViewById(R.id.recyclerView);

        //  MainActivity.mAdView.setVisibility(View.GONE);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeColors(R.color.toolbar_layoutcolor,
                R.color.heading1_black,
                R.color.heading2_black,
                R.color.blue);

        frame_footer = (FrameLayout) view.findViewById(R.id.footer);

        // new AsyncLoadXMLFeed().execute();

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        }
        else
        {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        business_recycle.setLayoutManager(layoutManager);
        business_recycle.setHasFixedSize(true);

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
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
        econNewsCat = args.getString("econnews");
        DevicePreference.getInstance().setNewsCat(econNewsCat);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SamaaAppAnalytics.getInstance().trackScreenView("Business News Urdu");
                SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "Business News Urdu");
            }
        }, 6000);

    }

    @Override
    public void onRefresh()
    {
        fetchEconList();
    }

    private void fetchEconList()
    {
        if (InternetConnection.checkConnection(getActivity()))
        {

            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getEconomyNewsUrdu();
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {

                    if (response.isSuccessful())
                    {

                        business_list = response.body().getEconomy();

                        if (business_list != null)
                        {
                            business_adapter = new ListingsAdapterUrdu(getActivity(), business_list, "Economy");
                            business_recycle.setAdapter(business_adapter);
                            DataCacheUrdu.saveEconomyList(getActivity(), business_list);
                            swipeLayout.setRefreshing(false);
                        }
                        else
                        {
                            business_list = new ArrayList<Contact>();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
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
        else
        {
            Toast.makeText(getActivity(), "No internet connection !", Toast.LENGTH_SHORT).show();
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
                fetchEconIfNotif();
                DevicePreference.getInstance().setFromNotif(false);
            }
            else
            {
                fetchEconListPreference();
            }
        }
    }

    private void fetchEconIfNotif()
    {

        if (InternetConnection.checkConnection(getActivity()))
        {

            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getEconomyNewsUrdu();
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {

                    if (response.isSuccessful())
                    {

                        business_list = response.body().getEconomy();

                        if (business_list != null)
                        {
                            business_adapter = new ListingsAdapterUrdu(getActivity(), business_list, "Economy");
                            business_recycle.setAdapter(business_adapter);
                            business_adapter.notifyDataSetChanged();

                            DevicePreference.getInstance().setmContext(getActivity());
                            DevicePreference.getInstance().setEconList(business_list);
                            setData();    // from push
                        }
                        else
                        {
                            business_list = new ArrayList<Contact>();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
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
        else
        {
            Toast.makeText(getActivity(), "No internet connection !", Toast.LENGTH_SHORT).show();
        }
    }
}