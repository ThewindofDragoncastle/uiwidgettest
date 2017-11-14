package com.example.uiwidgettest.designpattern.support.buildermode;

/**
 * Created by 40774 on 2017/11/6.
 */

public class Computer {
//    组件
    private String  mCpu;
    private String mMainboard;
    private String mRam;
//   安装
    public void setmCpu(String mCpu) {
        this.mCpu = mCpu;
    }

    public void setmMainboard(String mMainboard) {
        this.mMainboard = mMainboard;
    }

    public void setmRam(String mRam) {
        this.mRam = mRam;
    }
//    获得

    public String getmCpu() {
        return mCpu;
    }

    public String getmMainboard() {
        return mMainboard;
    }

    public String getmRam() {
        return mRam;
    }
}
