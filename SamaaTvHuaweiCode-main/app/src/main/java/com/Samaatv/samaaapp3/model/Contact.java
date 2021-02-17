package com.Samaatv.samaaapp3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contact implements Serializable
{

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("pubDate")
    @Expose
    private String pubDate;

    @SerializedName("videourl")
    @Expose
    private String videourl;


    @SerializedName("vmob")
    @Expose
    private String vmob;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("timings")
    @Expose
    private String timings;


    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("profile_pic")
    @Expose
    private String profilePic;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("youtube-url")
    @Expose
    private String ytvideo;

    @SerializedName("phone")
    @Expose
    private Phone phone;

    /**
     * @return The id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getDate()
    {
        return pubDate;
    }

    public String getVideo()
    {
        return videourl;
    }

    public String getVmob()
    {
        return vmob;
    }

    public void setVmob(String vmob)
    {
        this.vmob = vmob;
    }

    public String getLink()
    {
        return link;
    }

    public String getTimings()
    {
        return timings;
    }

    public String getUrl()
    {
        return url;
    }

    /**
     * @return The email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return The address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return The gender
     */
    public String getGender()
    {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    /**
     * @return The profilePic
     */
    public String getProfilePic()
    {
        return profilePic;
    }

    public void setProfilePic(String profilePic)
    {
        this.profilePic = profilePic;
    }

    public String getImage()
    {
        return image;
    }

    public String getYTVideo()
    {
        return ytvideo;
    }

    public String getCategory()
    {
        return category;
    }

    /**
     * @return The phone
     */
    public Phone getPhone()
    {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(Phone phone)
    {
        this.phone = phone;
    }

    public class Phone
    {

        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("home")
        @Expose
        private String home;
        @SerializedName("office")
        @Expose
        private String office;

        /**
         * @return The mobile
         */
        public String getMobile()
        {
            return mobile;
        }

        /**
         * @param mobile The mobile
         */
        public void setMobile(String mobile)
        {
            this.mobile = mobile;
        }

        /**
         * @return The home
         */
        public String getHome()
        {
            return home;
        }

        /**
         * @param home The home
         */
        public void setHome(String home)
        {
            this.home = home;
        }

        /**
         * @return The office
         */
        public String getOffice()
        {
            return office;
        }

        /**
         * @param office The office
         */
        public void setOffice(String office)
        {
            this.office = office;
        }

    }
}