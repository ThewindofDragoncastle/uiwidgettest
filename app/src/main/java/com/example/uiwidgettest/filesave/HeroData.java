package com.example.uiwidgettest.filesave;

import org.litepal.crud.DataSupport;

/**
 * Created by 40774 on 2017/7/28.
 */
//操作的是herodata数据库 而不是hero数据库
public class HeroData extends DataSupport {
    private int id;
    private String title;//称号
    private int height;
    private int energy;
    private String sex;

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return sex;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getEnergy() {
        return energy;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }
}
