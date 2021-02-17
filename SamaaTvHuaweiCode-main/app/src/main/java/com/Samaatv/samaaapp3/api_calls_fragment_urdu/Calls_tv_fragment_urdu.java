package com.Samaatv.samaaapp3.api_calls_fragment_urdu;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.Samaatv.samaaapp3.SplashScreen;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.utils.DataCacheUrdu;
import com.Samaatv.samaaapp3.utils.InternetConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/24/2017.
 */
public class Calls_tv_fragment_urdu
{

    static ArrayList<Contact> tv_list;

    public static void callTvList(final Context context)
    {
        if (InternetConnection.checkConnection(context))
        {

            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getTVShows();
            call.enqueue(new Callback<NewsCategoryList>()
            {
                @Override
                public void onResponse(Call<NewsCategoryList> call, Response<NewsCategoryList> response)
                {

                    if (response.isSuccessful())
                    {

                        tv_list = response.body().getPrograms();

                        if (tv_list != null)
                        {
                            DataCacheUrdu.saveTvList(context, tv_list);
                        }
                        else
                        {
                            tv_list = new ArrayList<Contact>();
                        }

                    }
                    else
                    {
                        relaunchTheApp(context);
                        Toast.makeText(context, "Something is wrong !", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<NewsCategoryList> call, Throwable t)
                {

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
            Toast.makeText(context, "Sorry, internet connection is not available !", Toast.LENGTH_LONG).show();
        }

    }

    private static void relaunchTheApp(Context context)
    {
        Intent reLaunchIntent = new Intent(context, SplashScreen.class);
        context.startActivity(reLaunchIntent);
    }
}
