package com.Samaatv.samaaapp3.api_calls_fragment;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.Samaatv.samaaapp3.SplashScreen;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.ContactList;
import com.Samaatv.samaaapp3.utils.DataCache;
import com.Samaatv.samaaapp3.utils.InternetConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/14/2017.
 */
public class Calls_live_fragment
{

    static ArrayList<Contact> mostWatch_list;

    public static void callLiveMostWatched(final Context context)
    {

        if (InternetConnection.checkConnection(context))
        {

            ApiService api = RetroClient.getApiService();
            Call<ContactList> call = api.getWebAPI();
            call.enqueue(new Callback<ContactList>()
            {
                @Override
                public void onResponse(Call<ContactList> call, Response<ContactList> response)
                {
                    if (response.isSuccessful())
                    {

                        if (response.body() != null)
                        {
                            mostWatch_list = response.body().getMostWatched();
                            if (mostWatch_list != null)
                            {

                                DataCache.saveMostWatched(context, mostWatch_list);
                            }
                            else
                            {
                                mostWatch_list = new ArrayList<Contact>();
                            }
                        }
                        else
                        {
                            Toast.makeText(context, "Oops, Something went wrong !", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        relaunchTheApp(context);
                        Toast.makeText(context, "Something is wrong !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ContactList> call, Throwable t)
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
            Toast.makeText(context, "Internet Connection Not Available !", Toast.LENGTH_SHORT).show();
        }
    }

    private static void relaunchTheApp(Context context)
    {
        Intent reLaunchIntent = new Intent(context, SplashScreen.class);
        context.startActivity(reLaunchIntent);
    }
}
