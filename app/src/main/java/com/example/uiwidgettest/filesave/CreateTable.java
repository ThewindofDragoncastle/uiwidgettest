package com.example.uiwidgettest.filesave;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 40774 on 2017/7/26.
 */
//每次升级要删除原来的数据
public class CreateTable extends SQLiteOpenHelper
{
    private Context mcontext;
    private String hero="create table hero (" +
            "id integer primary key autoincrement," +
            "name text," +
            "force integer," +
            "wit integer," +
            "virtue integer)";
    private String Searchherodata="create table Searchherodata (" +
            "id integer primary key autoincrement," +
            "loyal integer," +
            "exp integer)";
    public CreateTable(Context context, String name, SQLiteDatabase.CursorFactory factory, int vertion)
    {
        super(context,name,factory,vertion);
        mcontext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("位置：执行建表之前的语句 ","如果打印则说明成功！");
        db.execSQL(hero);
        db.execSQL(Searchherodata);
        Log.d("位置：执行建表之后的语句 ","如果打印则说明成功！");
        Toast.makeText(mcontext,"hero Searchherodata创造成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists hero");
        db.execSQL("drop table if exists Searchherodata");
        onCreate(db);
    }
}
