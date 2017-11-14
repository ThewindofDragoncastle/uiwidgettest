package com.example.uiwidgettest.winddragonnews.HandleData;

import android.provider.ContactsContract;

import java.util.List;

/**
 * Created by 40774 on 2017/11/8.
 */

public class News {
//    hasNext boolean true 是否有下一页
    private boolean hasNext;
//    retcode string 000000 返回的状态码
    private String retcode;
//    appCode string qihoo 本次查询的api名
    private String appcode;
//    dataType string news 本次查询的api类型
    private String dataType;
//    pageToken string 2 翻页值
    private String pageToken;
//    返回的数据
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public String getAppcode() {
        return appcode;
    }

    public String getDataType() {
        return dataType;
    }

    public String getPageToken() {
        return pageToken;
    }

    public String getRetcode() {
        return retcode;
    }
}
/*
参数名 类型 示例值 描述
-



tags string null tags



publishDate number 1494215160 发布日期时间戳


id string c028fc8126658124bc8f21a13650d51b 新闻id
*/