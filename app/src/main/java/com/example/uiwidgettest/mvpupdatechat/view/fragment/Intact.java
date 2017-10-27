package com.example.uiwidgettest.mvpupdatechat.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.mvpupdatechat.bufferdata.Bufferdata;
import com.example.uiwidgettest.mvpupdatechat.json.HandleJson;
import com.example.uiwidgettest.mvpupdatechat.presenter.Intact.MedIntactPresenter;
import com.example.uiwidgettest.mvpupdatechat.presenter.MedBasePresenter;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractIntract;
import com.example.uiwidgettest.mvpupdatechat.view.activity.CurrentData;
import com.example.uiwidgettest.mvpupdatechat.view.activity.MainPage;
import com.example.uiwidgettest.mvpupdatechat.view.activity.Utils;
import com.example.uiwidgettest.mvpupdatechat.view.adapter.IntactRecyclerviewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractIntract.*;

/**
 * Created by 40774 on 2017/10/16.
 */

public class Intact extends Fragment implements
        view,FragmentWithAdapter,SwipeRefreshLayout.OnRefreshListener {
    private List<com.example.uiwidgettest.ui.sendorrecieve.Intact> intacts;
    private IntactRecyclerviewAdapter adapter;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.intactrecycle)
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_intact,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intacts=new ArrayList<>();
        adapter=new IntactRecyclerviewAdapter(getContext(),intacts,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyLog.d("Intact:","视图在活动中被创建");
        //刷新条设置监听
        swipeRefreshLayout.setOnRefreshListener(this);
        MedIntactPresenter.getInstance().setViewInterface(this);
        //启动
        MedIntactPresenter.getInstance().Start();

    }

    @Override
    public void updateAdapter(List<com.example.uiwidgettest.ui.sendorrecieve.Intact> intacts) {
        this.intacts.clear();
        for (com.example.uiwidgettest.ui.sendorrecieve.Intact intact:intacts)
        {
            this.intacts.add(intact);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void StartRefresh() {
        if(!swipeRefreshLayout.isRefreshing())
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void EndRefresh() {
        if(swipeRefreshLayout.isRefreshing())
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void IntoChat(com.example.uiwidgettest.ui.sendorrecieve.Intact intact) {
        //设置当前页面访问的对象
        Bufferdata.setTarget(intact.gettargetAccount());
        MainPage page=(MainPage)getActivity();
        Utils.replament(page.getSupportFragmentManager(),FragmentCollector.GetChat(),this);
    }

    @Override
    public void onRefresh() {
        MedIntactPresenter.getInstance().Start();
    }
    @Override
    public void onResume() {
        //活动创建时，把碎片加入CurrentData 以方便其他类判断当前处于那个界面
        CurrentData.setCurrentfragment(this);
//        重新开启时重新请求数据
        onRefresh();
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        MyLog.d("碎片：","碎片暂停");
        CurrentData.setCurrentfragment(null);
    }
}
