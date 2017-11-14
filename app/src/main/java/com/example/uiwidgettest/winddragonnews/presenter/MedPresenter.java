package com.example.uiwidgettest.winddragonnews.presenter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.News;
import com.example.uiwidgettest.winddragonnews.model.newscontent.DownloadNewitemInterface;
import com.example.uiwidgettest.winddragonnews.model.newsimage.ImageDownListener;
import com.example.uiwidgettest.winddragonnews.view.ActivityListener;
import com.example.uiwidgettest.winddragonnews.view.NewsContentListener;
import com.example.uiwidgettest.winddragonnews.view.NewsFragmentListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 40774 on 2017/11/8.
 */

public class MedPresenter implements DataPresenter {
    private final String TAG=getClass().getName();
private NewsFragmentListener newsFragmentListener;
private ActivityListener activityListener;
private ImageDownListener imageDownListener;
private DownloadNewitemInterface.Down down;

    @Override
    public void returnNewsData(News news) {
        activityListener.ShowContentView(news);
    }

    @Override
    public void returnImageUrl(String File_path,String url) {
//        放入数据库
        MyLog.d(TAG,"返回图片");
        ActivityDataSupport.getInstance().saveCoverPath(File_path,url);
    }


    @Override
    public void executeNews(URL url) {
        down.RequestData(url);
    }

    @Override
    public void executeImage(URL url) {
        MyLog.d(TAG,"图片下载开始");
       imageDownListener.execute(url);
    }

    @Override
    public void setActivityListener(ActivityListener listener) {
        this.activityListener=listener;
    }

    @Override
    public void setNewsFragmentListener(NewsFragmentListener listener) {
        this.newsFragmentListener=listener;
    }


    @Override
    public void setDownloadListener(DownloadNewitemInterface.Down down) {
        this.down=down;
    }

    @Override
    public void setImageDownloadListener(ImageDownListener listener) {
        this.imageDownListener=listener;
    }

    @Override
    public void returnError(String e) {
        newsFragmentListener.showError(e);
    }
}
/*
     MyLog.d("测试数据","服务器信息："+jsondata);
                                    StringBuilder buffer=new StringBuilder();
                                    News news=new HandleJsonData().handleNews(jsondata);
                                    if(news!=null)
                                    {
                                        buffer.append("是否存在下一页："+news.isHasNext()+"\n");
                                        buffer.append("本次查询的api名："+news.getAppcode()+"\n");
                                        buffer.append("本次查询的api类型："+news.getDataType()+"\n");
                                        buffer.append("返回的状态码："+news.getRetcode()+"\n");
                                        buffer.append("翻页值："+news.getPageToken()+"\n");
                                    }
                                    List<Data> data=new HandleJsonData().handleData(jsondata);
                                    if(data.size()!=0)
                                    {
                                        for (Data data1:data) {
                                            buffer.append("id："+data1.getPosterId()+"\n");
                                            buffer.append("标题："+data1.getTitle()+"\n");
                                            buffer.append("新闻内容："+data1.getContent()+"\n");
                                            buffer.append("发布者名称："+data1.getPosterScreenName()+"\n");
                                            buffer.append("新闻链接："+data1.getUrl()+"\n");
                                            buffer.append("发布时间："+data1.getPublishDateStr()+"\n");
                                            buffer.append("评论数："+data1.getCommentCount()+"\n");
//                                                buffer.append("图片链接数："+data1.getImageUrls().size()+"\n");
                                        }
                                    }
                                    for (String s:new HandleJsonData().returnImageUrls(jsondata))
                                        buffer.append("图片url:"+s+"\n");*/