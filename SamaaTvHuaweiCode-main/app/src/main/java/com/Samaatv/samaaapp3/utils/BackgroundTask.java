package com.Samaatv.samaaapp3.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.Samaatv.samaaapp3.api_calls_fragment.Calls_blog_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_economy_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_editor_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_entertainment_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_global_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_live_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_morefeatures_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_pak_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_sport_fragment;
import com.Samaatv.samaaapp3.api_calls_fragment.Calls_tv_fragment;

/**
 * Created by Administrator on 6/14/2017.
 */
public class BackgroundTask extends AsyncTask<Void, Void, Void>
{

    private Context mContext;

    public BackgroundTask(Context context)
    {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params)
    {

        Calls_live_fragment.callLiveMostWatched(mContext);
        Calls_pak_fragment.callPakList(mContext);
        Calls_global_fragment.callGlobalList(mContext);
        Calls_economy_fragment.callEconomyList(mContext);
        Calls_sport_fragment.callSportsList(mContext);
        Calls_entertainment_fragment.callEnterList(mContext);
        Calls_editor_fragment.callEditList(mContext);
        Calls_tv_fragment.callTvList(mContext);
        Calls_blog_fragment.callBlogList(mContext);

        Calls_morefeatures_fragment.callHealthList(mContext);
        Calls_morefeatures_fragment.callLifeStyleList(mContext);
        Calls_morefeatures_fragment.callSocialList(mContext);
        Calls_morefeatures_fragment.callSciList(mContext);
        Calls_morefeatures_fragment.callWeirdList(mContext);

        return null;
    }
}
