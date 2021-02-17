package com.Samaatv.samaaapp3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsCategoryList implements Serializable
{

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

    @SerializedName("Programs")
    @Expose
    private ArrayList<Contact> programs = new ArrayList<>();

    /**
     * @return The tvshows
     */

    @SerializedName("Program-Episodes")
    @Expose
    private ArrayList<Contact> programs_episodes = new ArrayList<>();

    /**
     * @return The tvshows episodes
     */


    @SerializedName("Blogs")
    @Expose
    private ArrayList<Contact> blogs = new ArrayList<>();

    /**
     * @return The blogs
     */

    @SerializedName("See-More")
    @Expose
    private ArrayList<Contact> seemore = new ArrayList<>();

    /**
     * @return The See More
     */

    @SerializedName("Latest-Programs")
    @Expose
    private ArrayList<Contact> alsowatch = new ArrayList<>();

    /**
     * @return The Also Watch
     */

    @SerializedName("FilterTimeCategory")
    @Expose
    private ArrayList<Contact> newsbytime = new ArrayList<>();

    /**
     * @return The Also Watch
     */

    @SerializedName("top-blogs")
    @Expose
    private ArrayList<Contact> topblogs = new ArrayList<>();

    /**
     * @return The Top Blogs
     */

    @SerializedName("latest-blogs")
    @Expose
    private ArrayList<Contact> recentblogs = new ArrayList<>();

    /**
     * @return The Recent Blogs
     */

    @SerializedName("picture-blogs")
    @Expose
    private ArrayList<Contact> picblogs = new ArrayList<>();

    /**
     * @return The Picture Blogd
     */

    @SerializedName("video-blogs")
    @Expose
    private ArrayList<Contact> videoblogs = new ArrayList<>();

    /**
     * @return The Video Blogs
     */

    @SerializedName("power-games")
    @Expose
    private ArrayList<Contact> powergames = new ArrayList<>();

    /**
     * @return The Power Games
     */

    @SerializedName("social_pulse")
    @Expose
    private ArrayList<Contact> socialpulse = new ArrayList<>();

    /**
     * @return The Social Pulse
     */

    @SerializedName("glitz-glam")
    @Expose
    private ArrayList<Contact> glitzglams = new ArrayList<>();

    /**
     * @return The Glitz Glams
     */

    @SerializedName("food-shood")
    @Expose
    private ArrayList<Contact> foodshood = new ArrayList<>();

    /**
     * @return The Food Shood
     */

    @SerializedName("sports")
    @Expose
    private ArrayList<Contact> sportsblogs = new ArrayList<>();

    /**
     * @return The Sports Blogs
     */

    @SerializedName("Health")
    @Expose
    private ArrayList<Contact> healthblogs = new ArrayList<>();

    /**
     * @return The Health Blogs
     */

    @SerializedName("Life Stlye")
    @Expose
    private ArrayList<Contact> lifestyleblogs = new ArrayList<>();

    /**
     * @return The LifeStyle Blogs
     */

    @SerializedName("Social Buzz")
    @Expose
    private ArrayList<Contact> socialBuzzBlogs = new ArrayList<>();

    /**
     * @return The SocialBuzz Blogs
     */

    @SerializedName("Sci Tech")
    @Expose
    private ArrayList<Contact> sciTechBlogs = new ArrayList<>();

    /**
     * @return The Sci Tech Blogs
     */

    @SerializedName("Weird")
    @Expose
    private ArrayList<Contact> weirdBlogs = new ArrayList<>();

    /**
     * @return The Sci Tech Blogs
     */


    @SerializedName("lifestyle")
    @Expose
    private ArrayList<Contact> lifestyleMyNews = new ArrayList<>();

    /**
     * @return The LifeStyle MyNews
     */

    @SerializedName("scitech")
    @Expose
    private ArrayList<Contact> sciTechMyNews = new ArrayList<>();

    /**
     * @return The SciTech MyNews
     */


    @SerializedName("social")
    @Expose
    private ArrayList<Contact> socialMyNews = new ArrayList<>();

    /**
     * @return The Social MyNews
     */

    @SerializedName("weird")
    @Expose
    private ArrayList<Contact> weirdMyNews = new ArrayList<>();

    /**
     * @return The Social MyNews
     */


    public ArrayList<Contact> getNational()
    {
        return national;
    }

    public ArrayList<Contact> getNewsByTime()
    {
        return newsbytime;
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

    public ArrayList<Contact> getPrograms()
    {
        return programs;
    }

    public ArrayList<Contact> getBlogs()
    {
        return blogs;
    }

    public ArrayList<Contact> getSeeMore()
    {
        return seemore;
    }

    public ArrayList<Contact> getAlsoWatch()
    {
        return alsowatch;
    }

    public ArrayList<Contact> getTVShowsEpisode()
    {
        return programs_episodes;
    }

    public ArrayList<Contact> getRecentBlogs()
    {
        return recentblogs;
    }

    public ArrayList<Contact> getTopBlogs()
    {
        return topblogs;
    }

    public ArrayList<Contact> getPicBlogs()
    {
        return picblogs;
    }

    public ArrayList<Contact> getPowerGames()
    {
        return powergames;
    }

    public ArrayList<Contact> getVideoBlogs()
    {
        return videoblogs;
    }

    public ArrayList<Contact> getSocialPulse()
    {
        return socialpulse;
    }

    public ArrayList<Contact> getGlitzGlams()
    {
        return glitzglams;
    }

    public ArrayList<Contact> getFoodShood()
    {
        return foodshood;
    }

    public ArrayList<Contact> getSportBlogs()
    {
        return sportsblogs;
    }


    public ArrayList<Contact> getHealthBlogs()
    {
        return healthblogs;
    }

    public ArrayList<Contact> getLifestyleblogs()
    {
        return lifestyleblogs;
    }

    public ArrayList<Contact> getSocialBuzzBlogs()
    {
        return socialBuzzBlogs;
    }

    public ArrayList<Contact> getSciTechBlogs()
    {
        return sciTechBlogs;
    }

    public ArrayList<Contact> getWeirdBlogs()
    {
        return weirdBlogs;
    }


    public ArrayList<Contact> getLifestyleMyNews()
    {
        return lifestyleMyNews;
    }

    public ArrayList<Contact> getSciTechMyNews()
    {
        return sciTechMyNews;
    }

    public ArrayList<Contact> getSocialMyNews()
    {
        return socialMyNews;
    }

    public ArrayList<Contact> getWeirdMyNews()
    {
        return weirdMyNews;
    }
    /**
     * @param contacts The contacts
     */
   /* public void setContacts(ArrayList<Contact> contacts) {
        this.trend = trend;
    }*/
}