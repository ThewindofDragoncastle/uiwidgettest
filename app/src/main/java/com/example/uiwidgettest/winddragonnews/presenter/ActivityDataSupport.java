package com.example.uiwidgettest.winddragonnews.presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.news.Content;
import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.DbData;
import com.example.uiwidgettest.winddragonnews.HandleData.News;
import com.example.uiwidgettest.winddragonnews.databases.NewsSQLDatabasesHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 40774 on 2017/11/14.
 */

public class ActivityDataSupport implements ActivityDataSupportInterface {
//    为活动处理数据库数据
    private final String TAG=getClass().getName();
//    数据库
    private SQLiteDatabase database;
    private final String TABLE_CONTENT="content";
    private static final ActivityDataSupport ourInstance = new ActivityDataSupport();

    public static ActivityDataSupport getInstance() {
        return ourInstance;
    }

    private ActivityDataSupport() {

    }
    @Override
    public List<String> queryUrls(String tablename) {
//        查询新闻的url
//        可以复用
        List<String> dd=new ArrayList<>();
        Cursor cursor=database.query(tablename,new String[]{"url"},null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do{
                String data=cursor.getString(cursor.getColumnIndex("url"));
                MyLog.d(TAG,data);
                dd.add(data);
            }
            while (cursor.moveToNext());
        }
        return dd;
    }

    @Override
    public List<DbData> queryDatalist(String tablename) {
//        查询data列表
        //        可以复用
        List<DbData> dataList=new ArrayList<>();
        Cursor cursor=database.query(tablename,null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do{
                DbData da=new DbData();
                da.setCoverpath(cursor.getString(cursor.getColumnIndex("coverpath")));
                da.setCommentCount(cursor.getString(cursor.getColumnIndex("commentcount")));
                da.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                da.setContent(cursor.getString(cursor.getColumnIndex("content")));
                da.setPosterId(cursor.getString(cursor.getColumnIndex("posterid")));
                da.setPosterScreenName(cursor.getString(cursor.getColumnIndex("posterscreenname")));
                da.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                da.setTags(cursor.getString(cursor.getColumnIndex("tags")));
                if(cursor.getInt(cursor.getColumnIndex("islike"))==1)
                    da.setIslike(true);
                else
                    da.setIslike(false);
                da.setImageUrls(new String[]{ da.getCoverpath() });
                dataList.add(da);
            }
            while (cursor.moveToNext());
        }
        //     将数据反向
//        因为数据库最旧的 应该放在最后
        Collections.reverse(dataList);
        return dataList;
    }

    @Override
    public void SaveContentData(List<Data> data2) {
        //        名字在帮助类里面已经设好
        List<Data> data=data2;
//        在这里执行的应该是最新的数据先放入数据这是不合理的所以链表应该反转
        Collections.reverse(data);
        for(Data data1:data) {
            {
//               如果当前url在数据库中不存在 就放入数据库
                boolean isExists=false;
                for (String ss:queryUrls(TABLE_CONTENT))
                {
                    if(ss.equals(data1.getUrl()))
                        isExists=true;
                    if(isExists)
                        break;
                }
                if(!isExists) {
                    MyLog.d(TAG,"存入数据库。");
                    StringBuffer buffer = new StringBuffer();
                    ContentValues values = new ContentValues();
                    values.put("url", data1.getUrl());
                    values.put("commentcount", data1.getCommentCount());
                    values.put("content", data1.getContent());
                    values.put("posterid", data1.getPosterId());
                    values.put("title", data1.getTitle());
                    values.put("posterscreenname", data1.getPosterScreenName());
                    values.put("publishdatestr", data1.getPublishDateStr());
                    values.put("islike",false);
                    if (data1.getImageUrls() != null)
                        for (String s : data1.getImageUrls()) {
                            values.put("coverpath", data1.getImageUrls()[0]);
                            buffer.append(s);
                        }
                    values.put("imageurls", buffer.toString());
                    database.insert(TABLE_CONTENT, null, values);
                }
            }
        }
    }

    @Override
    public void DeleteFavorite(DbData data) {
//        删除收藏 同时该data在数据库也应该将收藏设为false
        database.delete("favorite","url=?",new String[]{data.getUrl()});
        //        更新该data在数据库的收藏值
        ContentValues values=new ContentValues();
        values.put("islike",false);
        database.update(TABLE_CONTENT,values,"url=?",new String[]{data.getUrl()});
    }

    @Override
    public void SaveFavorite(DbData data) {
//        收藏放入数据库
        StringBuffer buffer = new StringBuffer();
        ContentValues values = new ContentValues();
        values.put("url", data.getUrl());
        values.put("commentcount", data.getCommentCount());
        values.put("content", data.getContent());
        values.put("posterid", data.getPosterId());
        values.put("title", data.getTitle());
        values.put("posterscreenname", data.getPosterScreenName());
        values.put("publishdatestr", data.getPublishDateStr());
        values.put("islike",true);
        if (data.getImageUrls() != null)
            for (String s : data.getImageUrls()) {
                values.put("coverpath", data.getImageUrls()[0]);
                buffer.append(s);
            }
        values.put("imageurls", buffer.toString());
        database.insert("favorite",null,values);
        values.clear();
//        更新该data在数据库的收藏值
        values.put("islike",true);
        database.update(TABLE_CONTENT,values,"url=?",new String[]{data.getUrl()});
    }

    @Override
    public void setDatabases(SQLiteDatabase database) {
        this.database=database;
    }

    @Override
    public void saveCoverPath(String path,String url) {
//        coverpath预先放的就是url
        ContentValues values=new ContentValues();
        values.put("coverpath",path);
        database.update("content",values,"coverpath=?",new String[]{url});
        database.update("favorite",values,"coverpath=?",new String[]{url});
    }

}
