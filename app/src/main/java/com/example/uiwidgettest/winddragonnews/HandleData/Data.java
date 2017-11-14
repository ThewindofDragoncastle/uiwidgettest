package com.example.uiwidgettest.winddragonnews.HandleData;

import java.util.List;

/**
 * Created by 40774 on 2017/11/8.
 */

public class Data
{
//    对360新闻 GSON解析的类
    //    posterId string null posterId
    private String posterId;
    //    content string  新闻内容
    private String content;
    //    posterScreenName string  发布者名称
    private String posterScreenName ;
    //    url string http://ent.qq.com/a/20170508/023354.htm 新闻链接
    private String url;
    //    publishDateStr string 发布时间
    private String publishDateStr;
    //    title string  标题
    private String title;
    //commentCount string null 评论数
    private String commentCount;
//    tags
    private String tags;
    private String[] imageUrls;
    //    imageUrls string null 图片
//    private List<Imageurl> imageUrls;

//    public void setImageUrls(List<Imageurl> imageUrls) {
//        this.imageUrls = imageUrls;
//    }
//        public List<Imageurl> getImageUrls() {
//        return imageUrls;
//    }

    public String getCommentCount() {
        return commentCount;
    }

    public String getContent() {
        return content;
    }

//    public String getImageUrls() {
//        return imageUrls;
//    }


    public String[] getImageUrls() {
        return imageUrls;
    }

    public String getPosterId() {
        return posterId;
    }

    public String getPosterScreenName() {
        return posterScreenName;
    }

    public String getPublishDateStr() {
        return publishDateStr;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public void setPosterScreenName(String posterScreenName) {
        this.posterScreenName = posterScreenName;
    }

    public void setPublishDateStr(String publishDateStr) {
        this.publishDateStr = publishDateStr;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

