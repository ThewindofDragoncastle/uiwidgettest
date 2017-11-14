package com.example.uiwidgettest.designpattern.supportinterface;

import android.support.v4.app.Fragment;

/**
 * Created by 40774 on 2017/11/3.
 */

public interface Designpattern {
    void defaultMode();
    void singleTon();
    void simpleFactory();
    void FactoryMethod();
    void BuilderMode();
    void changeToFragment(Fragment fragment,String text);
}
