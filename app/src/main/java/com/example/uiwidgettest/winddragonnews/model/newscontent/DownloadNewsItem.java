package com.example.uiwidgettest.winddragonnews.model.newscontent;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.HandleJsonData;
import com.example.uiwidgettest.winddragonnews.HandleData.News;
import com.example.uiwidgettest.winddragonnews.presenter.DataPresenter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;

/**
 * Created by 40774 on 2017/11/8.
 */

public class DownloadNewsItem implements DownloadNewitemInterface.Down {
//    中间量列表
    private List<DataPresenter> presenters=new ArrayList<>();
    private Observer<News> newsob=new Observer<News>() {
        Disposable disposable;
        @Override
        public void onSubscribe(Disposable disposable) {
            this.disposable=disposable;
        }

        @Override
        public void onNext(News s) {
//           向所有媒介返回
            for (DataPresenter presenter:presenters)
            presenter.returnNewsData(s);
        }

        @Override
        public void onError(Throwable throwable) {
               for (DataPresenter presenter:presenters)
                  presenter.returnError(throwable.toString());
        }

        @Override
        public void onComplete() {
           disposable.dispose();
        }
    };
    @Override
    public void RequestData(final URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                final Request request=new Request.Builder().url(url).build();
                MyLog.d("测试数据","连接API");
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        MyLog.d("测试数据","服务器信息："+e.toString());
                        Observable.create(new ObservableOnSubscribe<News>() {
                            @Override
                            public void subscribe(ObservableEmitter<News> observableEmitter) throws Exception {
                                observableEmitter.onError(e);
                                observableEmitter.onComplete();
                            }
                        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(newsob);
                    }

                    @Override
                    public void onResponse(Call call,final Response response) throws IOException {
                        final String jsondata=response.body().string();
                        HandleJsonData handleJson=new HandleJsonData();
                        final News news;
                       final List<Data> data;
                        news=handleJson.handleNews(jsondata);
                        Observable.create(new ObservableOnSubscribe<News>() {
                            @Override
                            public void subscribe(ObservableEmitter<News> observableEmitter) throws Exception {
                                observableEmitter.onNext(news);
                                observableEmitter.onComplete();
                            }
                        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(newsob);
                    }
                        });
            }
        }).start();
    }

    @Override
    public void setPresenterInterface(DataPresenter presenterInterface) {
        presenters.add(presenterInterface);
    }
}