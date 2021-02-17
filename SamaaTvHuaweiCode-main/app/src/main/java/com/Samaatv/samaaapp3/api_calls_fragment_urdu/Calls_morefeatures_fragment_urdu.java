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
import com.Samaatv.samaaapp3.utils.DevicePreference;
import com.Samaatv.samaaapp3.utils.InternetConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/24/2017.
 */
public class Calls_morefeatures_fragment_urdu
{

    static ArrayList<Contact> healthList;
    static ArrayList<Contact> lifeList;
    static ArrayList<Contact> socialList;
    static ArrayList<Contact> scitechList;
    static ArrayList<Contact> weirdList;

    public static void callHealthList(final Context context)
    {

        if (InternetConnection.checkConnection(context))
        {
            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getHealthBlogDataUrdu();
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
                                DevicePreference.getInstance().setmContext(context);
                                DevicePreference.getInstance().setHealthList(healthList);
                                DataCacheUrdu.saveHealthList(context, healthList);
                            }
                            else
                            {
                                healthList = new ArrayList<Contact>();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Something is wrong", Toast.LENGTH_SHORT).show();
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


    public static void callLifeStyleList(final Context context)
    {

        if (InternetConnection.checkConnection(context))
        {
            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getLifeStyleBlogDataUrdu();
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
                                DevicePreference.getInstance().setmContext(context);
                                DevicePreference.getInstance().setTvList(lifeList);
                                DataCacheUrdu.saveLifeList(context, lifeList);
                            }
                            else
                            {
                                lifeList = new ArrayList<Contact>();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Something is wrong", Toast.LENGTH_SHORT).show();
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

    public static void callSocialList(final Context context)
    {

        if (InternetConnection.checkConnection(context))
        {
            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getSocialBuzzBlogDataUrdu();
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
                                DevicePreference.getInstance().setmContext(context);
                                DevicePreference.getInstance().setSocialList(socialList);
                                DataCacheUrdu.saveSocialList(context, socialList);
                            }
                            else
                            {
                                socialList = new ArrayList<Contact>();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Something is wrong", Toast.LENGTH_SHORT).show();
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

    public static void callSciList(final Context context)
    {

        if (InternetConnection.checkConnection(context))
        {
            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getSciTechBlogDataUrdu();
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
                                DevicePreference.getInstance().setmContext(context);
                                DevicePreference.getInstance().setTechList(scitechList);
                                DataCacheUrdu.saveSciList(context, scitechList);
                            }
                            else
                            {
                                scitechList = new ArrayList<Contact>();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Something is wrong", Toast.LENGTH_SHORT).show();
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

    public static void callWeirdList(final Context context)
    {

        if (InternetConnection.checkConnection(context))
        {
            ApiService api = RetroClient.getApiService();
            Call<NewsCategoryList> call = api.getWeirdBlogDataUrdu();
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
                                DevicePreference.getInstance().setmContext(context);
                                DevicePreference.getInstance().setWeirdList(weirdList);
                                DataCacheUrdu.saveWeirdList(context, weirdList);
                            }
                            else
                            {
                                weirdList = new ArrayList<Contact>();
                            }
                        }
                    }
                    else
                    {
                        relaunchTheApp(context);
                        Toast.makeText(context, "Something is wrong", Toast.LENGTH_SHORT).show();
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
