package com.example.uiwidgettest.myreview.mediaplayer.presenter;

import com.example.uiwidgettest.myreview.mediaplayer.model.Download;
import com.example.uiwidgettest.myreview.mediaplayer.model.DownloadStandard;

import java.net.URL;

import static com.example.uiwidgettest.myreview.mediaplayer.presenter.PresenterContractStandard.*;

/**
 * Created by 40774 on 2017/10/25.
 */

public interface PresenterContract  {
    interface DownloadStatus extends Model.Callback {
        void start();
        void failed();
        void proport(int progress);
        void complete();
    }
    interface View extends PresenterContractStandard.View.Down
    {
        //开始       下载
        void Start(URL url);
    }
    interface setDownloadInterface
    {
        void setDownloadStatusInterface(DownloadStatus status);
    }
    interface setPresenterInterface
    {
        void setPresenterCallback(PresenterCallback callback);
        void setDownloadStandard(DownloadStandard standard);
    }
    interface PresenterStandard extends PresenterContractStandard.View.Down
    {
        //规范
        void execute(URL url);
        void pause();
        void cancel();
    }
    interface PresenterCallback extends PresenterContractStandard.Presenter.Callback
    {

        void ShowFail();
        //进度条显示
        void ShowProgressBar();
        //进度条改变
        void ProgressBarChange(int progress);
        //下载完成进度条隐藏
        void HideProgressBar();
        void ShowToast();
    }
}
