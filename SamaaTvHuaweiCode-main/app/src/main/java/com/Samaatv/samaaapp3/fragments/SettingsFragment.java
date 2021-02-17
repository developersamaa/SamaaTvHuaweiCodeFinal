package com.Samaatv.samaaapp3.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;
import android.util.Log;
import android.widget.Toast;

import com.Samaatv.samaaapp3.R;
import com.Samaatv.samaaapp3.utils.DevicePreference;
import com.google.firebase.messaging.FirebaseMessaging;

//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.google.android.gms.gcm.GcmPubSub;

/**
 * The Preference Fragment which shows the Preferences as a List and handles the Dialogs for the
 * Preferences.
 *
 * @author Jakob Ulbrich
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private static final String TAG = SettingsFragment.class.getSimpleName();
    boolean language_eng;
    boolean language_urdu;
    SharedPreferences mPrefs;
    SwitchPreferenceCompat prefEng;
    SwitchPreferenceCompat prefUrdu;

    String KEY_PREF_RATE = "rateus";
    String KEY_PREF_NOTIFICATION = "push_noti";
    String KEY_PREF_NOTI_SOUND = "audible_alert";
    String KEY_PREF_START_SOUND = "startup_sound";

    //  SharedPreferences prefs1;


    @Override
    public void onCreatePreferences(Bundle bundle, String s)
    {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        prefEng = (SwitchPreferenceCompat) findPreference("english");
        prefUrdu = (SwitchPreferenceCompat) findPreference("urdu");


        language_eng = mPrefs.getBoolean("english", true);
        language_urdu = mPrefs.getBoolean("urdu", true);

        if (DevicePreference.getInstance().getLanguage() != null && DevicePreference.getInstance().getLanguage().contains("English"))
        {
            prefEng.setChecked(true);
            prefEng.setDefaultValue(true);
            prefUrdu.setChecked(false);
        }

        if (language_eng)
        {
            prefEng.setChecked(true);
            prefEng.setDefaultValue(true);
            prefUrdu.setChecked(false);
        }

        if (DevicePreference.getInstance().getLanguage() != null && DevicePreference.getInstance().getLanguage().contains("Urdu"))
        {
            prefUrdu.setChecked(true);
            prefUrdu.setDefaultValue(true);
            prefEng.setChecked(false);
        }

        if (language_urdu)
        {
            prefUrdu.setChecked(true);
            prefUrdu.setDefaultValue(true);
            prefEng.setChecked(false);
        }

        mPrefs.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onStart()
    {
        super.onStart();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //unregister the preferenceChange listener
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String key)
    {

//        prefEng = (SwitchPreferenceCompat) findPreference("english");
//        prefUrdu = (SwitchPreferenceCompat) findPreference("urdu");

        if (prefEng != null)
        {
            prefEng.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object switchEng)
                {

                    boolean isEngSwitchOn = (Boolean) switchEng;
                    if (isEngSwitchOn)
                    {
                        SharedPreferences.Editor e = mPrefs.edit();
                        e.putBoolean("english", isEngSwitchOn);
                        e.apply();
                        prefEng.setSwitchTextOn("On");
                        prefEng.setSummary("English");
                        FirebaseMessaging.getInstance().subscribeToTopic("english");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("urdu");
                        prefUrdu.setChecked(false);
                        prefUrdu.setDefaultValue(false);
                    }
                    return true;
                }
            });
        }

        if (prefUrdu != null)
        {
            prefUrdu.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object switchEng)
                {

                    boolean isEngSwitchOn = (Boolean) switchEng;
                    if (isEngSwitchOn)
                    {
                        SharedPreferences.Editor e = mPrefs.edit();
                        e.putBoolean("urdu", isEngSwitchOn);
                        e.apply();
                        prefUrdu.setSwitchTextOn("On");
                        prefUrdu.setSummary("Urdu");
                        FirebaseMessaging.getInstance().subscribeToTopic("urdu");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("english");
                        prefEng.setChecked(false);
                        prefEng.setDefaultValue(false);
                    }
                    return true;
                }
            });
        }

        final SwitchPreferenceCompat push_notification = (SwitchPreferenceCompat) getPreferenceManager().findPreference(KEY_PREF_NOTIFICATION);

        if (push_notification != null)
        {
            push_notification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference arg0, Object push)
                {
                    boolean push_app = (Boolean) push;
                    // boolean test = sharedPreferences.getBoolean("test", false);
                    if (push_app && language_eng)
                    {
                        // [START subscribe_topics to English]
                        FirebaseMessaging.getInstance().subscribeToTopic("english");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("urdu");
                        // [END subscribe_topics to English and Unsubscribe to Urdu]
                        // Log and toast
                        String msg = getString(R.string.eng_subscribed);
                        Log.d(TAG, msg);
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        push_notification.setSummary("English News");
                        Toast.makeText(getActivity(), "Push notification has been enabled !", Toast.LENGTH_SHORT).show();

                    }
                    else if (push_app && language_urdu)
                    {

                        FirebaseMessaging.getInstance().unsubscribeFromTopic("english");
                        // [ subscribe_topics to Urdu and Unsubscribe to English]
                        // [ subscribe_topics to Urdu]
                        FirebaseMessaging.getInstance().subscribeToTopic("urdu");


                        // Log and toast
                        String msg = getString(R.string.urdu_subscribed);
                        Log.d(TAG, msg);
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        push_notification.setSummary("News");
                        Toast.makeText(getActivity(), "Push notification has been enabled !", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        // [START unsubscribe_topics to Urdu and English]
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("urdu");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("english");
                        // [END unsubscribe_topics to Urdu and English]

                        // Log and toast
                        String msg = getString(R.string.unsubscribed);
                        Log.d(TAG, msg);
                        push_notification.setSummary("Push notification Disabled !");
                        Toast.makeText(getActivity(), "Push notification has been disabled !", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
        }


        final SwitchPreferenceCompat noti_sound = (SwitchPreferenceCompat) findPreference(KEY_PREF_NOTI_SOUND);

        if (noti_sound != null)
        {
            noti_sound.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference arg0, Object isSoundOn)
                {
                    boolean push_sound = (Boolean) isSoundOn;
                    // boolean test = sharedPreferences.getBoolean("test", false);
                    if (push_sound)
                    {
                        SharedPreferences.Editor editor = mPrefs.edit();
                        editor.putBoolean("audible_alert", push_sound);
                        editor.apply();
                        noti_sound.setSummary("On");
                        Toast.makeText(getActivity(), "Push notification sound has been enabled", Toast.LENGTH_SHORT).show();
                        //return true;
                    }
                    else
                    {
                        SharedPreferences.Editor editor = mPrefs.edit();
                        editor.putBoolean("audible_alert", push_sound);
                        editor.apply();
                        noti_sound.setSummary("Off");
                        Toast.makeText(getActivity(), "Push notification sound has been disabled", Toast.LENGTH_SHORT).show();
                        // return false;
                    }

                    return true;
                }
            });
        }

        final SwitchPreferenceCompat start_sound = (SwitchPreferenceCompat) findPreference(KEY_PREF_START_SOUND);


        if (start_sound != null)
        {
            start_sound.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference arg0, Object isSoundOn)
                {
                    boolean sound_app = (Boolean) isSoundOn;
                    // boolean test = sharedPreferences.getBoolean("test", false);
                    if (sound_app)
                    {
                        SharedPreferences.Editor editor = mPrefs.edit();
                        editor.putBoolean("startup_sound", sound_app);
                        editor.apply();
                        start_sound.setSummary("On");
                        Toast.makeText(getActivity(), "App startup sound has been enabled !", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        SharedPreferences.Editor editor = mPrefs.edit();
                        editor.putBoolean("startup_sound", sound_app);
                        editor.apply();
                        start_sound.setSummary("Off");
                        Toast.makeText(getActivity(), "App startup sound has been disabled !", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
        }

    }

    @Override
    public void onPause()
    {
        super.onPause();
        //unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //unregister event bus.
        //  EventBus.unregister(this);
    }
}
