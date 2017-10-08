package com.example.uiwidgettest.myreview.media;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.service.BinderListener;
import com.example.uiwidgettest.myreview.service.GetConnectionService;
import com.example.uiwidgettest.myreview.service.PlayService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 40774 on 2017/10/6.
 */

public class PlayMovieSong extends Fragment implements View.OnClickListener,BinderListener {
    private RecyclerView recyclerView;
    private TextView display;
    private LinearLayout linearLayout;
    private FloatingActionButton stop;
    //扫描到文件进行缓存
    private List<File> songs=new ArrayList<>();
    private List<File> movies=new ArrayList<>();
    private final String INTRODUCE="执行过程：1.从SharedPrefence缓存数据中取出" +
            "JSON类型的数据，解析还原成数个包含列表信息的类。\n2.将多个类传入RecyclerView（适配器）中，显示出来" +
            "\n3.根据用户的条件扫描磁盘，通过后缀筛选取出符合条件的音乐或者视频。" +
            "添加到适配器中" +
            "\4.根据传入的类中的信息判断为音乐还是电影，是否为最后，两种以线隔开，并且首行添加标题"+
            "\n5.将信息缓存。" +
            "\n处于后台扫描的时候禁止其他操作"+
            "\n难点在于：计算文件类型的递归调用以及对类型区分过后进行排版"+
            "\n扫描的类型有：mp3、mp4";
    private final int MOVIE=2;
    private final int SONG=1;
    private EditText input;
    //微小的变化也能让扫描停止
    private  boolean Isstop=false;
    private MovieSongitem songitem;
    //对歌曲或者电影进行收集
    private List<SongorMovie> songorMovies=new ArrayList<>();
    //对歌曲列表进行缓存
    private SharedPreferences.Editor editor;
    private Button scout;
    private Button hide;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.movieandsong,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.reclyer);
        hide=(Button)view.findViewById(R.id.hide);
        hide.setOnClickListener(this);
        stop=(FloatingActionButton)view.findViewById(R.id.stop);
        stop.setOnClickListener(this);
        scout=(Button)view.findViewById(R.id.scout);
        scout.setOnClickListener(this);
        display=(TextView)view.findViewById(R.id.display);
        display.setText(INTRODUCE);
        input=(EditText)view.findViewById(R.id.input);
        input.setText("开发专用文件夹");
        linearLayout=(LinearLayout)view.findViewById(R.id.parentdisplay);
        //原子级//预先将悬浮键隐藏
        stop.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //先于适配器取出缓存数据
        SharedPreferences preferences=
                PreferenceManager.getDefaultSharedPreferences(getContext());
        restore(preferences.getString("info",null));
        songitem=new MovieSongitem(getContext(),songorMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(songitem);
        stop.setVisibility(View.VISIBLE);
        GetConnectionService.binderListener=this;
        Intent intent=new Intent(getContext(), PlayService.class);
        getActivity().bindService(intent, GetConnectionService.conn,Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.hide:
               linearLayout.setVisibility(View.GONE);
                break;
            case R.id.scout:
                //锁定
                input.setEnabled(false);
                scout.setEnabled(false);
                //首先将终止标记复原
                Isstop=false;
                stop.setVisibility(View.VISIBLE);
                    Scout();

                break;
            case R.id.stop:
              linearLayout.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void Scout() {
        //需要先清空数组
        songorMovies.clear();
        songs.clear();
        movies.clear();
        //因为查找到数据之后需要更新UI所以在主线程进行
        //以一个不可取消的加载为提示
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("hello！酷狗。");
        dialog.setMessage("查找中...");
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//                Snackbar.make(recyclerView, "静默后台扫描中。", Snackbar.LENGTH_SHORT)
//                        .setAction("立即停止", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
                Toast.makeText(getContext(),"停止扫描。",Toast.LENGTH_SHORT).show();
                                input.setEnabled(true);
                                scout.setEnabled(true);
                                Isstop = true;
//                            }
//                        }).show();
            }
        });
        final File file;
      if(input.getText().toString().isEmpty())
         file= new File(Environment.getExternalStorageDirectory()+"");
          else
         file = new File(Environment.getExternalStorageDirectory()
                + File.separator + input.getText().toString());
        if (file.exists())
        {
            dialog.show();
            //如果文件存在开启子线程 进行查找 否则弹出提示不存在
        new Thread(new Runnable() {
            @Override
            public void run() {
                    Circle(file);
                MyLog.d("PlayMovieSong:","扫描完成");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //扫描完成转到主线程中执行
                        //进行缓存
                        //扫描完成悬浮按键隐藏（无论是自然扫描完成还是人为必定执行到此）
                        stop.setVisibility(View.GONE);
                        int size=0;
                        if(songs.size()!=0) {
                            //对可能出现的bug进行处理
                            size = songs.size();
                            for (int i = 0; i < size; i++) {
                                File file = songs.get(i);
                                SongorMovie songorMovie = new SongorMovie(SONG, file.getPath(), file.getName().split("[.]")[0]);
                                songorMovies.add(songorMovie);
                            }
                        }
              if(movies.size()!=0)
              for(File file:movies)
                  songorMovies.add(new SongorMovie(MOVIE,file.getPath(),file.getName().split("[.]")[0]));
//如果没有视频不添加
                        if(songorMovies.size()>=size+1)
                        songorMovies.get(size).setIslast(true);
                        input.setEnabled(true);
                        scout.setEnabled(true);
                        dialog.dismiss();
                        editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                  //将音乐数据交给服务
                        if(GetConnectionService.binder!=null)
                        GetConnectionService.binder.setArray(songorMovies);
                        editor.putString("info", SongOrMovietojson(songorMovies));
                        editor.apply();
                        songitem.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
           else
        Toast.makeText(getContext(),"此目录不存在。",Toast.LENGTH_SHORT).show();
    }
    private void Circle(File file)
    {
            if (Isstop)
                Thread.interrupted();

        if(file.isFile()) {
            try {
                String name=file.getName().split("[.]")[1];
                if (name.equals("mp3"))
                    songs.add(file);
                else if (name.equals("mp4"))
                     movies.add(file);
//                    String filename=file.getName();
//                    String path=file.getPath();
//                    String name=filename.split("[.]")[1];
//                    songorMovies.add(new SongorMovie(SONG,path,filename.split("[.]")[0]));

            }catch (ArrayIndexOutOfBoundsException e)
            {
//                MyLog.d("PlayMovieSong:", "发生异常。");
            }
        }
        else {
            try {
                for (File file1 : file.listFiles()) {
                        Circle(file1);
                }
            }catch (NullPointerException e)
            {
//                MyLog.d("PlayMovieSong:", "发生kong 异常。");
            }
        }
    }
    private String SongOrMovietojson(List<SongorMovie> movies)
    {
        try {

        JSONArray array=new JSONArray();
        for (SongorMovie movie:movies)
        {
                JSONObject object=new JSONObject();
                object.put("SongorMovie",movie.getSongorMovie());
                object.put("path",movie.getPath());
                object.put("name",movie.getName());
                object.put("Islast",movie.islast());
                array.put(object);
        }
        return array.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void restore(String jsondata) {
        if (jsondata != null) {
            Gson gson = new Gson();
            songorMovies=gson.fromJson(jsondata, new TypeToken<List<SongorMovie>>() {}.getType());
        }
    }

    @Override
    public void Binder(PlayService.MusicBinder binder) {
        //一旦绑定上就会调用绑定监听器
        GetConnectionService.binder.setArray(songorMovies);
    }
}
