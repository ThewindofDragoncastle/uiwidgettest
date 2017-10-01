package com.example.uiwidgettest.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uiwidgettest.MyLog;

/**
 * Created by 40774 on 2017/9/17.
 */

public class ChatTranscripts extends SQLiteOpenHelper {
    private final String CEATETABLE="create table if not exists chattranscripts " +
            "(" +"id integer primary key autoincrement,recipent text,account text,addresser text,message text"+
            "); ";
    public ChatTranscripts(Context context, String SQLname, SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,SQLname,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
           db.execSQL(CEATETABLE);
        MyLog.d("","创建数据库成功!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists chattranscripts");
        onCreate(db);
    }
}
