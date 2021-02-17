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

import com.Samaatv.samaaapp3.adapter.EditorsChoiceAdapterListing;
import com.Samaatv.samaaapp3.analytics.SamaaAppAnalytics;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.utils.DataCache;
import com.Samaatv.samaaapp3.utils.DevicePreference;
import com.Samaatv.samaaapp3.utils.InternetConnection;
import com.Samaatv.samaaapp3.utils.SamaaFirebaseAnalytics;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorsChoice extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    static final boolean GRID_LAYOUT = false;
    static FrameLayout frame_footer;
    SwipeRefreshLayout swipeLayout;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView editor_recycle;
    String editNewsCat;
    private EditorsChoiceAdapterListing editor_adapter;
    private ArrayList<Contact> editor_list;
    private View parentView;
    private Boolean isStarted = false;
    private Boolean isVisible = false;

    public EditorsChoice()
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
                fetchEditIfNotif();
                DevicePreference.getInstance().setFromNotif(false);
            }
            else
            {
                fetchEditListPreference();
            }
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recyclerview, container, false);
        return rootView;
    }

    private void setData()
    {
        if (DevicePreference.getInstance().getNewsCat() != null)
        {
            ArrayList<Contact> tempList = DevicePreference.getInstance().getEditList();
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        editor_recycle = (RecyclerView) view.findViewById(R.id.recyclerView);

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
        editor_recycle.setLayoutManager(layoutManager);
        editor_recycle.setHasFixedSize(true);

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
        editNewsCat = args.getString("editnews");
        DevicePreference.getInstance().setNewsCat(editNewsCat);
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
                SamaaAppAnalytics.getInstance().trackScreenView("Editor's Choice News English");
                SamaaFirebaseAnalytics.syncSamaaAnalytics(getActivity(), "Editor's Choice News English");
            }
        }, 6000);

    }

    @Override
    public void onRefresh()
    {
        fetchEditList();
    }

    private void fetchEditList()
    {

        if (InternetConnection.checkConnection(getActivity()))
        {

            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getEditorsNews();
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {

                    if (response.isSuccessful())
                    {

                        editor_list = response.body().getEditorChoice();

                        if (editor_list != null)
                        {

                            editor_adapter = new EditorsChoiceAdapterListing(getActivity(), editor_list);
                            editor_recycle.setAdapter(editor_adapter);
                            DataCache.saveEditList(getActivity(), editor_list);
                            swipeLayout.setRefreshing(false);
                        }
                        else
                        {
                            editor_list = new ArrayList<Contact>();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Something is wrong !", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "Sorry, internet connection is not available !", Toast.LENGTH_SHORT).show();
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
                fetchEditIfNotif();
                DevicePreference.getInstance().setFromNotif(false);
            }
            else
            {
                fetchEditListPreference();
            }
        }
    }

    private void fetchEditIfNotif()
    {


        if (InternetConnection.checkConnection(getActivity()))
        {

            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getEditorsNews();
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {

                    if (response.isSuccessful())
                    {

                        editor_list = response.body().getEditorChoice();

                        if (editor_list != null)
                        {

                            editor_adapter = new EditorsChoiceAdapterListing(getActivity(), editor_list);
                            editor_recycle.setAdapter(editor_adapter);
                            editor_adapter.notifyDataSetChanged();

                            DevicePreference.getInstance().setmContext(getActivity());
                            DevicePreference.getInstance().setEditList(editor_list);
                            setData();    // from push
                        }
                        else
                        {
                            editor_list = new ArrayList<Contact>();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Something is wrong !", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "Sorry, internet connection is not available !", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchEditListPreference()
    {

        editor_list = DataCache.retrieveEditList(getActivity());

        if (editor_list != null)
        {

            editor_adapter = new EditorsChoiceAdapterListing(getActivity(), editor_list);
            editor_recycle.setAdapter(editor_adapter);
            DevicePreference.getInstance().setmContext(getActivity());
            DevicePreference.getInstance().setEditList(editor_list);

            if (DevicePreference.getInstance().isFromNotif() == true)
            {
                setData();    // from push
                DevicePreference.getInstance().setFromNotif(false);
            }
        }
        else
        {
            editor_list = new ArrayList<Contact>();
        }

    }
}