package com.example.uiwidgettest.myreview.contentprovide;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

/**
 * Created by 40774 on 2017/10/4.
 */
//切换碎片的回调接口
public interface CutFragment {
    void Cut(Fragment Remove,Fragment fragment);
}
