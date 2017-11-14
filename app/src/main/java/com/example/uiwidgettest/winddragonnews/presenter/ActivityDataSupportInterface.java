package com.example.uiwidgettest.winddragonnews.presenter;

import android.database.sqlite.SQLiteDatabase;

import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.DbData;
import com.example.uiwidgettest.winddragonnews.HandleData.News;

import java.util.List;

/**
 * Created by 40774 on 2017/11/14.
 */

public interface ActivityDataSupportInterface {
    //    活动所用
    List<String> queryUrls(String tablename);
    List<DbData>  queryDatalist(String tablename);
    //    保存数据
    void SaveContentData(List<Data> data);
    void SaveFavorite(DbData data);
    void DeleteFavorite(DbData data);
//    设置数据库
    void setDatabases(SQLiteDatabase database);
    void saveCoverPath(String path,String url);
}
