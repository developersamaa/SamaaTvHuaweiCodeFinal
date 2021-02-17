package com.Samaatv.samaaapp3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 4/21/2017.
 */
public class PlayerSelect implements Serializable
{

    @SerializedName("player")
    @Expose
    private String player;


    public String getPlayer()
    {
        return player;
    }

    public void setPlayer(String player)
    {
        this.player = player;
    }

}

