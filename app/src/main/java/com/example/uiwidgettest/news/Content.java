package com.example.uiwidgettest.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import java.io.Writer;

/**
 * Created by 40774 on 2017/7/18.
 */
//增加方法刷新 将引入双页布局的右边一部分到碎片
public class Content extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.onlycontentfragment, container, false);
        return view;
    }

    public void refresh(String title,String content)
    {
        System.out.println("碎片刷新已被执行");
        View view1=view.findViewById(R.id.relative_linearLayout1);
        view1.setVisibility(View.VISIBLE);
        TextView textViewtitle=(TextView)view.findViewById(R.id.news_title);
        TextView textViewcontent=(TextView)view.findViewById(R.id.new_content);
        textViewtitle.setText(title);
        textViewcontent.setText(content);
    }
}
