package com.example.uiwidgettest.designpattern.support.buildermode;

/**
 * Created by 40774 on 2017/11/6.
 */

public class Direcror {
//    A商人的组装规范
//    用户不需要知道是谁安装，反正调用安装类就ok
    Builder mbuilder=null;
    private final String MERCHANT="（新世纪安装）";
    public Direcror(Builder builder)
    {
        this.mbuilder=builder;
    }
    public Computer createComputer(String cpu,String mainboard,String Ram)
    {
        mbuilder.buildMainboard(mainboard+MERCHANT);
        mbuilder.buildRam(Ram+MERCHANT);
        mbuilder.buildCpu(cpu+MERCHANT);
        return mbuilder.create();
    }
}
