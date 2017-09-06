package com.example.uiwidgettest.ui;

/**
 * Created by 40774 on 2017/7/15.
 */

public class Message {
    public final static int TYPE_RECEIVED=0;
    public final static int TYPE_SEND=1;
    private int currenttype;
    private String content;
    public Message(String content,int currenttype)
    {
        this.content=content;
        this.currenttype=currenttype;
    }
    public String getContent()
    {
        return content;
    }
    public int getCurrenttype()
    {
        return currenttype;
    }
}
