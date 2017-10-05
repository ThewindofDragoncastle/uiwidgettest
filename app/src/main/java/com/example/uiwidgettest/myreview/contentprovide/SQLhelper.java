package com.example.uiwidgettest.myreview.contentprovide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.myreview.json.Hero;
import com.example.uiwidgettest.news.Content;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/10/3.
 */

public class SQLhelper {
    //自建帮组处理数据库
    private final String  TAG="SQLhelper:";

    public String getSqlContent(SQLiteDatabase database)
    {
        StringBuilder extendData=new StringBuilder();
        Cursor cursor=database.query("hero",null,null,null,null,null,null);
        if (cursor.moveToFirst())
        {
          do
            {
                extendData.append("行："+cursor.getString(cursor.getColumnIndex("id"))+"\n");
                extendData.append("姓名："+cursor.getString(cursor.getColumnIndex("name"))+"\n");
                extendData.append("字："+cursor.getString(cursor.getColumnIndex("cognoment"))+"\n");
               if(cursor.getInt(cursor.getColumnIndex("gender"))==1)
                   extendData.append("性别：男"+"\n");
                else
                   extendData.append("性别：女"+"\n");
                extendData.append("体力："+cursor.getInt(cursor.getColumnIndex("energy"))+"\n");
                extendData.append("武力："+cursor.getInt(cursor.getColumnIndex("force"))+"\n");
                extendData.append("智慧："+cursor.getInt(cursor.getColumnIndex("wit"))+"\n");
                extendData.append("忠诚："+cursor.getInt(cursor.getColumnIndex("loyal"))+"\n");
                extendData.append("德行："+cursor.getInt(cursor.getColumnIndex("virtue"))+"\n");
            }  while (cursor.moveToNext());
            cursor.close();
        }
        MyLog.i(TAG,extendData.toString());
        return extendData.toString();
    }
    public List<Hero> getListHero(SQLiteDatabase database)
    {
        StringBuilder extendData=new StringBuilder();
        List<Hero> heros=new ArrayList<>();
        Cursor cursor=database.query("hero",null,null,null,null,null,null);
        if (cursor.moveToFirst())
        {
            do
            {
                Hero hero=new Hero();
                hero.setId(cursor.getInt(cursor.getColumnIndex("id")));
                hero.setName(cursor.getString(cursor.getColumnIndex("name")));
                hero.setCognomen(cursor.getString(cursor.getColumnIndex("cognoment")));
                hero.setEnergy(cursor.getInt(cursor.getColumnIndex("energy")));
                hero.setForce(cursor.getInt(cursor.getColumnIndex("force")));
                hero.setWit(cursor.getInt(cursor.getColumnIndex("wit")));
                hero.setLoyal(cursor.getInt(cursor.getColumnIndex("loyal")));
                hero.setVirtue(cursor.getInt(cursor.getColumnIndex("virtue")));
                if(cursor.getInt(cursor.getColumnIndex("gender"))==1)
                hero.setGender(true);
                else
                    hero.setGender(false);
                heros.add(hero);
            }  while (cursor.moveToNext());
            cursor.close();
        }
        MyLog.i(TAG,extendData.toString());
        return heros;
    }
    public void Update(SQLiteDatabase database,Hero hero,int Id)
    {
        ContentValues values=new ContentValues();
        values.put("name",hero.getName());
        values.put("cognoment",hero.getCognomen());
        values.put("gender",hero.getGender());
        values.put("energy",hero.getEnergy());
        values.put("force",hero.getForce());
        values.put("wit",hero.getWit());
        values.put("loyal",hero.getLoyal());
        values.put("virtue",hero.getVirtue());
        database.update("hero",values,"id=?",new String[]{Id+""});
    }
    public void insert(SQLiteDatabase database,Hero hero)
    {
        ContentValues values=new ContentValues();
        values.put("name",hero.getName());
        values.put("cognoment",hero.getCognomen());
        values.put("gender",hero.getGender());
        values.put("energy",hero.getEnergy());
        values.put("force",hero.getForce());
        values.put("wit",hero.getWit());
        values.put("loyal",hero.getLoyal());
        values.put("virtue",hero.getVirtue());
        database.insert("hero",null,values);
        values.clear();
    }
    public Hero initHero()
    {
        Hero hero=new Hero();
        hero.setName("赵云");
        hero.setCognomen("子龙");
        hero.setEnergy(98);
        hero.setWit(86);
        hero.setForce(97);
        hero.setGender(true);
        hero.setVirtue(95);
        hero.setLoyal(97);
        return hero;
    }
}
