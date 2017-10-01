package com.example.uiwidgettest.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ChattranscriptsContentProvider extends ContentProvider {
    private static final String AUTHORITY="com.example.uiwidgettest.herodatacontentprovider";
    private static final int REQUESTALLCHAT=1;
    private static UriMatcher uriMatcher;
    private ChatTranscripts chatTranscripts;
    static
    {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"chattranscripts/#",REQUESTALLCHAT);
    }
    public ChattranscriptsContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        if(uriMatcher.match(uri)==REQUESTALLCHAT)
        return "vnd.android.cursor.item/vnd."+AUTHORITY+".chattranscripts";
        else return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
       chatTranscripts=new ChatTranscripts(getContext(),"mychat",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase=chatTranscripts.getReadableDatabase();
        if(uriMatcher.match(uri)==REQUESTALLCHAT)
        {
            Cursor cursor=sqLiteDatabase.query("chattranscripts",projection,"id=?",
                    new String[]{uri.getPathSegments().get(1)},null,null,sortOrder);
            return cursor;
        }
        else
            return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
