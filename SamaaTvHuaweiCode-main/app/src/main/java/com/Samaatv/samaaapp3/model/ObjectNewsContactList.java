package com.Samaatv.samaaapp3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 4/18/2017.
 */
public class ObjectNewsContactList implements Serializable
{

    @SerializedName("cats_main")
    @Expose
    private ContactList objCateg_MainList;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ContactList getObjCateg_MainList()
    {
        return objCateg_MainList;
    }

    public void setObjCateg_MainList(ContactList objCateg_MainList)
    {
        this.objCateg_MainList = objCateg_MainList;
    }

    public Map<String, Object> getAdditionalProperties()
    {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties)
    {
        this.additionalProperties = additionalProperties;
    }

}
