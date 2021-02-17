package com.Samaatv.samaaapp3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 4/18/2017.
 */
public class ObjectBlogsCategoryList implements Serializable
{

    @SerializedName("blogs_main")
    @Expose
    private NewsCategoryList my_blogs;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public NewsCategoryList getMy_blogs()
    {
        return my_blogs;
    }

    public void setMy_blogs(NewsCategoryList my_blogs)
    {
        this.my_blogs = my_blogs;
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