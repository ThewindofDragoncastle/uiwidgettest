package com.example.uiwidgettest.materialdesign;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by 40774 on 2017/8/23.
 */

public class Hero implements Serializable{
    private int Image;
    private String name;
    private URL url;
    public int getImage() {
        return Image;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setImage(int image) {
        this.Image = image;
    }

    public Hero(URL url, String name)
   {
       this.url = url;
       this.name=name;
   }
}
