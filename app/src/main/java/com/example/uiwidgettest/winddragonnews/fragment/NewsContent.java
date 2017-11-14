package com.example.uiwidgettest.winddragonnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.broadcast.WebView;
import com.example.uiwidgettest.winddragonnews.HandleData.Data;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 40774 on 2017/11/7.
 */

public class NewsContent extends Fragment {
//    该碎片显示我的一些说明
    private final String INFO="诸多功能还未实现，不是不能，是有些不太必要。" +
        "例如：将本地收藏上传至云端服务器，还有一些设置。最大的冗余还是：将图片下载到了" +
        "本地，glide自带缓存效果，这样增加了很大代码冗余。\n-----冯江涛 \n2017年11月14日 19:19:05";
    @BindView(R.id.newscontent)
    TextView newscontent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.newscontent,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newscontent.setText(INFO);
    }
}
