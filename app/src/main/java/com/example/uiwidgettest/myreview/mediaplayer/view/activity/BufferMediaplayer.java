package com.example.uiwidgettest.myreview.mediaplayer.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.mediaplayer.model.Download;
import com.example.uiwidgettest.myreview.mediaplayer.presenter.MedPresenter;
import com.example.uiwidgettest.myreview.mediaplayer.presenter.PresenterContract;
import com.example.uiwidgettest.myreview.mediaplayer.presenter.PresenterContractStandard;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BufferMediaplayer extends AppCompatActivity implements PresenterContract.View,PresenterContract.PresenterCallback {
    private MedPresenter medPresenter;
    private Download download;
    @BindView(R.id.ipinput)
    EditText editText;

    @BindView(R.id.PlayandDownload)
    Button PlayandDownload;
    @BindView(R.id.displayprogress)
    TextView displayprogress;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    //进度条和显示文本的父布局
    @BindView(R.id.linearlayout1)
    LinearLayout progresslinearLayout;
    @BindView(R.id.linearlayout2)
    LinearLayout buttonlinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_mediaplayer);
        ButterKnife.bind(this);
//        请求权限
        if(Build.VERSION.SDK_INT>=23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            else
                start();
        }else
        {
            start();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    start();
        }
                    else
            {
                Toast.makeText(getBaseContext(),"Quit because your refuse.",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    private void start()
    {
        editText.setText("http://10.0.2.2/movie/冰霜女巫的英雄时刻.avi");
        medPresenter=MedPresenter.getInstance();
        download= Download.getInstance();
        medPresenter.setDownloadStandard(download);
        medPresenter.setPresenterCallback(this);
        download.setDownloadStatusInterface(medPresenter);
    }
    @OnClick(R.id.pauseDownload)
    public void onPauseDownload()
    {
        medPresenter.pause();
    }
    @OnClick(R.id.CancelDownload)
    public void onCancelDownload()
    {
        medPresenter.cancel();
    }
    @OnClick(R.id.PlayandDownload)
    public void onClickPlayandDownload()
    {
        try {
            String input=editText.getText().toString();
            if (!input.isEmpty()) {
                URL url = new URL(input);
                Start(url);
            }
            else
                Toast.makeText(getBaseContext(),"请输入数据！",Toast.LENGTH_SHORT).show();
        } catch (MalformedURLException e) {
            Toast.makeText(getBaseContext(),"URL格式错误！",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void Start(URL url) {
         medPresenter.execute(url);
    }

    @Override
    public void ShowFail() {
        Toast.makeText(getBaseContext(),"获取文件失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowProgressBar() {
        progressBar.setProgress(0);
        PlayandDownload.setText("重新下载");
        buttonlinearLayout.setVisibility(View.VISIBLE);
        progresslinearLayout.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
    }

    @Override
    public void ProgressBarChange(int progress) {
        displayprogress.setText("当前进度"+progress+"%　");
        progressBar.setProgress(progress);
        MyLog.d("BufferMediaplayer:","下载进度改变！"+progress);
    }

    @Override
    public void HideProgressBar() {
        PlayandDownload.setText("下载");
        buttonlinearLayout.setVisibility(View.GONE);
        progresslinearLayout.setVisibility(View.GONE);
        Toast.makeText(getBaseContext(),"下载成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowToast() {
        Toast.makeText(getBaseContext(),"下载开始",Toast.LENGTH_SHORT).show();
    }
}
