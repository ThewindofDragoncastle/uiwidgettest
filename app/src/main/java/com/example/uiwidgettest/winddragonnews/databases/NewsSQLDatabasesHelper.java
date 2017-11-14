package com.example.uiwidgettest.winddragonnews.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uiwidgettest.MyLog;

/**
 * Created by 40774 on 2017/11/13.
 */

public class NewsSQLDatabasesHelper extends SQLiteOpenHelper {
//    这个类建立数据库 和 表
    private final String TAG=getClass().getName();
    private final String  TABLE_NAME="content";
    private final String  TABLE_FAVORITE="create table if not exists   "+ "favorite (" +
            " id integer primary key autoincrement," +
            "account text,"+
            "posterid text," +
            "content text," +
            "posterscreenname text," +
            "tags text," +
            "url text," +
            "publishdateStr text," +
            "title text," +
            "publishdate varchar(20)," +
            "commentcount varchar(10)," +
            "imageurls text," +
            "coverpath text,"+
            "islike boolean,"+
            "newsid text"+
            "); ";
    private final String  TABLE_CONTENT= "create table if not exists "+TABLE_NAME+" (" +
            "id integer primary key autoincrement," +
            "posterid text," +
            "content text," +
            "posterscreenname text," +
            "tags text," +
            "url text," +
            "publishdatestr text," +
            "title text," +
            "publishdate varchar(20)," +
            "commentcount varchar(10)," +
            "imageurls text," +
            "coverpath text,"+
            "islike boolean,"+
            "newsid text"+
            "); ";

    public NewsSQLDatabasesHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "news", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        新闻数组
        db.execSQL(TABLE_CONTENT);
//        收藏列表
        db.execSQL(TABLE_FAVORITE);
        MyLog.d(TAG,"数据库建立");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MyLog.d(TAG,"删除成功");
        db.execSQL("drop table if exists content");
        db.execSQL("drop table if exists favorite");
        onCreate(db);
    }
}
