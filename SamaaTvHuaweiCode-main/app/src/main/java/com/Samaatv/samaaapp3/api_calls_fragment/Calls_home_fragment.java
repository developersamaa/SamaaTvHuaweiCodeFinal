package com.Samaatv.samaaapp3.api_calls_fragment;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.Samaatv.samaaapp3.SplashScreen;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.ContactList;
import com.Samaatv.samaaapp3.model.ObjectNewsContactList;
import com.Samaatv.samaaapp3.utils.DataCache;
import com.Samaatv.samaaapp3.utils.InternetConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/24/2017.
 */
public class Calls_home_fragment
{
    static ContactList tempList;

    public static void callHomeList(final Context context)
    {

        if (InternetConnection.checkConnection(context))
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
                                DataCache.saveHomeList(context, tempList);
                            }
                            else
                            {
                                tempList = new ContactList();
                            }
                        }
                        else
                        {
                            relaunchTheApp(context);
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Something is wrong !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ObjectNewsContactList> call, Throwable t)
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
                    if (t instanceof IndexOutOfBoundsException)
                    {
                        Toast.makeText(context, "Oops , something went wrong !", Toast.LENGTH_SHORT).show();
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
