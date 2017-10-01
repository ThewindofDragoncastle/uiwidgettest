package com.example.uiwidgettest.ui.sendorrecieve;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 40774 on 2017/9/19.
 */

public class IntactMessage {
    @SerializedName("head")
    public String headfile;
    public String name;
    public String account;
    public String status;
    public String online;
    public String unread;
    public String getUnread() {
        return unread;
    }

    public String getOnline() {
        return online;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getHeadfile() {
        return headfile;
    }

    public String getStatus() {
        return status;
    }
}
