package com.example.uiwidgettest.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.uiwidgettest.filesave.CreateTable;
//自建内容提供器 提供uri接口给外部访问
public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }
    private static final int HERO_ITEM=1;
    private static final int HERO_CONTENT=2;
    private static final int SEARCHHERODATA_ITEM=3;
    private static final int SEARCHHERODATA_CONTENT=4;
    private static UriMatcher uriMatcher;
    private CreateTable createTable;
    static
    {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.uiwidgettest.provider","hero/1",HERO_ITEM);
        //数字
        uriMatcher.addURI("com.example.uiwidgettest.provider","hero",HERO_CONTENT);
        //字符串
        uriMatcher.addURI("com.example.uiwidgettest.provider","Searchherodata/1",SEARCHHERODATA_ITEM);
        uriMatcher.addURI("com.example.uiwidgettest.provider","Searchherodata",SEARCHHERODATA_CONTENT);
    }
    @Nullable
    public boolean onCreate()
    {
       createTable=new CreateTable(getContext(),"hero.db",null,3);
        return true;
    }
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase=createTable.getWritableDatabase();
        Uri newuri=null;long id;
        switch (uriMatcher.match(uri))
        {
            case HERO_CONTENT:return uri;
            case SEARCHHERODATA_CONTENT:return uri;
            case HERO_ITEM:
             id=sqLiteDatabase.insert("hero",null,values);
                newuri=uri.parse("content://com.example.uiwidgettest.provider/hero/"+id);
                return newuri;
            case SEARCHHERODATA_ITEM: id=sqLiteDatabase.insert("Searchherodata",null,values);
                Log.d("MContentP:",id+"");
                newuri=uri.parse("content://com.example.uiwidgettest.provider/Searchherodata/"+id);
                return newuri;
              default:
                  Log.d("MContentP:","并未匹配到数据");
                  return newuri;
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase=createTable.getWritableDatabase();
        int id=0;String oldid;
        switch (uriMatcher.match(uri))
        {
            case HERO_CONTENT:
               id=sqLiteDatabase.delete("hero",selection,selectionArgs);
                Log.d("MCP（删除）:","匹配hero全项成功");
                break;
            case HERO_ITEM:
                oldid=uri.getPathSegments().get(1);
                id=sqLiteDatabase.delete("hero",selection,selectionArgs);
                Log.d("MCP（删除）:","匹配hero部分项成功 Oldid="+oldid);
                break;
            case SEARCHHERODATA_CONTENT:
                id=sqLiteDatabase.delete("Searchherodata",selection,selectionArgs);
                Log.d("MCP（删除）:","匹配Searchherodata全项成功");
                break;
            case SEARCHHERODATA_ITEM:
                Log.d("MCP（删除）:","匹配Searchherodata部分项成功");
                oldid=uri.getPathSegments().get(1);
                id=sqLiteDatabase.delete("Searchherodata","id=?",new String[]{oldid});
                break;
            default:  Log.d("MCP（删除）:","未匹配任何项");
        }
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
       SQLiteDatabase sqLiteDatabase=createTable.getWritableDatabase();
        int newid=0;
        String Oldid;
        switch (uriMatcher.match(uri))
        {
            case HERO_ITEM:
                Oldid=uri.getPathSegments().get(1);
                newid=sqLiteDatabase.update("hero",values,selection,selectionArgs);
                break;
            case HERO_CONTENT:
                newid=sqLiteDatabase.update("hero",values,selection,selectionArgs);
                break;
            case SEARCHHERODATA_CONTENT:
                newid=sqLiteDatabase.update("Searchherodata",values,selection,selectionArgs);
                break;
            case SEARCHHERODATA_ITEM:
                Oldid=uri.getPathSegments().get(1);
                newid=sqLiteDatabase.update("Searchherodata",values,selection,selectionArgs);
                break;
        }
        return newid;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase=createTable.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri))
        {
            case HERO_ITEM:
                //对于uri需要解析
                String heroId=uri.getPathSegments().get(1);
                cursor=sqLiteDatabase.query("hero",projection,"id>? and id<?",new String[] {heroId,5+""},null,null,sortOrder);
                break;
            case SEARCHHERODATA_CONTENT:
                cursor=sqLiteDatabase.query("Searchherodata",projection,selection,selectionArgs,null,null,sortOrder);
                Log.d("MCP:","Searchherodata全项查询成功");
                break;
            case SEARCHHERODATA_ITEM:
                String SearchherodataId=uri.getPathSegments().get(1);
                cursor=sqLiteDatabase.query("Searchherodata",projection,"id=?",new String[]{SearchherodataId},null
                        ,null,sortOrder);
                break;
            case HERO_CONTENT:
                cursor=sqLiteDatabase.query("hero",projection,selection,selectionArgs,null,null,sortOrder);
                Log.d("MCP:","hero全项查询成功");
                break;

            default:
                Log.d("MCP:","查询没有成功匹配");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri))
        {
            case HERO_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.uiwidgettest.provider.hero";
//            返回id=1
            case SEARCHHERODATA_CONTENT:
                return "vnd.android.cursor.dir/vnd.com.example.uiwidgettest.provider.Searchherodata";

            case SEARCHHERODATA_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.uiwidgettest.provider.Searchherodata";
            case HERO_CONTENT:
                return  "vnd.android.cursor.dir/vnd.com.example.uiwidgettest.provider.hero";
            default:
                return null;
        }
    }
}
