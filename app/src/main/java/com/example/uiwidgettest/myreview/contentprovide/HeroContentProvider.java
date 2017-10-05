package com.example.uiwidgettest.myreview.contentprovide;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.uiwidgettest.MyLog;

public class HeroContentProvider extends ContentProvider {
    private static final int ALLTABEL=0;
    private static final int TABLEALLDATA=1;
    private static final int IDBELOW5=2;
    private static final String AUTHORITY="com.example.uiwidgettest.heroprovider";
    private static UriMatcher uriMatcher;
    private HeroDatabase heroDatabase;
    static
    {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"hero/4",TABLEALLDATA);
        uriMatcher.addURI(AUTHORITY,"*",ALLTABEL);
        uriMatcher.addURI(AUTHORITY,"hero/5",IDBELOW5);
    }
    public HeroContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
      return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri))
        {
            case ALLTABEL:
                return "vnd.android.cursor.dir/vnd."+AUTHORITY+".hero";
            case TABLEALLDATA:
                return "vnd.android.cursor.item/vnd."+AUTHORITY+".hero";
            case IDBELOW5:
                return "vnd.android.cursor.item/vnd."+AUTHORITY+".hero";
        }
       return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        heroDatabase=new HeroDatabase(getContext(),"reviewhero",null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor=null;
        SQLiteDatabase sqLiteDatabase=heroDatabase.getReadableDatabase();
        MyLog.d("CP:","uri="+uri+" 搭配："+uriMatcher.match(uri));
        switch (uriMatcher.match(uri))
        {
            case ALLTABEL:
                //查询所有应该根据访问用户的需求查询
                cursor=sqLiteDatabase.query("hero",projection,selection,selectionArgs,null,null,sortOrder);
                return cursor;
            case TABLEALLDATA:
                cursor=sqLiteDatabase.query("hero",projection,"force<?",new String[]{80+""},null,null,sortOrder);
                return cursor;
            case IDBELOW5:
                cursor=sqLiteDatabase.query("hero",projection,"id<?",new String[]{5+""},null,null,sortOrder);
                return cursor;
        }
    return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
     return 0;
    }
}
