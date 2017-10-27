package com.example.uiwidgettest.framework.eventbus.useclass;

import android.view.View;

/**
 * Created by 40774 on 2017/10/11.
 */
//消息事件类
public class MessageEvent {
    private String message;

    private View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
