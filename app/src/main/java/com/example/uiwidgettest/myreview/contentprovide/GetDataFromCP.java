package com.example.uiwidgettest.myreview.contentprovide;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.myreview.broadcast.MyBroadcast;

/**
 * Created by 40774 on 2017/10/5.
 */

public class GetDataFromCP {
    //从内容提供器中获取数据
    public String GetData(Context context,Uri uri)
    {
        StringBuffer extendData=new StringBuffer();
        Cursor cursor=context.getContentResolver().query(uri,null,null,null,null);
        if(cursor!=null)
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
            return extendData.toString();
        }
        return null;
    }
}
