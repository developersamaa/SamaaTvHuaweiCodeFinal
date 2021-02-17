package com.Samaatv.samaaapp3.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;

/**
 * @author S.Shahzaib Ahmed
 */
public class InternetConnection
{

    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */

    public static boolean checkConnection(@NonNull Context context)
    {
        return ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
