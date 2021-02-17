package com.Samaatv.samaaapp3.utils;

import android.content.Context;

import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_blog_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_economy_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_editor_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_entertainment_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_global_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_live_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_morefeatures_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_pak_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_sport_fragment_urdu;
import com.Samaatv.samaaapp3.api_calls_fragment_urdu.Calls_tv_fragment_urdu;

public class BackgroundTaskUrduNew {
    public static void callbackgroundTasks(Context mContext) {
        Calls_live_fragment_urdu.callLiveMostWatched(mContext);
        Calls_pak_fragment_urdu.callPakList(mContext);
        Calls_global_fragment_urdu.callGlobalList(mContext);
        Calls_economy_fragment_urdu.callEconomyList(mContext);
        Calls_sport_fragment_urdu.callSportsList(mContext);
        Calls_entertainment_fragment_urdu.callEnterList(mContext);
        Calls_editor_fragment_urdu.callEditList(mContext);
        Calls_tv_fragment_urdu.callTvList(mContext);
        Calls_blog_fragment_urdu.callBlogList(mContext);

        Calls_morefeatures_fragment_urdu.callHealthList(mContext);
        Calls_morefeatures_fragment_urdu.callLifeStyleList(mContext);
        Calls_morefeatures_fragment_urdu.callSocialList(mContext);
        Calls_morefeatures_fragment_urdu.callSciList(mContext);
        Calls_morefeatures_fragment_urdu.callWeirdList(mContext);
    }
}
