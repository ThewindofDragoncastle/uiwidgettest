package com.example.uiwidgettest.thelight.tab;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/9/27.
 */

public class MyFragment extends Fragment implements View.OnClickListener,RecyclerViewItem
{
    private String title;
    private RecyclerView mRecyclerView;
    private List<MyPlan> myPlen=new ArrayList<>();
    private List<MyPlan> BackupmyPlen=new ArrayList<>();
    private FloatActionButtonOnClickListener tabName;
    private RecyclerViewAdapter adapter;
    private String[] medirs={};
    private boolean IsCut=false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        MyLog.d("碎片：","与活动关联"+title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            MyLog.d("碎片：","视图创建开始");
            View v = inflater.inflate(R.layout.myfragment, container, false);
//        linearLayout=(LinearLayout) v.findViewById(R.id.linearlayout);
            mRecyclerView = (RecyclerView) v.findViewById(R.id.myrecycler);
            FloatingActionButton button=(FloatingActionButton) v.findViewById(R.id.tabfab);
            button.setOnClickListener(this);
        //设置监听
//        ViewList.views.add(mRecyclerView);
            MyLog.d("碎片：", "添加碎片开始，集合大小+" );
//        linearLayout.removeAllViews();
        try {
            ViewGroup parent = (ViewGroup)v.getParent();
            //由于返回的就是父容器所以不需要清空
            parent.removeAllViews();
            MyLog.i("碎片", "移除父布局成功");
        }catch (NullPointerException e)
        {
            MyLog.i("碎片", "捕捉到一个萌萌的空指针异常");
        }
            return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.d("碎片：","被创建");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new RecyclerViewAdapter(getActivity(),myPlen,this);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public String toString() {
        return "我是碎片";
    }
    public void ininMyPlan(List<MyPlan> myPlen,String title,FloatActionButtonOnClickListener tabName)
    {
        this.tabName=tabName;
        this.title=title;
        //直接等于无效，因为数组就是地址
        for (MyPlan myPlan:myPlen)
        this.myPlen.add(myPlan);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tabfab:
            //if permission can use
            if (tabName.onClick(title)) {
                if (!IsCut) {
                    returnFile();
                    IsCut = true;
                } else {
                    recoverData();
                    IsCut = false;
                }
                adapter.notifyDataSetChanged();
            }
//            break;
//        }
    }
    private void returnFile()
    {
        if(medirs.length==0) {
            File file = new File(Environment.getExternalStorageDirectory() + "");
            medirs = file.list();
        }
        //save data
        CopyShuzu();
        //update data
        myPlen.clear();
        for(String files:medirs) {
         MyPlan plan = new MyPlan();
         plan.setMyplan(files);
         myPlen.add(plan);
        }
    }
    private void CopyShuzu()
    {
        //let newdata put in olddata
        BackupmyPlen.clear();
        for (int i=0;i<myPlen.size();i++)
        {
            BackupmyPlen.add(myPlen.get(i));
        }
    }
    private void recoverData()
    {
        myPlen.clear();
        for (MyPlan myPlan:BackupmyPlen)
        {
            myPlen.add(myPlan);
        }
    }

    @Override
    public void ReturnData(String[] data) {
       // medirs=data;
        //returnFile();
        //adapter.notifyDataSetChanged();
        Toast.makeText(getContext(),"hhhhhh",Toast.LENGTH_SHORT).show();
    }
}