package com.example.uiwidgettest.myreview.mediaplayer.model;

import android.os.Environment;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.mvpupdatechat.bufferdata.Bufferdata;
import com.example.uiwidgettest.mvpupdatechat.presenter.BaseModelService;
import com.example.uiwidgettest.myreview.mediaplayer.presenter.PresenterContract;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 40774 on 2017/10/25.
 */
//不开启服务的下载
public class Download implements DownloadStandard,PresenterContract.setDownloadInterface {
    private static Download download;
    private volatile int       currentstatus=0;
    private final int DOWNLOAD=0;
    private final int CANCEL=1;
    private final int PAUSE=2;
    private final String TAG=this.getClass().getName();
    private Download()
    {}
    public static Download getInstance()
    {
        if(download==null)
            download=new Download();
        return download;
    }
//异步处理
    private Observer<Integer> obs=new Observer<Integer>() {
    Disposable disposable=null;
    @Override
    public void onSubscribe(@NonNull Disposable disposable1) {
         disposable=disposable1;
         status.start();
    }

    @Override
    public void onNext(@NonNull Integer integer) {
        status.proport(integer);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        MyLog.d(TAG,"当前线程："+Thread.currentThread().getName());
        status.failed();
    }

    @Override
    public void onComplete() {
       status.complete();
        if(disposable!=null) {
            disposable.dispose();
            MyLog.d(TAG,"下载完成订阅取消");
        }
    }
};
    private PresenterContract.DownloadStatus status;
    private final String FILE_MKDIRS= Environment.getExternalStorageDirectory()+
            File.separator+"开发专用文件夹"+File.separator+"movie";
    @Override
    public void execute(final URL url) {
        File medirs=new File(FILE_MKDIRS);
        String urlname=url.toString().substring(url.toString().lastIndexOf(File.separator));
        final File file=new File(FILE_MKDIRS+File.separator+urlname);
        MyLog.d(TAG,"文件地址："+file.getPath());
        MyLog.d(TAG,"文件夹地址："+medirs.getPath());
        if(!medirs.exists())
            medirs.mkdirs();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> observableEmitter) throws Exception {
                MyLog.d(TAG,"测试开始执行");
                     final long contentlength=file.length();
               final long length=getDownloadFileSize(url);
                MyLog.d(TAG,"待下载的文件大小："+length+"\n已下载的文件大小："+contentlength);
                if(contentlength==length)
                {
                    observableEmitter.onComplete();
                    return;
                }
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(url).addHeader("RANGE","bytes="+contentlength+"-").build();
                Call call=client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        MyLog.d(TAG,"访问网络文件失败"+url.toString()+"失败原因："+e.toString());
                        observableEmitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        MyLog.d(TAG,"访问网络成功");
                        observableEmitter.onNext(0);
                        InputStream in=null;
                        RandomAccessFile randfile=null;
                        BufferedInputStream buin=null;
                        try {
                            in=response.body().byteStream();
                            randfile=new RandomAccessFile(file,"rw");
                            //忽略掉已下载的文件
                            randfile.seek(contentlength);
                            buin=new BufferedInputStream(in);
                            byte[] bs=new byte[1024];
                            int len;
                            long total=contentlength;
                            long progress=0;
                            while ((len=buin.read(bs))!=-1)
                            {
                                if(currentstatus==DOWNLOAD) {
                                    total = total + len;
                                    randfile.write(bs, 0, len);
                                    if (total * 100 / length != progress) {
                                        progress = total * 100 / length;
                                        //这里是异步对速度影响不大
                                        observableEmitter.onNext((int) progress);
                                    }
                                }else if(currentstatus==PAUSE)
                                    return;
                                else if (currentstatus==CANCEL)
                                {
                                    file.delete();
                                    return;
                                }

                            }
                            observableEmitter.onComplete();
                        }catch (IOException e)
                        {
                            observableEmitter.onError(e);
                        }
                        finally {
    try {
        MyLog.d(TAG,"文件关闭");
        if(randfile!=null)
        randfile.close();
        if(in!=null)
            in.close();
        if(buin!=null)
            buin.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
                        }

                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(obs);

    }

    @Override
    public void setDownloadStatusInterface(PresenterContract.DownloadStatus status) {
        this.status=status;
    }

    @Override
    public void setDownloadStatus(int status) {
        currentstatus=status;
    }
    private long getDownloadFileSize(URL url)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        Response response=null;
        try {
            response=client.newCall(request).execute();
            if(response!=null)
                return response.body().contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(response!=null)
            response.close();
        }
        return 0;
    }
}
