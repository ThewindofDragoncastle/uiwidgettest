package com.example.uiwidgettest.framework.mvp.useclass;

import android.os.Environment;
import android.util.Log;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.framework.mvp.useinterface.LoadTaskCallBack;
import com.example.uiwidgettest.framework.mvp.useinterface.NetTask;
import com.example.uiwidgettest.framework.mvp.useinterface.ObservableTask;

import org.reactivestreams.Subscription;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
 * Created by 40774 on 2017/10/13.
 */

public class DataInfoTask implements NetTask<String>,ObservableTask{
    private final String TAG="DataInfoTask:";
    private static DataInfoTask INSTANCE=null;
    //地址
    private static final String HOST="http://39.108.123.220/www/hero.json";
    private io.reactivex.Observable ob;
    public static DataInfoTask getInstance()
    {
        if(INSTANCE==null)
            INSTANCE=new DataInfoTask();
        return INSTANCE;
    }
    private DataInfoTask()
    {}

    @Override
    public void execute(final String data, final LoadTaskCallBack callBack) {
       getObservable(HOST).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new io.reactivex.Observer<Hero>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                            callBack.onStart();
                    }

                    @Override
                    public void onNext(@NonNull Hero s) {
                           callBack.onSucess(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        MyLog.d("DataInfoTask:","OnError运行的线程；"+Thread.currentThread().getName());
                        callBack.onFailed();
                    }

                    @Override
                    public void onComplete() {
                           callBack.onFinish();
                    }
                });
    }

    @Override
    public io.reactivex.Observable getObservable(String url) {
        if(ob==null)
        ob=io.reactivex.Observable.create(new ObservableOnSubscribe<Hero>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Hero> observableEmitter) throws Exception {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(HOST).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        observableEmitter.onError(new Exception());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data=response.body().string();
                        Hero hero=new Hero();
                        hero.setName(data);
                        MyLog.d(TAG,"data:"+data);
                        observableEmitter.onNext(hero);
                        observableEmitter.onComplete();
                    }
                });
            }
        });
        return ob;
    }
}
