package com.example.uiwidgettest.myreview.contentprovide;

import com.example.uiwidgettest.myreview.json.Hero;

import java.util.IdentityHashMap;

/**
 * Created by 40774 on 2017/10/5.
 */
//回调接口 处理点击编辑或者删除 回调到主页面数据库执行
public interface Onclicksomething {
    boolean delete(int Id);
    void confirm(Hero hero,int Id);
}
