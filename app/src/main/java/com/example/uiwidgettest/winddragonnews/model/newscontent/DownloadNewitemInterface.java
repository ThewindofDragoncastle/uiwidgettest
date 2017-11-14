package com.example.uiwidgettest.winddragonnews.model.newscontent;

import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.News;
import com.example.uiwidgettest.winddragonnews.presenter.DataPresenter;

import java.net.URL;
import java.util.List;

/**
 * Created by 40774 on 2017/11/8.
 */

public interface DownloadNewitemInterface {
    interface Down
    {
        void RequestData(URL url);
        void setPresenterInterface(DataPresenter presenterInterface);
    }
}
