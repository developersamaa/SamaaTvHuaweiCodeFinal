package com.Samaatv.samaaapp3.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.type.HAEventType;
import com.huawei.hms.analytics.type.HAParamType;

public class SamaaFirebaseAnalytics
{

    private static FirebaseAnalytics mFirebaseAnalytics;
    private static HiAnalyticsInstance hiAnalyticsInstance;


    public static void syncSamaaAnalytics(Context context, String screenName)
    {

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);
//        //Logs an app event.
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//        //Sets whether analytics collection is enabled for this app on this device.
//        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);


        // Obtain the HiAnalyticsInstance instance.
        hiAnalyticsInstance = HiAnalytics.getInstance(context);

        Bundle bundle = new Bundle();
        bundle.putString(HAParamType.PRODUCTNAME, screenName);
        hiAnalyticsInstance.onEvent(HAEventType.VIEWCONTENT, bundle);
        hiAnalyticsInstance.setAnalyticsEnabled(true);

    }
}
