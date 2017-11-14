package com.example.uiwidgettest.winddragonnews.model.newsimage;

import android.os.Environment;
import android.util.Log;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.winddragonnews.presenter.DataPresenter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 40774 on 2017/11/12.
 */
//这里做的很多余，GLIDE自带缓存效果，不过这个可以直接看见大小
public class ImageDownload implements ImageDownListener {
    private String TAG=getClass().getName();
//    path
    private final String MKDIR_PATH= Environment.getExternalStorageDirectory()
        +File.separator+"开发专用文件夹"+File.separator+"新闻图片";
private List<DataPresenter> dataPresenters=new ArrayList<>();
    @Override
    public void setDataPresenter(DataPresenter presenter) {
        dataPresenters.add(presenter);
    }
    @Override
    public void execute(final URL url) {
//        必须开启多个线程下载多个图片
        new Thread(new Runnable() {
            @Override
            public void run() {
//                下载文件准备
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        String filename = url.toString().substring(url.toString().lastIndexOf(File.separator));
                        filename = filename.split(File.separator)[1];
//                        删除“？”号
                        filename = filename.split("\\?")[0];
                        MyLog.d(TAG, filename);
                        File mkdir = new File(MKDIR_PATH);
                        if (mkdir.isFile())
                            mkdir.delete();
                        if (!mkdir.exists())
                            mkdir.mkdir();
                        File file = new File(MKDIR_PATH + File.separator + filename);
                        MyLog.d(TAG, "文件目录：" + file.getPath());
                        if (file.exists()) {
//                            存在直接返回 否则下载
                                MyLog.d(TAG, "文件已存在");

                        }
//                流
                        else {
//                            下载文件
                            InputStream inputStream = null;
                            BufferedInputStream bufferedInputStream = null;
                            FileOutputStream fileOutputStream = null;
                            BufferedOutputStream outputStream = null;
                            try {
                                MyLog.d(TAG, "文件创建：" + file.createNewFile());
                                inputStream = response.body().byteStream();
                                bufferedInputStream = new BufferedInputStream(inputStream);
                                fileOutputStream = new FileOutputStream(file);
                                outputStream = new BufferedOutputStream(fileOutputStream);
                                byte[] b = new byte[1024];
                                while (bufferedInputStream.read(b, 0, b.length) != -1) {
                                    outputStream.write(b);
                                }
                                outputStream.flush();
                                inputStream.close();
                                outputStream.close();
                                bufferedInputStream.close();
                                fileOutputStream.close();
                                response.body().close();
//                                通过数据库更新data路径
                            } catch (IOException e) {
                                MyLog.d(TAG, "文件流错误：" + e.toString());
                                try {
                                    if(response.body()!=null)
                                        response.body().close();
                                    if (inputStream != null)
                                        inputStream.close();
                                    if (outputStream != null)
                                        outputStream.close();
                                    if (bufferedInputStream != null)
                                        bufferedInputStream.close();
                                    if (fileOutputStream != null)
                                        fileOutputStream.close();
                                } catch (IOException e1) {

                                }
                                io.reactivex.Observable.just(file.getPath())
                                        .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
//                                        一张图片放入数据库一次
                                          dataPresenters.get(0).returnImageUrl(s,url.toString());
                                    }
                                });
                            }
                        }
                    }
                });

            }
        }).start();
//
    }
}
