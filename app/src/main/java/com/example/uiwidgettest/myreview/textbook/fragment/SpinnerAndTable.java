package com.example.uiwidgettest.myreview.textbook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uiwidgettest.R;

/**
 * Created by 40774 on 2017/12/27.
 */

public class SpinnerAndTable extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.textbook_tablelayout_3_6,container,false);
        return view;
    }
}
