package com.example.uiwidgettest.myreview.media;

/**
 * Created by 40774 on 2017/10/6.
 */

public class SongorMovie {
    private int SongorMovie;
    private String path;
    private String name;
    private boolean Islast=false;

    public void setIslast(boolean islast) {
        Islast = islast;
    }

    public boolean islast() {
        return Islast;
    }

    public SongorMovie(int SongorMovie, String path, String name)
    {
        this.SongorMovie=SongorMovie;
        this.path=path;
        this.name=name;
    }

    public int getSongorMovie() {
        return SongorMovie;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
