package com.Samaatv.samaaapp3.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.Samaatv.samaaapp3.model.Contact;
import com.Samaatv.samaaapp3.model.ContactList;
import com.Samaatv.samaaapp3.model.NewsCategoryList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Administrator on 6/14/2017.
 */
public class DataCache
{

    public static void saveMostWatched(Context context, ArrayList<Contact> mostWatch_list)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(mostWatch_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("livepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("mostwatch_list", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveMostWatched(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("livepref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("mostwatch_list", null);
        ArrayList<Contact> mostWatch_list = gson.fromJson(json, listOfObjects);
        return mostWatch_list;
    }

    public static void saveHomeList(Context context, ContactList tempList)
    {
        Gson gson = new Gson();
        String strObject = gson.toJson(tempList); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("homepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("homeList", strObject);
        prefsEditor.commit();
    }

    public static ContactList retrieveHomeList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("homepref", Context.MODE_PRIVATE);
        String json = myPrefs.getString("homeList", null);
        ContactList tempList = gson.fromJson(json, ContactList.class);
        return tempList;
    }

    public static void savePakList(Context context, ArrayList<Contact> pak_list)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(pak_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("pakpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("pak_list", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrievePakList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("pakpref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("pak_list", null);
        ArrayList<Contact> pak_list = gson.fromJson(json, listOfObjects);
        return pak_list;
    }

    public static void saveGlobList(Context context, ArrayList<Contact> world_list)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(world_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("worldpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("world_list", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveGlobList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("worldpref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("world_list", null);
        ArrayList<Contact> world_list = gson.fromJson(json, listOfObjects);
        return world_list;
    }


    public static void saveEconomyList(Context context, ArrayList<Contact> business_list)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(business_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("businesspref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("business_list", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveEconomyList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("businesspref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("business_list", null);
        ArrayList<Contact> business_list = gson.fromJson(json, listOfObjects);
        return business_list;
    }

    public static void saveSportsList(Context context, ArrayList<Contact> sports_list)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(sports_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("sportspref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("sports_list", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveSportsList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("sportspref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("sports_list", null);
        ArrayList<Contact> sports_list = gson.fromJson(json, listOfObjects);
        return sports_list;
    }

    public static void saveEnterList(Context context, ArrayList<Contact> enter_list)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(enter_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("enterpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("enter_list", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveEnterList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("enterpref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("enter_list", null);
        ArrayList<Contact> enter_list = gson.fromJson(json, listOfObjects);
        return enter_list;
    }

    public static void saveEditList(Context context, ArrayList<Contact> editor_list)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(editor_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("editpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("editor_list", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveEditList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("editpref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("editor_list", null);
        ArrayList<Contact> editor_list = gson.fromJson(json, listOfObjects);
        return editor_list;
    }

    public static void saveTvList(Context context, ArrayList<Contact> tv_list)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(tv_list, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("tvpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("tv_list", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveTvList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("tvpref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("tv_list", null);
        ArrayList<Contact> tv_list = gson.fromJson(json, listOfObjects);
        return tv_list;
    }

    public static void saveBlogList(Context context, NewsCategoryList tempList)
    {
        Gson gson = new Gson();
        String strObject = gson.toJson(tempList); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("blogpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("tempList", strObject);
        prefsEditor.commit();
    }

    public static NewsCategoryList retrieveBlogList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("blogpref", Context.MODE_PRIVATE);
        String json = myPrefs.getString("tempList", null);
        NewsCategoryList tempList = gson.fromJson(json, NewsCategoryList.class);
        return tempList;
    }

    //////////////  More Categories caches ///////////////////////

    public static void saveHealthList(Context context, ArrayList<Contact> healthList)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(healthList, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("healthpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("healthList", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveHealthList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("healthpref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("healthList", null);
        ArrayList<Contact> healthList = gson.fromJson(json, listOfObjects);
        return healthList;
    }

    public static void saveLifeList(Context context, ArrayList<Contact> lifeList)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(lifeList, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("lifepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("lifeList", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveLifeList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("lifepref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("lifeList", null);
        ArrayList<Contact> lifeList = gson.fromJson(json, listOfObjects);
        return lifeList;
    }

    public static void saveSocialList(Context context, ArrayList<Contact> socialList)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(socialList, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("socialpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("socialList", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveSocialList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("socialpref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("socialList", null);
        ArrayList<Contact> socialList = gson.fromJson(json, listOfObjects);
        return socialList;
    }

    public static void saveSciList(Context context, ArrayList<Contact> scitechList)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(scitechList, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("scipref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("scitechList", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveSciList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("scipref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("scitechList", null);
        ArrayList<Contact> scitechList = gson.fromJson(json, listOfObjects);
        return scitechList;
    }

    public static void saveWeirdList(Context context, ArrayList<Contact> weirdList)
    {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String strObject = gson.toJson(weirdList, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences myPrefs = context.getSharedPreferences("weirdpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("weirdList", strObject);
        prefsEditor.commit();
    }

    public static ArrayList<Contact> retrieveWeirdList(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences myPrefs = context.getSharedPreferences("weirdpref", Context.MODE_PRIVATE);
        Type listOfObjects = new TypeToken<ArrayList<Contact>>()
        {
        }.getType();
        String json = myPrefs.getString("weirdList", null);
        ArrayList<Contact> weirdList = gson.fromJson(json, listOfObjects);
        return weirdList;
    }

}
