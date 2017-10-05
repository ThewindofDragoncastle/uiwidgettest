package com.example.uiwidgettest.myreview.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/10/2.
 */
//字符串转JSON语句
public class StringtoJson {
    //共三种方法
    public void Stringcat(String[] mystring)
    {
//直接拼接

    }
    public JSONArray Listcat(List<Hero> heros)
    {
//数组拼接
        try {
        JSONArray array=new JSONArray();
        for (Hero hero:heros) {
            JSONObject object = new JSONObject();
            object.put("name",hero.getName());
            object.put("cognomen",hero.getCognomen());
            object.put("energy",hero.getEnergy());
            object.put("force",hero.getForce());
            object.put("wit",hero.getWit());
            object.put("virtue",hero.getVirtue());
            object.put("gender",hero.getGender());
            object.put("loyal",hero.getLoyal());
            array.put(object);
        }
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void Mapcat(String[] mystring)
    {
//map拼接
    }
}
