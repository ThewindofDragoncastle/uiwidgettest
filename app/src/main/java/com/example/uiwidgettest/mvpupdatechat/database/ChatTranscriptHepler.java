package com.example.uiwidgettest.mvpupdatechat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.myreview.contentprovide.SQLhelper;

/**
 * Created by 40774 on 2017/10/18.
 */

public class ChatTranscriptHepler extends SQLiteOpenHelper {
    public ChatTranscriptHepler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,"dragon", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MyLog.d("ChatTranscriptHepler:","数据库建立成功。");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
