package com.example.uiwidgettest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uiwidgettest.R;

/**
 * Created by 40774 on 2017/7/17.
 */

public class RightFragment extends Fragment {
    public static String TAG="RightFrament右边碎片";
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState)
    {
        Log.d(TAG,"视图被创造");
        View view=layoutInflater.inflate(R.layout.right_fragment,viewGroup,false);
        return view;
    }
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.d(TAG,"连接到活动");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//可以保存数据
        Log.d(TAG,"碎片产生");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"与碎片关联的活动被创造");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"碎片开始使用");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"碎片重新被使用");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"碎片暂停");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"连接到活动");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"碎片被销毁");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"碎片与活动接触关联");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"销毁视图");
    }
}