package com.example.uiwidgettest.designpattern.support.buildermode;

/**
 * Created by 40774 on 2017/11/6.
 */

public class Merchant extends Builder {
    //    组装商人A
    private Computer mcomputer=new Computer();
    @Override
    public void buildCpu(String cpu) {
         mcomputer.setmCpu(cpu);
    }

    @Override
    public void buildMainboard(String maiboard) {
     mcomputer.setmMainboard(maiboard);
    }

    @Override
    public void buildRam(String Ram) {
       mcomputer.setmRam(Ram);
    }

    @Override
    public Computer create() {
        return mcomputer;
    }

}
