package com.example.uiwidgettest.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 40774 on 2017/7/30.
 */

public class DIYcontentprovider extends ContentProvider {
    private static final int HERO_ITEM=1;
    private static final int HERO_CONTENT=2;
    private static final int HERODATA_ITEM=3;
    private static final int HERODATA_CONTENT=4;
    private static  UriMatcher uriMatcher;
    static
    {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.uiwidgettest.provider","hero/1*",HERO_ITEM);
        //数字
        uriMatcher.addURI("com.example.uiwidgettest.provider","hero/#",HERO_CONTENT);
        //字符串
        uriMatcher.addURI("com.example.uiwidgettest.provider","herodata/1*",HERODATA_ITEM);
        uriMatcher.addURI("com.example.uiwidgettest.provider","herodata/#",HERODATA_CONTENT);
    }
    @Nullable
    public boolean onCreate()
    {
        return false;
    }
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
      switch (uriMatcher.match(uri))
      {
          case HERO_ITEM:
          break;
          case HERODATA_CONTENT:
              break;
          case HERODATA_ITEM:
              break;
          case HERO_CONTENT:
              break;

          default:
      }
      return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri))
        {
            case HERO_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.uiwidgettest.provider.hero";
//            返回id=1

            case HERODATA_CONTENT:
                return "vnd.android.cursor.dir/vnd.com.example.uiwidgettest.provider.herodata";
            case HERODATA_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.uiwidgettest.provider.herodata";
            case HERO_CONTENT:
                return  "vnd.android.cursor.dir/vnd.com.example.uiwidgettest.provider.hero";
            default:
                return null;
        }
    }
}
