package com.example.uiwidgettest.winddragonnews.presenter;

import android.database.sqlite.SQLiteDatabase;

import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.News;
import com.example.uiwidgettest.winddragonnews.model.newscontent.DownloadNewitemInterface;
import com.example.uiwidgettest.winddragonnews.model.newsimage.ImageDownListener;
import com.example.uiwidgettest.winddragonnews.view.ActivityListener;
import com.example.uiwidgettest.winddragonnews.view.NewsContentListener;
import com.example.uiwidgettest.winddragonnews.view.NewsFragmentListener;

import java.net.URL;
import java.util.List;

/**
 * Created by 40774 on 2017/11/8.
 */

public interface DataPresenter {
//    执行操作
    void executeNews(URL url);
    void executeImage(URL url);
//返回数据
    void returnError(String e);
    void returnNewsData(News news);
    void returnImageUrl(String File_path,String url);
//设置
    void setActivityListener(ActivityListener listener);
    void setNewsFragmentListener(NewsFragmentListener listener);
    void setDownloadListener(DownloadNewitemInterface.Down down);
    void setImageDownloadListener(ImageDownListener listener);
}
