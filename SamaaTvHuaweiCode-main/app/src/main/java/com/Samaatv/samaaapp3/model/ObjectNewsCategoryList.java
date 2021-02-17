package com.Samaatv.samaaapp3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 4/17/2017.
 */
public class ObjectNewsCategoryList implements Serializable
{

    @SerializedName("my_news")
    @Expose
    private NewsCategoryList my_news;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public NewsCategoryList getMy_news()
    {
        return my_news;
    }

    public void setMy_news(NewsCategoryList my_news)
    {
        this.my_news = my_news;
    }

    public Map<String, Object> getAdditionalProperties()
    {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value)
    {
        this.additionalProperties.put(name, value);
    }
}
