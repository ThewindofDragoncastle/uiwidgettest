package com.example.uiwidgettest.materialdesign;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uiwidgettest.MyLog;

/**
 * Created by 40774 on 2017/8/26.
 */

public class HeroMessagedatabasehelper extends SQLiteOpenHelper {
    private final String Databasedata="create table HeroMessage ("+
            "id integer primary key autoincrement," +
            "name text," +
            "disorder integer," +
            "narrative text)"

    ;
    public HeroMessagedatabasehelper(Context context, String name, SQLiteDatabase.CursorFactory cursor, int version)
    {
        super(context,name,cursor,version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Databasedata);
        MyLog.d("英雄数据库：","建立成功！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists HeroMessage");
        onCreate(db);
    }
}
