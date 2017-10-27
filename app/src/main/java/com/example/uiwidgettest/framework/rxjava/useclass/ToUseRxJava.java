package com.example.uiwidgettest.framework.rxjava.useclass;

import android.app.Activity;
import android.content.Context;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.framework.rxjava.useinterface.CallBackButton;
import com.example.uiwidgettest.framework.rxjava.useinterface.DisplayString;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 40774 on 2017/10/10.
 */

public class ToUseRxJava  {
    private DisplayString displayString;
    private final String TAG="ToUseRxJava:";
    public ToUseRxJava(DisplayString displayString)
    {
        this.displayString=displayString;
    }

    public Subscriber subscriber=new Subscriber<String>() {
        @Override
        public void onSubscribe(Subscription subscription) {
            displayString.display("Subscriber:onSubscribe");
            MyLog.d(TAG,"onSubscribe");
        }

        @Override
        public void onNext(String s) {
            displayString.display("Subscriber:"+"onNext:"+s);
            MyLog.d(TAG,"onNext:"+s);
        }

        @Override
        public void onError(Throwable throwable) {
            displayString.display("Subscriber:"+"onError");
            MyLog.d(TAG,"onError");
        }

        @Override
        public void onComplete() {
            displayString.display("Subscriber:"+"onComplete");
            MyLog.d(TAG,"onComplete");
        }
    };
    public Observer observer=new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable disposable) {
            displayString.display("Observer:"+"onStart");
            MyLog.d(TAG,"onStart");
        }

        @Override
        public void onNext(String s) {
            displayString.display("Observer:"+"onNext"+s);
            MyLog.d(TAG,"onNext:"+s);
        }

        @Override
        public void onError(Throwable throwable) {
            displayString.display("Observer:"+"onError");
            MyLog.d(TAG,"onError");
        }

        @Override
        public void onComplete() {
            displayString.display("Observer:"+"onComplete");
            MyLog.d(TAG,"onComplete");
        }
    };
    public Observable observable=Observable.create(new ObservableOnSubscribe() {
        @Override
        public void subscribe(@NonNull ObservableEmitter observableEmitter) throws Exception {

            observableEmitter.onNext("陈宏林");
            observableEmitter.onNext("李准");
            observableEmitter.onNext("张鸿雁");
            observableEmitter.onComplete();
        }
    });
    public void useRx0()
    {
        //完整
        observable.subscribe(observer);
    }
    public void useRx1()
    {
        //简化
        Observable<String> observable=Observable.just("张辽文远","典韦");
        observable.subscribe(observer);
    }
    public void useRx2()
    {
        //部分回调
        Observable<String> observable=Observable.just("虎痴许褚","白衣书生");
        Consumer<String> consumer=new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                displayString.display("Consumer:"+s);
            }
        };
        observable.subscribe(consumer);
    }
    public void useRx3()
    {
        //consumer处理时间，其他处理完成
        Consumer<Throwable> onError=new Consumer<Throwable>() {
            @Override
            public void accept(Throwable o) throws Exception {

            }
        };
        Consumer<String> onNext=new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                displayString.display("Consumer:"+s);
            }
        };
        Action onComplete=new Action() {
            @Override
            public void run() throws Exception {
                displayString.display(" ActiononComplete:");
            }
        };
        //有顺序之分
        observable.subscribe(onNext,onError,onComplete);
    }
    public void useRx4()
    {
        //interval
        Observable.interval(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Long>() {
            @Override
            public void accept(final Long aLong) throws Exception {
                //这个是在子线程中进行
                        //需要运行在主线程上
                        displayString.display("Consumer:"+aLong);
            }
        });
    }
    public void useRx5(final String ip)
    {
//        "用Rxjava以Okhttp访问网络",子线程，如果以原先的做法必须使用activity.runuiin..
        //因为我们用的RXjava框架，这种不太好的做法，我全部删除 去掉传入活动activity
       final Observable thisob= Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> observableEmitter) throws Exception {
                OkHttpClient client=new OkHttpClient();
                RequestBody body=new FormBody.Builder()
                        .add("ip",ip)
                        .build();
               final Request request=new Request.Builder()
                        .url("http://ip.taobao.com/activityView/getIpInfo.php")
                        .post(body)
                        .build();
                okhttp3.Call call=client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                          observableEmitter.onError(new Exception("错误"));
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                      final String info=response.body().string();
                        observableEmitter.onNext(info+
                                "访问网址："+request.url().toString());
                        observableEmitter.onComplete();
                    }
                });
            }
        });
        thisob.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Toast.makeText(MyApplication.getContext(),"呼叫成功"+s,Toast.LENGTH_SHORT).show();
                displayString.display(s);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Toast.makeText(MyApplication.getContext(),"呼叫失败",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onComplete() {
                displayString.display("Observe:onComplete");
            }
        });
    }

}
