package com.example.uiwidgettest.news;

/**
 * Created by 40774 on 2017/7/18.
 */
//新闻的内容 无视图
public class News {
    private String content;
    private String title;
//public News(String title,String content)
//    {
//        this.content=content;
//        this.title=title;
//    }这样写不利于更换标题和内容
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
