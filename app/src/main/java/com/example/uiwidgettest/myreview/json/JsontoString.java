package com.example.uiwidgettest.myreview.json;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by 40774 on 2017/10/2.
 */

public class JsontoString {
    private StringBuilder stringBuilder=new StringBuilder("");
    public String toJson(String jsonstring)
    {
        //初始化
        //解析多段数据
        stringBuilder.setLength(0);
        JSONArray array=new JSONArray();
        Gson gson=new Gson();
        List<Hero> heros=gson.fromJson(jsonstring,new TypeToken<List<Hero>>(){}.getType());
        for(Hero hero:heros)
            stringBuilder.append("数据库数据转换为Json在从json解析为字符串\n"+"姓名："+hero.getName()+"\n字："+hero.getCognomen()+
                    "\n性别："+hero.getGender()+"\n体力："+hero.getEnergy()
                    +"\n武力："+hero.getForce()+ "\n智慧："+hero.getWit()+
                    "\n忠诚："+hero.getLoyal()+"\n德行:"+hero.getVirtue()+"\n");
        return stringBuilder.toString();
    }
}
