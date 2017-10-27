package com.example.uiwidgettest.myreview.mediaplayer.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.mediaplayer.presenter.PresenterContract;
import com.example.uiwidgettest.myreview.mediaplayer.presenter.PresenterContractStandard;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 40774 on 2017/10/25.
 */

public class Video extends Fragment {
    @BindView(R.id.video)
    VideoView media;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.video,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        media.setVideoURI(Uri.parse("http://10.0.2.2/movie/英雄时刻.avi"));
        media.start();
        super.onActivityCreated(savedInstanceState);
    }
}
