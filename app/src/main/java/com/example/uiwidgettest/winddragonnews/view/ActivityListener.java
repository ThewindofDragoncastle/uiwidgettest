package com.example.uiwidgettest.winddragonnews.view;

import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.News;

import java.util.List;

/**
 * Created by 40774 on 2017/11/10.
 */

public interface ActivityListener {
    void start();
    void initDatabases();
    void initMvp();
    void initDrawerlayout();
//    给碎片加载数据
    void loadData();

//    切换碎片
    void changeFragment(int i);
    void ShowContentView(News news);

//    从数据库查询新闻的url

}
