package com.example.uiwidgettest.winddragonnews.HandleData;

/**
 * Created by 40774 on 2017/11/14.
 */

public class DbData extends Data {
//扩展json数据的dbdata 以便于存入数据库，已经类来操作
    private String coverpath;
    public boolean islike;

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public boolean islike() {

        return islike;
    }

    public void setCoverpath(String coverpath) {
        this.coverpath = coverpath;
    }

    public String getCoverpath() {
        return coverpath;
    }


}
