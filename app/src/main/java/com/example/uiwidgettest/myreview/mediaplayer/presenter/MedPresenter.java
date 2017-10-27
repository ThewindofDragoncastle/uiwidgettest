package com.example.uiwidgettest.myreview.mediaplayer.presenter;

import android.widget.Toast;

import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractChat;
import com.example.uiwidgettest.myreview.mediaplayer.model.Download;
import com.example.uiwidgettest.myreview.mediaplayer.model.DownloadStandard;
import com.example.uiwidgettest.myreview.mediaplayer.presenter.PresenterContract.DownloadStatus;

import java.net.URL;

import static com.example.uiwidgettest.myreview.mediaplayer.presenter.PresenterContract.*;

/**
 * Created by 40774 on 2017/10/25.
 */

public class MedPresenter implements DownloadStatus
        ,setPresenterInterface, PresenterStandard{
    private static  MedPresenter medPresenter;
    private final int DOWNLOAD=0;
    private final int CANCEL=1;
    private final int PAUSE=2;
    private  MedPresenter()
    {}
    public static  MedPresenter getInstance()
    {
        if(medPresenter==null)
            medPresenter=new  MedPresenter();
        return medPresenter;
    }
    private DownloadStandard standard;
    private PresenterCallback view;
    @Override
    public void start() {
        view.ShowProgressBar();
        view.ShowToast();
    }

    @Override
    public void failed() {
         view.ShowFail();
    }

    @Override
    public void proport(int progress) {
          view.ProgressBarChange(progress);
    }

    @Override
    public void complete() {
           view.HideProgressBar();
    }

    @Override
    public void setPresenterCallback(PresenterCallback callback) {
        view=callback;
    }

    @Override
    public void setDownloadStandard(DownloadStandard standard) {
          this.standard=standard;
    }

    @Override
    public void execute(URL url) {
        standard.setDownloadStatus(DOWNLOAD);
          standard.execute(url);
    }

    @Override
    public void pause() {
        standard.setDownloadStatus(PAUSE);
    }

    @Override
    public void cancel() {
        standard.setDownloadStatus(CANCEL);
    }
}
