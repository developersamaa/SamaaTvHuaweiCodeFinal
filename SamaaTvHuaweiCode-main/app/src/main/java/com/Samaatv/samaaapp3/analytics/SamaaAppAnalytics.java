package com.Samaatv.samaaapp3.analytics;

import android.os.Bundle;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;

/**
 * Created by Administrator on 5/10/2017.
 */
public class SamaaAppAnalytics extends MultiDexApplication
{
    public static final String TAG = SamaaAppAnalytics.class
            .getSimpleName();

    private static SamaaAppAnalytics mInstance;

    public static synchronized SamaaAppAnalytics getInstance()
    {
        return mInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;


        // Huawei Analytics
        HiAnalyticsTools.enableLog();

//        AnalyticsTrackers.initialize(this);
//        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
    }

//    public synchronized Tracker getGoogleAnalyticsTracker()
//    {
//        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
//        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
//    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName)
    {
//        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
//        t.setScreenName(screenName);

        // Send a screen view.
//        t.send(new HitBuilders.ScreenViewBuilder().build());

//        GoogleAnalytics.getInstance(this).dispatchLocalHits();


        HiAnalyticsInstance instance = HiAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(screenName, screenName);

        instance.onEvent(screenName, bundle);

    }
//
//    /***
//     * Tracking exception
//     *
//     * @param e exception to be tracked
//     */
//    public void trackException(Exception e)
//    {
//        if (e != null)
//        {
//            Tracker t = getGoogleAnalyticsTracker();
//
//            t.send(new HitBuilders.ExceptionBuilder()
//                    .setDescription(
//                            new StandardExceptionParser(this, null)
//                                    .getDescription(Thread.currentThread().getName(), e))
//                    .setFatal(false)
//                    .build()
//            );
//        }
//    }
//
//    /***
//     * Tracking event
//     *
//     * @param category event category
//     * @param action   action of the event
//     * @param label    label
//     */
//    public void trackEvent(String category, String action, String label)
//    {
//        Tracker t = getGoogleAnalyticsTracker();
//
//        // Build and send an Event.
//        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
//    }

}

