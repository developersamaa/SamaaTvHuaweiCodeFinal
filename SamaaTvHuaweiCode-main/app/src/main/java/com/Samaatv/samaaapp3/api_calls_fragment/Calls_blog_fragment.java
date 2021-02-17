package com.Samaatv.samaaapp3.api_calls_fragment;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.Samaatv.samaaapp3.SplashScreen;
import com.Samaatv.samaaapp3.api.ApiService;
import com.Samaatv.samaaapp3.api.RetroClient;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.Samaatv.samaaapp3.model.ObjectBlogsCategoryList;
import com.Samaatv.samaaapp3.utils.DataCache;
import com.Samaatv.samaaapp3.utils.InternetConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/23/2017.
 */
public class Calls_blog_fragment
{
    static NewsCategoryList tempList;

    public static void callBlogList(final Context context)
    {

        if (InternetConnection.checkConnection(context))
        {

            ApiService api = RetroClient.getApiService();
            Call<ObjectBlogsCategoryList> call = api.getBlogDataNews();
            call.enqueue(new Callback<ObjectBlogsCategoryList>()
            {
                @Override
                public void onResponse(Call<ObjectBlogsCategoryList> call, Response<ObjectBlogsCategoryList> response)
                {


                    if (response.isSuccessful())
                    {
                        tempList = response.body().getMy_blogs();

                        if (tempList != null)
                        {
                            DataCache.saveBlogList(context, tempList);

                        }
                        else
                        {
                            tempList = new NewsCategoryList();
                        }
                    }
                    else
                    {
                        relaunchTheApp(context);
                        Toast.makeText(context, "Something is wrong !", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<ObjectBlogsCategoryList> call, Throwable t)
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
