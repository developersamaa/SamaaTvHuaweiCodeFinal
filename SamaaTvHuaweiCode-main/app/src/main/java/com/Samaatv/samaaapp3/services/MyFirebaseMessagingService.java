package com.Samaatv.samaaapp3.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.collection.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.Samaatv.samaaapp3.MainActivity;
import com.Samaatv.samaaapp3.MainActivityUrdu;
import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    private static final String TAG = "MyFirebaseMsgService";
    String language;
    String newsUrl;
    String message;
    String newsCategory;
    private NotificationUtils notificationUtils;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
        {
            return;
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null)
        {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0)
        {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try
            {
                handleMessageData(remoteMessage.getData());
            }
            catch (Exception e)
            {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        showNotificationMessage(message, newsUrl, newsCategory, language);
    }


    private void handleMessageData(Map<String, String> data)
    {
        language = ((ArrayMap) data).valueAt(0).toString();
        newsUrl = ((ArrayMap) data).valueAt(1).toString();
        message = ((ArrayMap) data).valueAt(2).toString();
        newsCategory = ((ArrayMap) data).valueAt(3).toString();
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent)
    {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    private void showNotificationMessage(String message, String newsUrl, String newsCategory, String language)
    {

        if (language != null && language.contains("english"))
        {

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("news_url", newsUrl);
            intent.putExtra("news_cat", newsCategory);
            intent.putExtra("message", message);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.samaa_logo)
                    .setContentTitle("SAMAA Update")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }

        if (language != null && language.contains("urdu"))
        {

            Intent intent = new Intent(this, MainActivityUrdu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("news_url", newsUrl);
            intent.putExtra("news_cat", newsCategory);
            intent.putExtra("message", message);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.samaa_logo)
                    .setContentTitle("SAMAA Update")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSoundUrdu();
        }
    }

    @Override
    public void onNewToken(@NonNull String refreshedToken) {
        super.onNewToken(refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent("registrationComplete");
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void storeRegIdInPref(String refreshedToken)
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ah_firebase", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", refreshedToken);
        editor.commit();
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token)
    {
        // TODO: Implement this method to send token to your app server.
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }
}
