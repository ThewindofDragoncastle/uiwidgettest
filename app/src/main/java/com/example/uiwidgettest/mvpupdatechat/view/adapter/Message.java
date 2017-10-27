package com.example.uiwidgettest.mvpupdatechat.view.adapter;

import android.support.annotation.NonNull;

/**
 * Created by 40774 on 2017/10/18.
 */

public class Message {
    private String message="???";
    //false代表发信人
    private boolean IsRepient =false;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setRepient(boolean repient) {
        IsRepient = repient;
    }

    public boolean isRepient() {
        return IsRepient;
    }

}
