package com.example.uiwidgettest.myreview.contentprovide;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uiwidgettest.MyLog;

/**
 * Created by 40774 on 2017/10/2.
 */
//建表语句
public class HeroDatabase extends SQLiteOpenHelper
{
    private final String TAG="HeroDatabase:";
private final String CREATE_TABLE="create table if not exists hero (" +
        "id integer primary key autoincrement,"+
        "name varchar(20)," +
        "cognoment varchar(20)," +
        "energy integer," +
        "force integer," +
        "gender boolean," +
        "wit integer," +
        "loyal integer," +
        "virtue integer"+
        ")";
    public HeroDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            MyLog.d(TAG,"数据库建立");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("drop table if exists hero");
         db.execSQL(CREATE_TABLE);
    }
}
