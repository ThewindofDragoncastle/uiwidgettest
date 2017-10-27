package com.example.uiwidgettest.myreview.mediaplayer.model;

import java.net.URL;

/**
 * Created by 40774 on 2017/10/25.
 */

public interface DownloadStandard {
    //启动接口
    void execute(URL url);
    void setDownloadStatus(int status);
}
