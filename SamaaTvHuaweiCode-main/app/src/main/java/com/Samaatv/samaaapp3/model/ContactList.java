package com.Samaatv.samaaapp3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactList implements Serializable
{

    @SerializedName("Trending_Now")
    @Expose
    private ArrayList<Contact> trend = new ArrayList<>();
    /**
     * @return The trend
     */

    @SerializedName("Most-Watched-Programs")
    @Expose
    private ArrayList<Contact> most_watched = new ArrayList<>();
    /**
     * @return The Most Watched
     */

    @SerializedName("Most_Watched")
    @Expose
    private ArrayList<Contact> most_watched_urdu = new ArrayList<>();
    /**
     * @return The Most Watched Urdu
     */

    @SerializedName("National")
    @Expose
    private ArrayList<Contact> national = new ArrayList<>();
    /**
     * @return The national
     */

    @SerializedName("Sports")
    @Expose
    private ArrayList<Contact> sports = new ArrayList<>();
    /**
     * @return The sports
     */

    @SerializedName("Editors_Choice")
    @Expose
    private ArrayList<Contact> editors_choice = new ArrayList<>();
    /**
     * @return The editors_Choice
     */

    @SerializedName("Entertainment")
    @Expose
    private ArrayList<Contact> entertainment = new ArrayList<>();
    /**
     * @return The entertainment
     */


    @SerializedName("Economy")
    @Expose
    private ArrayList<Contact> economy = new ArrayList<>();
    /**
     * @return The economy
     */


    @SerializedName("Global")
    @Expose
    private ArrayList<Contact> global = new ArrayList<>();

    /**
     * @return The global
     */

    @SerializedName("Breaking")
    @Expose
    private ArrayList<Contact> breaking = new ArrayList<>();
    /**
     * @return The breaking
     */

    @SerializedName("Player")
    @Expose
    private ArrayList<PlayerSelect> player = new ArrayList<>();

    /**
     * @return The player
     */

    public ArrayList<Contact> getTrend()
    {
        return trend;
    }

    public ArrayList<Contact> getMostWatched()
    {
        return most_watched;
    }

    public ArrayList<Contact> getNational()
    {
        return national;
    }

    public ArrayList<Contact> getSports()
    {
        return sports;
    }

    public ArrayList<Contact> getEditorChoice()
    {
        return editors_choice;
    }

    public ArrayList<Contact> getEntertainment()
    {
        return entertainment;
    }

    public ArrayList<Contact> getEconomy()
    {
        return economy;
    }

    public ArrayList<Contact> getGlobal()
    {
        return global;
    }

    public ArrayList<Contact> getBreaking()
    {
        return breaking;
    }

    public ArrayList<Contact> getMostWatchedUrdu()
    {
        return most_watched_urdu;
    }

    public ArrayList<PlayerSelect> getPlayer()
    {
        return player;
    }

    /**
     * @param contacts The contacts
     */
    public void setContacts(ArrayList<Contact> contacts)
    {
        this.trend = trend;
    }
}