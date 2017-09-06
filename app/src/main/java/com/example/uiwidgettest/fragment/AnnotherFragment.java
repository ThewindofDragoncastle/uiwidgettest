package com.example.uiwidgettest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uiwidgettest.R;

/**
 * Created by 40774 on 2017/7/17.
 */

public class AnnotherFragment extends Fragment {
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.annother_right_fragment,viewGroup,false);
        return view;
    }
}
