package com.example.uiwidgettest.byservice;

/**
 * Created by 40774 on 2017/8/17.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onPause();
    void onCancel();
    void onSuccess();
    void onFailed();
}
