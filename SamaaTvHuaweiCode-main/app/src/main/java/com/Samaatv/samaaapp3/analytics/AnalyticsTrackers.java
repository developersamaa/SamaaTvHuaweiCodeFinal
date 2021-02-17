package com.Samaatv.samaaapp3.analytics;

import android.content.Context;


/**
 * Created by Administrator on 5/10/2017.
 */
public class AnalyticsTrackers
{

    private static AnalyticsTrackers sInstance;
//    private final Map<Target, Tracker> mTrackers = new HashMap<Target, Tracker>();
    private final Context mContext;

    /**
     * Don't instantiate directly - use {@link #getInstance()} instead.
     */
    private AnalyticsTrackers(Context context)
    {
        mContext = context.getApplicationContext();
    }

    public static synchronized void initialize(Context context)
    {
        if (sInstance != null)
        {
            throw new IllegalStateException("Extra call to initialize analytics trackers");
        }

        sInstance = new AnalyticsTrackers(context);
    }

    public static synchronized AnalyticsTrackers getInstance()
    {
        if (sInstance == null)
        {
            throw new IllegalStateException("Call initialize() before getInstance()");
        }

        return sInstance;
    }

//    public synchronized Tracker get(Target target)
//    {
//        if (!mTrackers.containsKey(target))
//        {
//            Tracker tracker;
//            switch (target)
//            {
//                case APP:
//                    tracker = GoogleAnalytics.getInstance(mContext).newTracker(R.xml.app_tracker);
//                    tracker.enableAdvertisingIdCollection(true);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unhandled analytics target " + target);
//            }
//            mTrackers.put(target, tracker);
//        }
//
//        return mTrackers.get(target);
//    }

    public enum Target
    {
        APP,
        // Add more trackers here if you need, and update the code in #get(Target) below
    }
}
