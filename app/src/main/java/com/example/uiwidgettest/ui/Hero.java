package com.example.uiwidgettest.ui;

/**
 * Created by 40774 on 2017/7/2.
 */

public class Hero {
    private String name;
    private int image;

    public Hero(int image,String name) {
        this.image = image;
        this.name=name;
    }

    public int getImage() {
        return image;
    }
    public void setName(String name) {
        this.name=name;
    }
    public void setImage(int Image) {
        this.image=Image;
    }
    public String getName() {
        return name;
    }
}
