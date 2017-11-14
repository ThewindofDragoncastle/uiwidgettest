package com.example.uiwidgettest.designpattern.support.buildermode;

/**
 * Created by 40774 on 2017/11/6.
 */

public abstract class Builder {
//    组装规范
    public abstract void buildCpu(String cpu);
    public abstract void buildMainboard(String maiboard);
    public abstract void buildRam(String Ram);
    public abstract Computer create();
}
