package com.example.uiwidgettest.winddragonnews.view;

import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.DbData;
import com.example.uiwidgettest.winddragonnews.HandleData.News;

import java.util.List;

/**
 * Created by 40774 on 2017/11/8.
 */

public interface NewsFragmentListener {
    void start();
    void showError(String e);
    interface FormatNewsFragment
    {
//        本身接口规范
        void setDataList(List<DbData> dataList);
        void setImage(String file_path);
        void setPage(int i);
    }
}
