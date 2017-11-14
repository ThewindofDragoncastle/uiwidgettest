package com.example.uiwidgettest.winddragonnews.model.newsimage;

import com.example.uiwidgettest.winddragonnews.presenter.DataPresenter;

import java.net.URL;

/**
 * Created by 40774 on 2017/11/12.
 */

public interface ImageDownListener {
    void setDataPresenter(DataPresenter presenter);
    void execute(URL url);
}
