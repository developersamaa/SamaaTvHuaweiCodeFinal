package com.Samaatv.samaaapp3.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewGroup;

import com.Samaatv.samaaapp3.model.Contact;

import java.util.ArrayList;

/**
 * Created by Administrator on 4/28/2017.
 */
public class DevicePreference
{

    private static DevicePreference mInstance;
    ArrayList<Contact> globalList;
    ArrayList<Contact> pakList;
    ArrayList<Contact> sportsList;
    ArrayList<Contact> enterList;
    ArrayList<Contact> econList;
    ArrayList<Contact> editList;
    ArrayList<Contact> healthList;
    ArrayList<Contact> tvList;
    ArrayList<Contact> techList;
    ArrayList<Contact> socialList;
    ArrayList<Contact> weirdList;
    String healthP;
    String lifeP;
    String socialP;
    String scitechP;
    String weirdP;
    String newsCat;
    ViewGroup rootView;
    boolean fromNotif;
    String language;
    private Context mContext;
    private SharedPreferences mPrefs;
    private String KEY_AUTH_HEADER = "authkey";

    private DevicePreference()
    {

    }

    public static DevicePreference getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new DevicePreference();
        }

        return mInstance;
    }

    public void initPref(Context context)
    {

        this.mContext = context;

        mPrefs = mContext.getSharedPreferences("clientPrefs", Context.MODE_PRIVATE);
    }

    public Context getmContext()
    {
        return mContext;
    }

    public void setmContext(Context mContext)
    {
        this.mContext = mContext;
    }


    public ArrayList<Contact> getGlobalList()
    {
        return globalList;
    }

    public void setGlobalList(ArrayList<Contact> globalList)
    {
        this.globalList = globalList;
    }

    public ArrayList<Contact> getPakList()
    {
        return pakList;
    }

    public void setPakList(ArrayList<Contact> pakList)
    {
        this.pakList = pakList;
    }

    public ArrayList<Contact> getSportsList()
    {
        return sportsList;
    }

    public void setSportsList(ArrayList<Contact> sportsList)
    {
        this.sportsList = sportsList;
    }

    public ArrayList<Contact> getEnterList()
    {
        return enterList;
    }

    public void setEnterList(ArrayList<Contact> enterList)
    {
        this.enterList = enterList;
    }

    public ArrayList<Contact> getEconList()
    {
        return econList;
    }

    public void setEconList(ArrayList<Contact> econList)
    {
        this.econList = econList;
    }

    public ArrayList<Contact> getEditList()
    {
        return editList;
    }

    public void setEditList(ArrayList<Contact> editList)
    {
        this.editList = editList;
    }

    public ArrayList<Contact> getHealthList()
    {
        return healthList;
    }

    public void setHealthList(ArrayList<Contact> healthList)
    {
        this.healthList = healthList;
    }

    public ArrayList<Contact> getTvList()
    {
        return tvList;
    }

    public void setTvList(ArrayList<Contact> tvList)
    {
        this.tvList = tvList;
    }

    public ArrayList<Contact> getTechList()
    {
        return techList;
    }

    public void setTechList(ArrayList<Contact> techList)
    {
        this.techList = techList;
    }

    public ArrayList<Contact> getSocialList()
    {
        return socialList;
    }

    public void setSocialList(ArrayList<Contact> socialList)
    {
        this.socialList = socialList;
    }

    public ArrayList<Contact> getWeirdList()
    {
        return weirdList;
    }

    public void setWeirdList(ArrayList<Contact> weirdList)
    {
        this.weirdList = weirdList;
    }


    public String getHealthP()
    {
        return healthP;
    }

    public void setHealthP(String healthP)
    {
        this.healthP = healthP;
    }

    public String getLifeP()
    {
        return lifeP;
    }

    public void setLifeP(String lifeP)
    {
        this.lifeP = lifeP;
    }

    public String getSocialP()
    {
        return socialP;
    }

    public void setSocialP(String socialP)
    {
        this.socialP = socialP;
    }

    public String getScitechP()
    {
        return scitechP;
    }

    public void setScitechP(String scitechP)
    {
        this.scitechP = scitechP;
    }

    public String getWeirdP()
    {
        return weirdP;
    }

    public void setWeirdP(String weirdP)
    {
        this.weirdP = weirdP;
    }

    public ViewGroup getRootView()
    {
        return rootView;
    }

    public void setRootView(ViewGroup rootView)
    {
        this.rootView = rootView;
    }

    public String getNewsCat()
    {
        return newsCat;
    }

    public void setNewsCat(String newsCat)
    {
        this.newsCat = newsCat;
    }

    public boolean isFromNotif()
    {
        return fromNotif;
    }

    public void setFromNotif(boolean fromNotif)
    {
        this.fromNotif = fromNotif;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }
}
