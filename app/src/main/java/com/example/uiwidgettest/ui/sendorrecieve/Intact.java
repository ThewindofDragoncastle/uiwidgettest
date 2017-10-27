package com.example.uiwidgettest.ui.sendorrecieve;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 40774 on 2017/9/5.
 */

public class Intact {
    private String name;
    private Boolean unread=false;
    private String  time;
    @SerializedName("image")
    private int intractimage;
    private int online;
    @SerializedName("target")
    private String targetaccount;
    @SerializedName("current")
    private String currentaccount;
    public int getIntractimage() {
        return intractimage;
    }

    public int getOnline() {
        return online;
    }

    public void setIntractimage(int intractimage) {
        this.intractimage = intractimage;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public Intact(String currentaccount,String targetaccount)
    {
        this.targetaccount=targetaccount;
        this.currentaccount=currentaccount;
    }
    public String getCurrentaccount() {
        return currentaccount;
    }

    public String gettargetAccount() {
        return targetaccount;
    }

    public Boolean getUnread() {
        return unread;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUnread(Boolean unread) {
        this.unread = unread;
    }
}
