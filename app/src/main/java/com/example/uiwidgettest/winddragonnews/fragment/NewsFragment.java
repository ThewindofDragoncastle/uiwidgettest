package com.example.uiwidgettest.winddragonnews.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.DbData;
import com.example.uiwidgettest.winddragonnews.HandleData.News;
import com.example.uiwidgettest.winddragonnews.adapter.RecyclerViewAdapter;
import com.example.uiwidgettest.winddragonnews.useinterface.ForFragmetAdapter.NewsFragmentToMainpage;
import com.example.uiwidgettest.winddragonnews.useinterface.ForFragmetAdapter.RecyclerviewToNewsFragment;
import com.example.uiwidgettest.winddragonnews.view.NewsFragmentListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 40774 on 2017/11/7.
 */

public class NewsFragment extends Fragment implements RecyclerviewToNewsFragment,NewsFragmentListener,NewsFragmentListener.FormatNewsFragment {
    private final String TAG=getClass().getName();
//    通过活动传入当前界面代码
    private int page=-1;
    @BindView(R.id.reclyer)
    RecyclerView recycler;
//    向活动回调
    private NewsFragmentToMainpage mcallback;
    private List<DbData> newsList=new ArrayList<>();
    private RecyclerViewAdapter madapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.newsfragment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        madapter=new RecyclerViewAdapter(getContext(), this,newsList);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(madapter);
        start();
//        每次创建碎片都应该设置媒介 7个碎片就7个媒介
    }

    @Override
    public void ChangeFragmentCallback(Data data) {
        mcallback.ChangeFragmentCallback(data);
    }
    public void setMcallback(NewsFragmentToMainpage mcallback)
    {
        this.mcallback = mcallback;
    }

    @Override
    public void start() {

    }

    @Override
    public void setDataList(List<DbData> dataList) {
//        如果直接赋予地址 适配器不认为数据源发生了改变
        int len=newsList.size();
        newsList.clear();
        if(madapter!=null) {
            MyLog.d(TAG,"适配器不为空");
//            为什么要通知数据改变？因为多次加载时 不进行数据改变通知 那么下文的数据插入就会报错
            madapter.notifyItemRangeRemoved(0,len);
        }
        if(dataList!=null)
        for (DbData data:dataList) {
            Collections.addAll(newsList, data);
//            String[] my=data.getImageUrls();
//            MyLog.d(TAG,"图片URL"+my[1]);
//            new HandleJsonData().imageURLS(data.getImageUrls());
        }
//        这时候有一个问题数据下传至这里的时候可能适配器还为空
//        如果适配器为空 那么等适配器创建时自己通知
        if(madapter!=null) {
            MyLog.d(TAG,"适配器不为空");
            madapter.notifyItemRangeInserted(0,newsList.size());
//            madapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setImage(String file_path) {

    }

    @Override
    public void setPage(int i) {
        page=i;
    }

    @Override
    public void showError(String e) {
        MyLog.d(TAG,"当前页面："+page+"勾选界面"+mcallback.getPage());
//        警示框只在当前界面弹出
        if(mcallback.getPage()==page) {
            new AlertDialog.Builder(getContext())
                    .setTitle("网络故障")
                    .setMessage(e+"\n")
                    .setPositiveButton("朕知道了", null)
                    .show();
        }
    }
}

